package de.luca.graph;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeData {
    String label;
    boolean isBox;
    int width;
    int height;
    String description;
    List<String> children = new ArrayList<>();
    String next;
    String sequenceOperator;

    // Konstruktor, der von Jackson genutzt wird um NodeData aus einer JSON zu
    // erstellen.
    @JsonCreator
    public NodeData(@JsonProperty("label") String label, @JsonProperty("box") boolean box,
            @JsonProperty("width") int width, @JsonProperty("height") int height,
            @JsonProperty("description") String description, @JsonProperty("next") String next,
            @JsonProperty("sequenceOperator") String sequenceOperator,
            @JsonProperty("children") List<String> children) {
        this.label = label;
        this.isBox = box;
        this.width = width;
        this.height = height;
        this.description = description;
        this.children = children;
        this.next = next;
        this.sequenceOperator = sequenceOperator;
    }

    public NodeData(String pLabel, boolean pIsBox, int pWidth, int pHeight, String pDescription) {
        this(pLabel, pIsBox, pWidth, pHeight, pDescription, null, null, new ArrayList<>());
    }

    public NodeData(String pLabel, boolean pIsBox, int pWidth, int pHeight, String pDescription, String next,
            String sequenceOperator) {
        this(pLabel, pIsBox, pWidth, pHeight, pDescription, next, sequenceOperator, new ArrayList<>());
    }

    public String label() {
        return label;
    }

    public boolean isBox() {
        return isBox;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public String description() {
        return description;
    }

    public List<String> children() {
        return children;
    }

    public void addChild(String childId) {
        children.add(childId);
    }

    public void setNext(String next, String operator) {
        this.next = next;
        this.sequenceOperator = operator;
    }

    public String sequenceOperator() {
        return sequenceOperator;
    }

    // Methoden für Jackson:

    public String getNext() {
        return next;
    }

    public String getSequenceOperator() {
        return sequenceOperator;
    }

    public String getLabel() {
        return label;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getChildren() {
        return children;
    }
}
