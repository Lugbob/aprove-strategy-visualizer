package de.luca.graph;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Graph {
    private int edgeCounter = -1;
    private int nodeCounter = -1;
    private String root;
    private String description = "";

    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    public Graph() {
    }

    @JsonCreator
    public Graph(@JsonProperty("root") String root, @JsonProperty("nodes") List<Node> nodes,
            @JsonProperty("edges") List<Edge> edges, @JsonProperty("description") String description) {
        this.root = root;
        if (nodes != null) {
            this.nodes.addAll(nodes);
        }

        if (edges != null) {
            this.edges.addAll(edges);
        }
        if (description != null) {
            this.description = description;
        } else {
            description = "";
        }
    }

    public Node addNode(String type, Position position, String label, String description) {
        nodeCounter++;
        String id = type + "-" + nodeCounter;
        Node node = new Node(id, position, new NodeData(label, false, 0, 0, description));
        nodes.add(node);
        return node;
    }

    public Node addBoxNode(String label, Position position, int width, int height) {
        nodeCounter++;
        String id = "Box-" + nodeCounter;
        Node node = new Node(id, position, new NodeData(label, true, width, height, ""));
        nodes.add(node);
        return node;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void addEdge(Node node1, Node node2) {
        edgeCounter++;
        String id = "edge-" + edgeCounter;
        edges.add(new Edge(id, node1.id(), node2.id(), null, null));
    }

    public void addEdge(Node node1, Node node2, String sequenceOperator) {
        edgeCounter++;
        String id = "edge-" + edgeCounter;
        edges.add(new Edge(id, node1.id(), node2.id(), sequenceOperator, null));
    }

    public void addLabeledEdge(Node source, Node target, String label) {
        edgeCounter++;
        String id = "edge-" + edgeCounter;
        edges.add(new Edge(id, source.id(), target.id(), null, label));
    }

    // Methoden für Jackson:

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRoot() {
        return root;
    }

    public String getDescription() {
        return description;
    }
}