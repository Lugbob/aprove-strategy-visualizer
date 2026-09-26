package de.luca.parsing;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import de.luca.grammar.StrategyDeclarationLexer;
import de.luca.grammar.StrategyDeclarationParser;

//Class for processor declarations
public class DeclarationParser {
    private ArrayList<String> duplicateDeclarations = new ArrayList<>();

    public Map<String, ProcessorDeclaration> getProcessors(Path declarationPath) throws Exception {
        String declarationText = Files.readString(declarationPath);
        CharStream input = CharStreams.fromString(declarationText);
        StrategyDeclarationLexer lexer = new StrategyDeclarationLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        StrategyDeclarationParser parser = new StrategyDeclarationParser(tokens);

        StrategyDeclarationParser.ProgramContext tree = parser.program();

        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new RuntimeException("Could not parse declaration file.");
        }

        DeclarationVisitor visitor = new DeclarationVisitor();
        visitor.visit(tree);
        duplicateDeclarations = visitor.getMultipleDeclarations();

        return visitor.getDeclarations();
    }

    public ArrayList<String> getDuplicateDeclarations() {
        return duplicateDeclarations;
    }
}
