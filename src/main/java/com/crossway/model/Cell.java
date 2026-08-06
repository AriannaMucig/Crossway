package com.crossway.model;

import java.util.Optional;

public class Cell {
    private PlayerColor color = null;

    public boolean isEmpty() {
        return this.color == null;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public Optional<PlayerColor> getColor() {
        return Optional.ofNullable(this.color);
    }
}
