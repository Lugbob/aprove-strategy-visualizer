package de.luca.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    private int x;
    private int y;

    @JsonCreator
    public Position(@JsonProperty("x") int x,
            @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int[] arr) {
        this(arr[0], arr[1]);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveBy(int x, int y) {
        this.x += x;
        this.y += y;
    }
}