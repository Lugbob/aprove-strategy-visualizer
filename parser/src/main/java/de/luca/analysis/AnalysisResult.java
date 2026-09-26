package de.luca.analysis;

import java.util.List;
import java.util.Map;

public record AnalysisResult(
        List<String> processorsWithoutDeclaration,
        int processorsWithoutDeclarationCount,
        List<String> declaredButNotInStrategyFile,
        int declaredButNotInStrategyFileCount,
        List<String> declaredProcessors,
        int declaredProcessorCount,
        List<String> processorsInStrategyFile,
        int processorsInStrategyFileCount,
        List<String> processorsWithMultipleDeclaration,
        int processorsWithMultipleDeclarationCount,
        Map<String, Integer> strategyConstructCounts,
        List<String> strategyDefinitions,
        int strategyDefinitionCount,
        List<String> duplicateStrategyDefinitions,
        int duplicateStrategyDefinitionCount,
        List<List<String>> referenceCycles,
        Map<String, List<String>> strategyReferences,
        Map<String, List<String>> strategyReferencedBy,
        Map<String, String> declaredProcessorsdefaults

) {
}
