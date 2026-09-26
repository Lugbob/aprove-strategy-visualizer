package de.luca.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.luca.grammar.StrategyParser;
import de.luca.grammar.StrategyParser.StrategyAtomContext;
import de.luca.graph.Graph;
import de.luca.graph.Node;
import de.luca.graph.Position;
import de.luca.grammar.StrategyParserBaseVisitor;

//Besucht den erzeugten Parse-Tree und erzeugt daraus den Java strategy graph
public class StrategyGraphVisitor extends StrategyParserBaseVisitor<BuildResult> {
    private static int Y_GAP = 250;
    private static int X_GAP = 420;
    private int[] pos = { 0, 0 };
    private final Graph graph = new Graph();

    private final Map<String, ProcessorDeclaration> declarations;
    Map<String, ProcessorDeclaration> processorsInStrategyFile;
    private final Map<String, String> strategyDescriptions;

    public StrategyGraphVisitor(Map<String, ProcessorDeclaration> declarations,
            Map<String, ProcessorDeclaration> processorsInStrategyFile, Map<String, String> strategyDescriptions) {
        this.declarations = declarations;
        this.processorsInStrategyFile = processorsInStrategyFile;
        this.strategyDescriptions = strategyDescriptions;
    }

    public Graph getGraph() {
        return graph;
    }

    private Position getPosition() {
        return new Position(pos[0], pos[1]);
    }

    private void moveDown() {
        pos[1] += Y_GAP;
    }

    @Override
    public BuildResult visitProgram(StrategyParser.ProgramContext ctx) throws RuntimeException {
        throw new RuntimeException("visitProgram wird hier nicht benutzt. Parser.java geht über equations.");
    }

    @Override
    public BuildResult visitEquation(StrategyParser.EquationContext ctx) {
        BuildResult result = visit(ctx.strategyTerm());
        graph.setRoot(result.entry.id());
        return result;
    }

    @Override
    public BuildResult visitStrategyTerm(StrategyParser.StrategyTermContext ctx) {
        return visit(ctx.sequence());
    }

    @Override
    public BuildResult visitSequence(StrategyParser.SequenceContext ctx) {
        int baseX = pos[0];

        List<StrategyAtomContext> atoms = ctx.strategyAtom(); // Alle StrategyAtoms in einer Liste A:B:C -> (A,B,C)
        BuildResult current = visit(atoms.get(0)); // Passendes Analysieren von A
        Node previousEntry = current.entry;
        List<Node> subTrees = new ArrayList<>();
        subTrees.addAll(current.subTree);

        for (var i = 1; i < atoms.size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText(); // ob : oder ;

            pos[0] = baseX; // Bugfix falls pos[0] broken in anderen Methoden
            moveDown();

            BuildResult next = visit(atoms.get(i)); // Passende Analyse von B

            previousEntry.data().setNext(next.entry.id(), operator);
            previousEntry = next.entry;

            subTrees.addAll(next.subTree);

            for (Node exit : current.exits) { // Letzten Teilgraph mit nächstem Teilgraph verbinden: [A,B] zu C = A->C
                                              // und B->C
                graph.addEdge(exit, next.entry, operator);
            }

            current = makeResult(current.entry, next.exits, subTrees);
        }
        return current;
    }

    @Override
    public BuildResult visitStrategyAtom(StrategyParser.StrategyAtomContext ctx) {
        if (ctx.repeat() != null) { // Gehe alle Möglichkeiten durch und falls nur Referenz, erstelle direkt Knoten.
            return visit(ctx.repeat());
        } else if (ctx.iterate() != null) {
            return visit(ctx.iterate());
        } else if (ctx.choice() != null) {
            return visit(ctx.choice());
        } else if (ctx.maybe() != null) {
            return visit(ctx.maybe());
        } else if (ctx.delay() != null) {
            return visit(ctx.delay());
        } else if (ctx.timer() != null) {
            return visit(ctx.timer());
        } else if (ctx.ifExpr() != null) {
            return visit(ctx.ifExpr());
        } else if (ctx.combine() != null) {
            return visit(ctx.combine());
        } else if (ctx.solveProve() != null) {
            return visit(ctx.solveProve());
        } else if (ctx.processor() != null) {
            return visit(ctx.processor());
        } else if (ctx.LCNAME() != null) {
            String referenceName = ctx.LCNAME().getText();
            String description = strategyDescriptions.get(referenceName);

            if (description == null || description.isBlank()) {
                description = "Variable which refers to another defined strategy term.";
            }

            Node newNode = graph.addNode("Reference", getPosition(), "Reference: " + referenceName, description);
            return makeResult(newNode, List.of(newNode), List.of(newNode));
        }
        throw new RuntimeException("Unhandled strategy atom: " + ctx.getText());
    }

    @Override
    public BuildResult visitIterate(StrategyParser.IterateContext ctx) { // Nicht mehr gebraucht
        String label = ctx.getStart().getText();
        String description = "";
        switch (label) {
            case "Iterate":
                description = "Executes an iterable processor and processes the resulting list, possibly in parallel. Succeeds if one result succeeds.";
                break;
            case "IterateS":
                description = "Executes an iterable processor and processes the resulting list sequentially. Succeeds if one result succeeds.";
                break;
            default:
                description = "Unexpected strategy type";
                break;
        }
        int baseX = pos[0];
        int baseY = pos[1];

        Node entryNode = graph.addNode(label, getPosition(), label, description);

        pos[0] = baseX;
        pos[1] = baseY + Y_GAP;

        BuildResult inside = visit(ctx.processor());
        entryNode.data().addChild(inside.entry.id());
        graph.addEdge(entryNode, inside.entry);

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(entryNode);
        subTrees.addAll(inside.subTree);

        BuildResult result = makeResult(entryNode, inside.exits, subTrees);
        result = addBoxAround(result, label);

        pos[0] = baseX;
        pos[1] = result.maxY;

        return result;
    }

    @Override
    public BuildResult visitRepeat(StrategyParser.RepeatContext ctx) {
        // Erstelle Node mit Namen "Repeat". Verbinde exit des gesamten Teilgraphen
        // wieder mit Repeat, wobei repeat entry und exit knoten des gesamten
        // Teilgraphen ist
        String label = ctx.getStart().getText();
        int baseX = pos[0];
        int baseY = pos[1];
        String description = "";
        switch (label) {
            case "Repeat":
                description = "Repeats the strategy in the box as often as possible, at least " + ctx.NUMBER().getText()
                        + " and at most " + ctx.upperBound().getText()
                        + " times. The order in which results are processed is not specified.";
                break;
            case "RepeatS":
                description = "Repeats the strategy in the box as often as possible, at least " + ctx.NUMBER().getText()
                        + " and at most " + ctx.upperBound().getText()
                        + " times. Results are processed in the order in which they were generated.";
                break;
            default:
                description = "No description available.";
                break;
        }

        Node repeatNode = graph.addNode(label, getPosition(),
                label + "(Lower Bound = " + ctx.NUMBER().getText() +
                        " | Upper Bound = " + ctx.upperBound().getText() + ")",
                description);

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(repeatNode);

        pos[0] = baseX;
        pos[1] = baseY + Y_GAP;

        BuildResult repeatBody = visit(ctx.strategyTerm());
        repeatNode.data().addChild(repeatBody.entry.id());

        int targetLeftX = baseX + X_GAP;
        int dx = targetLeftX - repeatBody.minX;
        moveSubtree(repeatBody, dx, 0);
        repeatBody = makeResult(repeatBody.entry, repeatBody.exits, repeatBody.subTree); // Diese & letzte 3 Zeilen:
                                                                                         // repeatBody.minx soll nach
                                                                                         // Verschieben = baseX + X_GAP
                                                                                         // sein.

        subTrees.addAll(repeatBody.subTree);

        graph.addEdge(repeatNode, repeatBody.entry);

        for (Node exit : repeatBody.exits) {
            graph.addEdge(exit, repeatNode);
        }

        BuildResult result = makeResult(repeatNode, List.of(repeatNode), subTrees);
        result = addBoxAround(result, label);

        // Cursor zurückbewegen
        pos[0] = baseX;
        pos[1] = result.maxY;

        return result;
    }

    @Override
    public BuildResult visitProcessor(StrategyParser.ProcessorContext ctx) {
        String processorName = ctx.UCNAME().getText();
        ProcessorDeclaration declaration = null;
        String declaredName = null;

        for (String name : declarations.keySet()) {
            if (name.equalsIgnoreCase(processorName)) {
                declaration = declarations.get(name);
                declaredName = name;
                break;
            }
        }
        if (declaredName != null) {
            processorsInStrategyFile.putIfAbsent(declaredName, declaration);
        } else {
            processorsInStrategyFile.putIfAbsent(processorName, null);
        }

        String description = "Processor call. Succeeds if the processor transforms a basic obligation into a new obligation.";

        if (declaration != null && declaration.description() != null && !declaration.description().isBlank()) {
            description = declaration.description();
        }

        String parameters = "";
        if (ctx.parameterBlock() != null)
            parameters += ctx.parameterBlock().getText();

        // Node mit Namen des Prozessors erstellen (Entry und Exit ist die Node des
        // Prozessors selber)
        String formattedParam = "\n" + formatParameters(parameters);
        Node procNode = graph.addNode("Processor", getPosition(),
                "Processor: " + ctx.UCNAME().getText() + formattedParam, description);
        return makeResult(procNode, List.of(procNode), List.of(procNode));
    }

    @Override
    public BuildResult visitChoice(StrategyParser.ChoiceContext ctx) throws RuntimeException {

        // Erstelle Node mit Namen "First" Entry ist First exit sind die Enden der
        // jeweiligen Teilgraphen, die direkt in der StrategyTermList enthalten sind.
        int baseX = pos[0];
        int baseY = pos[1];
        String description = "";
        String label = ctx.getStart().getText();
        switch (label) {
            case "Any":
                description = "Evaluates strategies in arbitrary order, possibly in parallel. Fails if none succeeds.";
                break;
            case "First":
                description = "Evaluates strategies in the given order. Fails if none succeeds.";
                break;
            case "AnyK":
                if (ctx.NUMBER() == null)
                    throw new RuntimeException("AnyK expects a number.");
                description = "Evaluates multiple strategies in arbitrary order, with at most " + ctx.NUMBER().getText()
                        + " strategies in parallel. Succeeds if one strategy succeeds.";
                break;
            default:
                description = "Unexpected strategy type";
        }

        String displayLabel = label;
        String boxLabel = label;

        if (label.equals("First")) {
            displayLabel = "First\n{ordered}";
            boxLabel = "First {ordered}";
        } else if (label.equals("Any")) {
            displayLabel = "Any\n{unordered}";
            boxLabel = "Any {unordered}";
        } else if (label.equals("AnyK")) {
            displayLabel = "AnyK\n{max " + ctx.NUMBER().getText() + " parallel}";
            boxLabel = "AnyK {max " + ctx.NUMBER().getText() + "}";
        }

        Node entryNode = graph.addNode(label, getPosition(), displayLabel, description); // Erstelle First-Knoten und
                                                                                         // setze es als neuen Entry
                                                                                         // Node

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(entryNode);

        List<Node> childNodes = new ArrayList<>();

        List<Node> exitNodes = new ArrayList<>();
        StrategyParser.StrategyTermListContext allStrategyTerms = ctx.strategyTermList();

        int cursorX = baseX;

        int childIndex = 1;

        for (var i : allStrategyTerms.strategyTerm()) { // Gehe alle Nodes in First(x1,...,xn) durch und verbinde sie
                                                        // mit der "First" Node
            pos[0] = baseX;
            pos[1] = baseY + Y_GAP;

            BuildResult current = visit(i);
            entryNode.data().addChild(current.entry.id());

            // Teilbaum so verschieben, dass seine linke Kante bei cursorX liegt
            int dx = cursorX - current.minX;
            moveSubtree(current, dx, 0);
            current = makeResult(current.entry, current.exits, current.subTree);

            cursorX = current.maxX + X_GAP;

            childNodes.addAll(current.subTree);
            subTrees.addAll(current.subTree);

            if (label.equals("First")) {
                graph.addLabeledEdge(entryNode, current.entry, String.valueOf(childIndex));
            } else {
                graph.addEdge(entryNode, current.entry);
            }
            childIndex++;

            exitNodes.addAll(current.exits);
        }

        // Alle Kinder zusammen unter entryNode zentrieren
        int childCenterX = (getMinX(childNodes) + getMaxX(childNodes)) / 2;
        int centerDx = baseX - childCenterX;

        for (Node node : childNodes) {
            node.position().moveBy(centerDx, 0);
        }

        Node blockExit = graph.addNode("BlockExit", new Position(baseX, getMaxY(childNodes) + Y_GAP), "", "");
        subTrees.add(blockExit);

        for (Node exit : exitNodes) {
            graph.addEdge(exit, blockExit);
        }

        BuildResult result = makeResult(entryNode, List.of(blockExit), subTrees);
        result = addBoxAround(result, boxLabel);
        pos[0] = baseX;
        pos[1] = result.maxY;
        return result; // Setze die Exits von First auf Exit Knoten
    }

    @Override
    public BuildResult visitMaybe(StrategyParser.MaybeContext ctx) {
        // Erstelle Node mit Namen "Maybe". Entry ist Maybe innerer StrategyTerm wird
        // als Teilgraph gebaut, wobei Maybe
        // mit entry des inneren Teilgraphen verbunden wird. Exits von Maybe sind die
        // exits des Teilgraphen und maybe selber
        int baseX = pos[0];
        int baseY = pos[1];

        Node entryNode = graph.addNode("Maybe", getPosition(), "Maybe",
                "Applies the strategy at most once and succeeds even if it fails.");

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(entryNode);

        pos[0] = baseX;
        pos[1] = baseY + Y_GAP;

        BuildResult insideMaybe = visit(ctx.strategyTerm());
        entryNode.data().addChild(insideMaybe.entry.id());

        int targetLeftX = baseX + X_GAP; // Teil im Maybe nach rechts verschieben
        int dx = targetLeftX - insideMaybe.minX;

        moveSubtree(insideMaybe, dx, 0);
        insideMaybe = makeResult(insideMaybe.entry, insideMaybe.exits, insideMaybe.subTree);

        subTrees.addAll(insideMaybe.subTree);

        graph.addEdge(entryNode, insideMaybe.entry);

        List<Node> exits = new ArrayList<>();
        exits.add(entryNode);
        exits.addAll(insideMaybe.exits);

        BuildResult result = makeResult(entryNode, exits, subTrees);
        result = addBoxAround(result, "Maybe");

        // Cursor zurücksetzen
        pos[0] = baseX;
        pos[1] = result.maxY;

        return result;
    }

    @Override
    public BuildResult visitTimer(StrategyParser.TimerContext ctx) { // Erstelle Node mit Namen "Timer". Zeit als
                                                                     // Parameter dahinter und dann wie bei maybe den
                                                                     // StrategyTerm rechts.
        int baseX = pos[0];
        int baseY = pos[1];
        String label = ctx.getStart().getText();
        String description = "";
        String time = ctx.NUMBER().getText();
        switch (label) {
            case "Timer":
                description = "Executes the strategy in the box with a time limit of " + time
                        + "ms. Fails if the timeout is reached.";
                break;
            case "WallTimer":
                description = "Executes the strategy in the box with a wall-clock time limit of " + time
                        + "ms. Fails if the timeout is reached.";
                break;
            default:
                description = "Unexpected strategy type";
                break;
        }

        Node entryNode = graph.addNode(label, getPosition(), label + ": " + time, description);

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(entryNode);

        pos[0] = baseX;
        pos[1] = baseY + Y_GAP;
        BuildResult insideTimer = visit(ctx.strategyTerm());
        entryNode.data().addChild(insideTimer.entry.id());

        int targetLeftX = baseX + X_GAP;
        int dx = targetLeftX - insideTimer.minX;

        moveSubtree(insideTimer, dx, 0);
        insideTimer = makeResult(insideTimer.entry, insideTimer.exits, insideTimer.subTree);

        subTrees.addAll(insideTimer.subTree);
        graph.addEdge(entryNode, insideTimer.entry);

        BuildResult result = makeResult(entryNode, insideTimer.exits, subTrees);
        result = addBoxAround(result, label);
        // Cursor zurücksetzen
        pos[0] = baseX;
        pos[1] = result.maxY;

        return result;
    }

    @Override
    public BuildResult visitIfExpr(StrategyParser.IfExprContext ctx) { // If ist wie ein Prozessor mit extra Steps:
        // Condition if und else einfach als Knoten hinschreiben >>
        int baseX = pos[0];
        int baseY = pos[1];
        String params = ctx.parameterBlock().getText();
        String condition = extractParam(params, "Condition");
        Node entryNode = graph.addNode("Condition", getPosition(), "Condition[" + condition + "]",
                "Evaluates a condition and continues with the corresponding branch.");

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(entryNode);

        List<StrategyParser.StrategyTermContext> terms = ctx.strategyTerm();

        if (terms.isEmpty()) { // Wenn es keinen Part mit runden Klammern danach gibt

            String sub1 = extractParam(params, "Sub1");
            String sub2 = extractParam(params, "Sub2");

            if (sub1 == null) {
                throw new RuntimeException("If expression expects Sub1: " + params);
            }

            sub1 = sub1.replace("\"", "");

            if (sub2 != null) {
                sub2 = sub2.replace("\"", "");
            }

            pos[0] = baseX - X_GAP;
            pos[1] = baseY + Y_GAP;

            Node ifTruePart = graph.addNode("Reference", getPosition(), "Reference: " + sub1,
                    getStrategyDescription(sub1));

            subTrees.add(ifTruePart);
            graph.addLabeledEdge(entryNode, ifTruePart, "true");
            entryNode.data().addChild(ifTruePart.id());

            Node ifFalsePart = null;

            if (sub2 != null) {
                pos[0] = baseX + X_GAP;
                pos[1] = baseY + Y_GAP;

                ifFalsePart = graph.addNode("Reference", getPosition(), "Reference: " + sub2,
                        getStrategyDescription(sub2));

                subTrees.add(ifFalsePart);
                graph.addLabeledEdge(entryNode, ifFalsePart, "false");
            }

            int blockExitY = ifTruePart.position().y();

            if (ifFalsePart != null) {
                blockExitY = Math.max(blockExitY, ifFalsePart.position().y());
            }

            Node blockExit = graph.addNode("BlockExit", new Position(baseX, blockExitY + Y_GAP), "", "");
            subTrees.add(blockExit);

            graph.addEdge(ifTruePart, blockExit);

            if (ifFalsePart != null) {
                graph.addEdge(ifFalsePart, blockExit);
                entryNode.data().addChild(ifFalsePart.id());
            } else {
                graph.addLabeledEdge(entryNode, blockExit, "false");
            }

            BuildResult result = makeResult(entryNode, List.of(blockExit), subTrees);
            result = addBoxAround(result, "If");

            pos[0] = baseX;
            pos[1] = result.maxY;

            return result;

        } else { // Zweite Art von IfExpressions

            List<Node> exits = new ArrayList<>();
            List<Node> childNodes = new ArrayList<>();
            boolean falseGoesToExit = false;

            if (terms.size() == 2) {
                pos[0] = baseX - X_GAP;
            } else {
                pos[0] = baseX;
            }
            pos[1] = baseY + Y_GAP;

            BuildResult trueBranch = visit(terms.get(0));
            entryNode.data().addChild(trueBranch.entry.id());
            graph.addLabeledEdge(entryNode, trueBranch.entry, "true");

            exits.addAll(trueBranch.exits);
            subTrees.addAll(trueBranch.subTree);

            BuildResult trueGroup = makeResult(trueBranch.entry, trueBranch.exits, trueBranch.subTree);

            if (terms.size() == 2) {
                pos[0] = baseX + X_GAP;
                pos[1] = baseY + Y_GAP;

                BuildResult falseBranch = visit(terms.get(1));
                entryNode.data().addChild(falseBranch.entry.id());
                graph.addLabeledEdge(entryNode, falseBranch.entry, "false");

                exits.addAll(falseBranch.exits);
                subTrees.addAll(falseBranch.subTree);

                BuildResult falseGroup = makeResult(falseBranch.entry, falseBranch.exits, falseBranch.subTree);

                // Wie bei Choice: erst nebeneinander ohne Überlappung
                int cursorX = baseX;

                int dxTrue = cursorX - trueGroup.minX;
                moveSubtree(trueGroup, dxTrue, 0);
                trueGroup = makeResult(trueGroup.entry, trueGroup.exits, trueGroup.subTree);

                cursorX = trueGroup.maxX + X_GAP;

                int dxFalse = cursorX - falseGroup.minX;
                moveSubtree(falseGroup, dxFalse, 0);
                falseGroup = makeResult(falseGroup.entry, falseGroup.exits, falseGroup.subTree);

                // Beide Gruppen gemeinsam unter Condition zentrieren
                childNodes.addAll(trueGroup.subTree);
                childNodes.addAll(falseGroup.subTree);

                int childCenterX = (getMinX(childNodes) + getMaxX(childNodes)) / 2;
                int centerDx = baseX - childCenterX;

                for (Node node : childNodes) {
                    node.position().moveBy(centerDx, 0);
                }

            } else {
                falseGoesToExit = true;
            }

            Node blockExit = graph.addNode("BlockExit", new Position(baseX, getMaxY(subTrees) + Y_GAP), "", "");

            if (falseGoesToExit) {
                graph.addLabeledEdge(entryNode, blockExit, "false");
            }

            subTrees.add(blockExit);

            for (Node exit : exits) {
                graph.addEdge(exit, blockExit);
            }

            BuildResult result = makeResult(entryNode, List.of(blockExit), subTrees);
            result = addBoxAround(result, "If");

            pos[0] = baseX;
            pos[1] = result.maxY;

            return result;
        }
    }

    @Override
    public BuildResult visitCombine(StrategyParser.CombineContext ctx) { // Zum größten Teil übernommen von choice
        int baseX = pos[0];
        int baseY = pos[1];
        String description = "";
        String label = ctx.getStart().getText();
        switch (label) {
            case "Combine":
                description = "Combines multiple strategies into one strategy.";
                break;
            case "CombineParallel":
                description = "Combines multiple strategies and executes them in parallel.";
                break;
            case "CombineSequential":
                description = "Combines multiple strategies and executes them sequentially.";
                break;
            default:
                description = "Unexpected strategy type";
                break;
        }
        String displayLabel = label;
        String boxLabel = label;

        if (label.equals("CombineParallel")) {
            displayLabel = "Combine\n{parallel}";
            boxLabel = "CombineParallel {parallel}";
        } else if (label.equals("CombineSequential")) {
            displayLabel = "Combine\n{sequential}";
            boxLabel = "CombineSequential {sequential}";
        }

        Node entryNode = graph.addNode(label, getPosition(), displayLabel, description);

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(entryNode);

        List<Node> childNodes = new ArrayList<>();
        List<Node> exitNodes = new ArrayList<>();

        StrategyParser.StrategyTermListContext allStrategyTerms = ctx.strategyTermList();

        int cursorX = baseX;
        int childIndex = 1;

        for (StrategyParser.StrategyTermContext i : allStrategyTerms.strategyTerm()) {
            pos[0] = baseX;
            pos[1] = baseY + Y_GAP;

            BuildResult current = visit(i);
            entryNode.data().addChild(current.entry.id());

            int dx = cursorX - current.minX;
            moveSubtree(current, dx, 0);
            current = makeResult(current.entry, current.exits, current.subTree);
            cursorX = current.maxX + X_GAP;

            childNodes.addAll(current.subTree);
            subTrees.addAll(current.subTree);

            if (label.equals("CombineSequential")) {
                graph.addLabeledEdge(entryNode, current.entry, String.valueOf(childIndex));
                childIndex++;
            } else {
                graph.addEdge(entryNode, current.entry);
            }
            exitNodes.addAll(current.exits);
        }

        int childCenterX = (getMinX(childNodes) + getMaxX(childNodes)) / 2;
        int centerDx = baseX - childCenterX;

        for (Node node : childNodes) {
            node.position().moveBy(centerDx, 0);
        }

        Node blockExit = graph.addNode("BlockExit", new Position(baseX, getMaxY(childNodes) + Y_GAP), "", "");

        subTrees.add(blockExit);

        for (Node exit : exitNodes) {
            graph.addEdge(exit, blockExit);
        }

        BuildResult result = makeResult(entryNode, List.of(blockExit), subTrees);
        result = addBoxAround(result, boxLabel);

        pos[0] = baseX;
        pos[1] = result.maxY;

        return result;
    }

    @Override
    public BuildResult visitSolveProve(StrategyParser.SolveProveContext ctx) { // Viel von Maybe übernommen
        String label = ctx.getStart().getText();
        String description = "";
        int baseX = pos[0];
        int baseY = pos[1];

        switch (label) {
            case "Solve":
                description = "Evaluates strategy S and succeeds if the resulting truth value is known.";
                break;
            case "Prove":
                description = "Attempts to prove the current obligation using the strategy in the box.";
                break;
            case "Disprove":
                description = "Attempts to disprove the current obligation using the strategy in the box.";
                break;
            default:
                description = "Unexpected strategy type";
                break;
        }

        Node entryNode = graph.addNode(label, getPosition(), label, description);

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(entryNode); // entryNode ist auch Teil des SubTrees

        pos[0] = baseX;
        pos[1] = baseY + Y_GAP;
        BuildResult insideSolveProve = visit(ctx.strategyTerm());
        entryNode.data().addChild(insideSolveProve.entry.id());

        int targetLeftX = baseX + X_GAP;
        int dx = targetLeftX - insideSolveProve.minX;

        moveSubtree(insideSolveProve, dx, 0);
        insideSolveProve = makeResult(insideSolveProve.entry, insideSolveProve.exits, insideSolveProve.subTree);

        graph.addEdge(entryNode, insideSolveProve.entry);
        subTrees.addAll(insideSolveProve.subTree);

        List<Node> exits = new ArrayList<>();
        exits.addAll(insideSolveProve.exits);

        BuildResult result = makeResult(entryNode, exits, subTrees);
        result = addBoxAround(result, label);

        pos[0] = baseX;
        pos[1] = result.maxY;

        return result;
    }

    @Override
    public BuildResult visitDelay(StrategyParser.DelayContext ctx) {
        // Von Choice kopiert nur Zeit eingefügt
        int baseX = pos[0];
        int baseY = pos[1];
        String description = "";
        String time = ctx.NUMBER().getText();
        String label = ctx.getStart().getText();
        switch (label) {
            case "Delay":
                description = "Executes the strategy in the box after a delay of " + time + " ms.";
                break;
            case "AnyDelay":
                description = "Starts the first strategy immediately and the remaining strategies after " + time
                        + " ms. Succeeds as soon as one strategy succeeds.";
                break;
        }
        Node entryNode = graph.addNode(label, getPosition(), label + ": " + time, description);

        List<Node> subTrees = new ArrayList<>();
        subTrees.add(entryNode); // entryNode ist auch Teil des SubTrees

        List<Node> childNodes = new ArrayList<>();
        List<Node> exitNodes = new ArrayList<>();

        StrategyParser.StrategyTermListContext allStrategyTerms = ctx.strategyTermList();
        if (label.equals("Delay") && allStrategyTerms.strategyTerm().size() != 1) { // Delay erwartet nur eine Strategie
                                                                                    // als Parameter.
            throw new RuntimeException("Delay expects exactly one strategy.");
        }

        int cursorX = baseX;

        for (StrategyParser.StrategyTermContext i : allStrategyTerms.strategyTerm()) {
            pos[0] = baseX;
            pos[1] = baseY + Y_GAP;

            BuildResult current = visit(i);
            entryNode.data().addChild(current.entry.id());

            int dx = cursorX - current.minX;

            moveSubtree(current, dx, 0);
            current = makeResult(current.entry, current.exits, current.subTree);

            cursorX = current.maxX + X_GAP;

            childNodes.addAll(current.subTree);
            subTrees.addAll(current.subTree);

            graph.addEdge(entryNode, current.entry);
            exitNodes.addAll(current.exits);
        }

        int childCenterX = (getMinX(childNodes) + getMaxX(childNodes)) / 2;
        int centerDx = baseX - childCenterX;

        for (Node node : childNodes) {
            node.position().moveBy(centerDx, 0);
        }

        Node blockExit = graph.addNode("BlockExit", new Position(baseX, getMaxY(childNodes) + Y_GAP), "", "");

        subTrees.add(blockExit);

        for (Node exit : exitNodes) {
            graph.addEdge(exit, blockExit);
        }

        BuildResult result = makeResult(entryNode, List.of(blockExit), subTrees);
        result = addBoxAround(result, label);

        pos[0] = baseX;
        pos[1] = result.maxY;

        return result;
    }

    private String extractParam(String params, String key) {
        int start = params.indexOf(key + "=");

        if (start == -1)
            return null;

        start += (key + "=").length();

        int end;

        if (key.equals("Condition")) {
            end = params.indexOf(",Sub1=", start);

            if (end == -1) {
                end = params.lastIndexOf("]");
            }
        } else if (key.equals("Sub1")) {
            end = params.indexOf(",Sub2=", start);

            if (end == -1) {
                end = params.lastIndexOf("]");
            }
        } else {
            end = params.lastIndexOf("]");
        }

        if (end == -1)
            return null;

        return params.substring(start, end);
    }

    private String formatParameters(String text) {
        StringBuilder result = new StringBuilder();
        int depth = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '[') {
                result.append(c);
                depth++;
                result.append("\n");
                result.append("  ".repeat(depth));
            } else if (c == ',') {
                result.append(c);
                result.append("\n");
                result.append("  ".repeat(depth));
            } else if (c == ']') {
                depth--;
                result.append("\n");
                result.append("  ".repeat(depth));
                result.append(c);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    private int getMinX(List<Node> nodes) {
        int min = Integer.MAX_VALUE;
        for (Node node : nodes) {
            min = Math.min(min, node.position().x());
        }
        return min;
    }

    private int getMaxX(List<Node> nodes) {
        int max = Integer.MIN_VALUE;
        for (Node node : nodes) {
            max = Math.max(max, node.position().x());
        }
        return max;
    }

    private int getMinY(List<Node> nodes) {
        int min = Integer.MAX_VALUE;
        for (Node node : nodes) {
            min = Math.min(min, node.position().y());
        }
        return min;
    }

    private int getMaxY(List<Node> nodes) {
        int max = Integer.MIN_VALUE;
        for (Node node : nodes) {
            max = Math.max(max, node.position().y());
        }
        return max;
    }

    private void moveSubtree(BuildResult result, int dx, int dy) {
        for (Node node : result.subTree) {
            node.position().moveBy(dx, dy);
        }
    }

    private BuildResult makeResult(Node entry, List<Node> exits, List<Node> subTree) {
        return new BuildResult(entry, exits, subTree, getMinX(subTree), getMaxX(subTree), getMinY(subTree),
                getMaxY(subTree));
    }

    private BuildResult addBoxAround(BuildResult result, String label) {
        int padding = 25;

        int x = result.minX - padding;
        int y = result.minY - padding;
        int width = result.maxX - result.minX + 2 * padding;
        int height = result.maxY - result.minY + 2 * padding;

        Node boxNode = graph.addBoxNode(label, new Position(x, y), width + X_GAP / 2, height + Y_GAP / 2);

        List<Node> newSubTree = new ArrayList<>();
        newSubTree.add(boxNode);
        newSubTree.addAll(result.subTree);

        return makeResult(result.entry, result.exits, newSubTree);
    }

    private String getStrategyDescription(String name) {
        String description = strategyDescriptions.get(name);

        if (description == null || description.isBlank()) {
            return "Variable which refers to another defined strategy term.";
        }

        return description;
    }
}