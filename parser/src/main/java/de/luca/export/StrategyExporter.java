package de.luca.export;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.luca.graph.Graph;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

//Formatiert den Graph zurück in einen strategy file.
public class StrategyExporter {

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Graph> graphs = mapper.readValue(
                new File(args[0]),
                new TypeReference<Map<String, Graph>>() {
                });

        String strategyFile = export(graphs);

        Files.writeString(Path.of("edited.strategy"), strategyFile);

        System.out.println("Strategy exported to edited.strategy");
    }

    public static String export(Map<String, Graph> graphs) {
        StrategyFileGenerator generator = new StrategyFileGenerator();
        return generator.generateFile(graphs);
    }
}