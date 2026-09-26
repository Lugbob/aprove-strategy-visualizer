package de.luca;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.luca.analysis.AnalysisResult;
import de.luca.grammar.StrategyLexer;
import de.luca.grammar.StrategyParser;
import de.luca.graph.Edge;
import de.luca.graph.Graph;
import de.luca.graph.Node;
import de.luca.parsing.DeclarationParser;
import de.luca.parsing.ProcessorDeclaration;
import de.luca.parsing.StrategyGraphVisitor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.nio.file.*;
import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.List;

//Haupt Backend-Pipeline. Parst strategy definition und processor declaration files.
public class StrategyVisualizerBackend {

    private static final Set<String> STRATEGY_LANGUAGE_TERMS = Set.of("Sequence", "ParallelSequence", "First", "Any",
            "AnyDelay", "AnyK", "Combine", "CombineParallel", "CombineSequential", "Delay", "Maybe", "Timer",
            "WallTimer", "Repeat", "RepeatS", "Solve", "Prove", "Disprove", "If");

    public static void main(String[] args) throws Exception {

        if (args.length < 1)
            throw new IllegalArgumentException(
                    "Usage: java de.luca.StrategyVisualizerBackend <strategy-file> <processor-Declaration-File (optional)>");

        Path declarationPath = Path.of("..", "public", "exampleProcDecFiles", "std.strategy");
        Path inputPath = Path.of(args[0]);
        if (args.length > 1) {
            declarationPath = Path.of(args[1]);
        }
        Path outputPath = Path.of("..", "public", "parsedGraph.json");

        if (!Files.exists(inputPath)) {
            throw new IllegalArgumentException("Input file does not exist: " + inputPath);
        }

        if (!Files.exists(declarationPath)) {
            throw new IllegalArgumentException("Declaration file does not exist: " + declarationPath);
        }

        String inputText = Files.readString(inputPath);
        CharStream input = CharStreams.fromString(inputText); // Umwandeln für ANTLR

        StrategyLexer lexer = new StrategyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        for (String line : inputText.split("\n")) {
            if (line.trim().startsWith("declare ")) {
                System.err.println(
                        "Warning: processor declaration found in strategy file.");
                break;
            }
        }

        StrategyParser parser = new StrategyParser(tokens);

        StrategyParser.ProgramContext tree = parser.program(); // Jeder ParseTree ist auch ein Programm
        Map<String, Graph> graphs = new LinkedHashMap<>(); // Bäume können nach Namen gesucht werden.

        // Umformung des Graphen in eine JSON über Jackson:
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Macht den JSON-Output lesbar

        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new RuntimeException("Parsing fehlgeschlagen.(Parser.java)");
        }

        if (tree.equation().isEmpty()) {
            System.out.println("No equations found.");
            mapper.writeValue(outputPath.toFile(), graphs);
            return;
        }

        Map<String, String> strategyDescriptions = new LinkedHashMap<>();

        for (var equation : tree.equation()) {
            String name = equation.LCNAME().getText();

            StringBuilder description = new StringBuilder();

            for (var desc : equation.DESCRIPTION()) {
                String text = desc.getText().replaceFirst("#@description", "").trim();
                if (!description.isEmpty()) {
                    description.append(" ");
                }
                description.append(text);
            }
            strategyDescriptions.put(name, description.toString());
        }

        DeclarationParser declarationParser = new DeclarationParser();
        Map<String, ProcessorDeclaration> processorDeclarations = declarationParser.getProcessors(declarationPath); // Prozessorendefinitionen

        List<String> duplicateDeclarations = declarationParser.getDuplicateDeclarations();
        List<String> duplicateStrategyDefinitions = new ArrayList<>();

        for (String name : STRATEGY_LANGUAGE_TERMS) {
            processorDeclarations.remove(name);
        }
        Map<String, ProcessorDeclaration> processorsInStrategyFile = new LinkedHashMap<>();

        Path analysisPath = Path.of("..", "public", "analysis.json");

        for (var equation : tree.equation()) {
            String name = equation.LCNAME().getText();

            var builder = new StrategyGraphVisitor(processorDeclarations, processorsInStrategyFile,
                    strategyDescriptions);
            builder.visit(equation);

            Graph graph = builder.getGraph();
            graph.setDescription(strategyDescriptions.getOrDefault(name, ""));

            // Test ob es irgendwo doppelte StrategyDefinitions gibt.
            if (graphs.containsKey(name)) {
                System.out.println("Duplicate strategy definition: " + name);
                duplicateStrategyDefinitions.add(name);
            }
            graphs.put(name, graph);

        }

        // Static analysis
        Map<String, Integer> strategyConstructCounts = new LinkedHashMap<>();
        List<String> strategyDefinitions = new ArrayList<>(graphs.keySet());
        Map<String, List<String>> strategyReferencedBy = new LinkedHashMap<>();
        Map<String, List<String>> strategyReferences = new LinkedHashMap<>();

        for (String strategy : strategyDefinitions) {
            strategyReferencedBy.put(strategy, new ArrayList<>());
            strategyReferences.put(strategy, new ArrayList<>());
        }
        for (var entry : graphs.entrySet()) {
            String currentStrategy = entry.getKey();
            Graph graph = entry.getValue();

            for (Node node : graph.getNodes()) {
                String label = node.data().getLabel();

                if (!label.startsWith("Reference:")) {
                    continue;
                }

                String referencedStrategy = label.substring("Reference:".length()).trim();

                if (!graphs.containsKey(referencedStrategy)) {
                    continue;
                }

                if (!strategyReferences.get(currentStrategy).contains(referencedStrategy)) {
                    strategyReferences.get(currentStrategy).add(referencedStrategy);
                }

                if (!strategyReferencedBy.get(referencedStrategy).contains(currentStrategy)) {
                    strategyReferencedBy.get(referencedStrategy).add(currentStrategy);
                }
            }
        }
        List<List<String>> referenceCycles = findReferenceCycles(strategyReferences);

        // Dafür sorgen, dass auch Sachen die nicht vorkommen aufgezählt werden:
        for (String construct : List.of(
                ";",
                ":",
                "First",
                "Any",
                "AnyK",
                "Combine",
                "CombineParallel",
                "CombineSequential",
                "Maybe",
                "Repeat",
                "RepeatS",
                "Timer",
                "WallTimer",
                "Delay",
                "AnyDelay",
                "If",
                "Solve",
                "Prove",
                "Disprove")) {
            strategyConstructCounts.put(construct, 0);
        }

        for (Graph graph : graphs.values()) {
            for (Node node : graph.getNodes()) {
                String construct = getStrategyConstruct(node);

                if (construct != null) {
                    int oldCount = strategyConstructCounts.getOrDefault(construct, 0);
                    strategyConstructCounts.put(construct, oldCount + 1);
                }
            }

            for (Edge edge : graph.getEdges()) {
                String operator = getSequenceOperator(edge);

                if (":".equals(operator) || ";".equals(operator)) {
                    int oldCount = strategyConstructCounts.getOrDefault(operator, 0);
                    strategyConstructCounts.put(operator, oldCount + 1);
                }
            }
        }

        Map<String, String> declaredProcessorsDefaults = new LinkedHashMap<>();

        for (var entry : processorDeclarations.entrySet()) {
            declaredProcessorsDefaults.put(entry.getKey(), entry.getValue().defaults());
        }

        List<String> declaredProcessors = new ArrayList<>(processorDeclarations.keySet());
        List<String> strategyFileProcessors = new ArrayList<>(processorsInStrategyFile.keySet());

        List<String> declaredButNotInStrategyFile = new ArrayList<>();

        for (String declared : declaredProcessors) {
            boolean found = false;

            for (String processor : strategyFileProcessors) {
                if (declared.equalsIgnoreCase(processor)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                declaredButNotInStrategyFile.add(declared);
            }
        }

        List<String> processorsWithoutDeclaration = new ArrayList<>();

        for (String processor : strategyFileProcessors) {
            boolean found = false;

            for (String declared : declaredProcessors) {
                if (processor.equalsIgnoreCase(declared)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                processorsWithoutDeclaration.add(processor);
            }
        }

        AnalysisResult analysis = new AnalysisResult(processorsWithoutDeclaration, processorsWithoutDeclaration.size(),
                declaredButNotInStrategyFile, declaredButNotInStrategyFile.size(), declaredProcessors,
                declaredProcessors.size(), strategyFileProcessors, strategyFileProcessors.size(), duplicateDeclarations,
                duplicateDeclarations.size(), strategyConstructCounts, strategyDefinitions, strategyDefinitions.size(),
                duplicateStrategyDefinitions, duplicateStrategyDefinitions.size(), referenceCycles, strategyReferences,
                strategyReferencedBy,
                declaredProcessorsDefaults);

        mapper.writeValue(outputPath.toFile(), graphs);

        mapper.writeValue(analysisPath.toFile(), analysis);
    }

    // Helper Method to get amount of strategy constructs in a file.
    private static String getStrategyConstruct(Node node) {
        String label = node.data().getLabel();

        if (label.startsWith("First"))
            return "First";
        if (label.startsWith("AnyK"))
            return "AnyK";
        if (label.startsWith("AnyDelay"))
            return "AnyDelay";
        if (label.startsWith("Any"))
            return "Any";
        if (label.startsWith("CombineParallel"))
            return "CombineParallel";
        if (label.startsWith("CombineSequential"))
            return "CombineSequential";
        if (label.startsWith("Combine"))
            return "Combine";
        if (label.startsWith("RepeatS"))
            return "RepeatS";
        if (label.startsWith("Repeat("))
            return "Repeat";
        if (label.startsWith("Maybe"))
            return "Maybe";
        if (label.startsWith("WallTimer"))
            return "WallTimer";
        if (label.startsWith("Timer"))
            return "Timer";
        if (label.startsWith("Delay"))
            return "Delay";
        if (label.startsWith("Condition["))
            return "If";
        if (label.startsWith("Solve"))
            return "Solve";
        if (label.startsWith("Prove"))
            return "Prove";
        if (label.startsWith("Disprove"))
            return "Disprove";

        return null;
    }

    private static String getSequenceOperator(Edge edge) {
        return edge.sequenceOperator();
    }

    // Helper Methode um per DFS Cycles in Strategy References zu finden.
    private static List<List<String>> findReferenceCycles(Map<String, List<String>> references) {

        List<List<String>> cycles = new ArrayList<>();
        Map<String, Integer> state = new LinkedHashMap<>();
        List<String> path = new ArrayList<>();

        for (String strategy : references.keySet()) {
            if (state.getOrDefault(strategy, 0) == 0) {
                dfsCycle(strategy, references, state, path, cycles);
            }
        }

        return cycles;
    }

    private static void dfsCycle(String strategy, Map<String, List<String>> references, Map<String, Integer> state,
            List<String> path, List<List<String>> cycles) {

        state.put(strategy, 1);
        path.add(strategy);

        for (String next : references.get(strategy)) {

            if (state.getOrDefault(next, 0) == 0) {
                dfsCycle(next, references, state, path, cycles);
            }

            else if (state.get(next) == 1) {
                int start = path.indexOf(next);

                List<String> cycle = new ArrayList<>(path.subList(start, path.size()));

                cycle.add(next);
                cycles.add(cycle);
            }
        }

        path.remove(path.size() - 1);
        state.put(strategy, 2);
    }
}