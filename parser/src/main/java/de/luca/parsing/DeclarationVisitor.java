package de.luca.parsing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import de.luca.grammar.StrategyDeclarationParser;
import de.luca.grammar.StrategyDeclarationParserBaseVisitor;

public class DeclarationVisitor extends StrategyDeclarationParserBaseVisitor<Void> {

    private final LinkedHashMap<String, ProcessorDeclaration> declarations = new LinkedHashMap<>();
    private final ArrayList<String> multipleDeclarations = new ArrayList<>();

    public ArrayList<String> getMultipleDeclarations() {
        return multipleDeclarations;
    }

    public Map<String, ProcessorDeclaration> getDeclarations() {
        return declarations;
    }

    @Override
    public Void visitProgram(StrategyDeclarationParser.ProgramContext ctx) {
        for (var declaration : ctx.declaration()) {
            visitDeclaration(declaration);
        }
        return null;
    }

    @Override
    public Void visitDeclaration(StrategyDeclarationParser.DeclarationContext ctx) {
        String name = ctx.namePart().getText();
        String className = ctx.qualifiedName().getText();

        String defaults = "";
        if (ctx.parameterBlock() != null) {
            defaults = ctx.parameterBlock().getText();
        }

        String description = "";

        if (!ctx.DESCRIPTION().isEmpty()) {
            StringBuilder builder = new StringBuilder();

            for (var desc : ctx.DESCRIPTION()) {
                String text = desc.getText().replaceFirst("#@description", "").trim();

                if (!builder.isEmpty()) {
                    builder.append(" ");
                }
                builder.append(text);
            }
            description = builder.toString();
        }

        if (declarations.containsKey(name)) {
            System.out.println("Multiple declarations: " + name);
            multipleDeclarations.add(name);
        }

        declarations.put(name, new ProcessorDeclaration(name, className, defaults, description));
        return null;
    }
}