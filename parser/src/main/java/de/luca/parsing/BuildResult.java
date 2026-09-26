package de.luca.parsing;

import java.util.List;

import de.luca.graph.Node;

public class BuildResult {
    public Node entry;
    public List<Node> exits;
    public List<Node> subTree;
    public int minX;
    public int maxX;
    public int minY;
    public int maxY;

    public BuildResult(Node entry, List<Node> exits, List<Node> subTree, int minX, int maxX, int minY, int maxY) {
        this.entry = entry;
        this.exits = exits;
        this.subTree = subTree;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
}