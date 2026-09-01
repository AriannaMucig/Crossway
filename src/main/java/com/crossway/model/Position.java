package com.crossway.model;

public record Position(int x, int y) {
    @Override
    public String toString() {
        return "(" + (char) ('A' + y) + "," + (x + 1) + ")";
    }
}
