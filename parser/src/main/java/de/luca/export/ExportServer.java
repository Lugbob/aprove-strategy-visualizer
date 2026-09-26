//This method "ExportServer" was developed with AI assistance; final logic was reviewed manually
package de.luca.export;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import de.luca.graph.Graph;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

//Da die StrategyExport Klasse in Java geschrieben wurde, muss ein zweiter Server
//aufgesetzt werden, der das REACT frontend mit der Java-Klasse verbindet, sodass export()
//aufgerufen werden kann
public class ExportServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Wenn eine Anfrage an \export kommt, wird sie an handleExport übergeben:
        server.createContext("/export", exchange -> handleExport(exchange));

        server.start();
        System.out.println("Export server running on http://localhost:" + PORT);
    }

    private static void handleExport(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);

        // Handle preflight request
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        // Deserialisiere die JSON in einen Graphen.
        Map<String, Graph> graphs = mapper.readValue(
                exchange.getRequestBody(),
                new TypeReference<Map<String, Graph>>() {
                });

        String result = StrategyExporter.export(graphs);
        sendTextResponse(exchange, result);
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
    }

    private static void sendTextResponse(HttpExchange exchange, String text) throws IOException {
        byte[] response = text.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().add(
                "Content-Type",
                "text/plain; charset=UTF-8");

        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.getResponseBody().close();
    }
}