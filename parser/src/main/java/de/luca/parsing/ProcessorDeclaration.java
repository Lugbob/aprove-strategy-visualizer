package de.luca.parsing;

public record ProcessorDeclaration(
        String name,
        String className,
        String defaults,
        String description) {
}
