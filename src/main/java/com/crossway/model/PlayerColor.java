package com.crossway.model;

public enum PlayerColor {
    BLACK,
    WHITE;

    public PlayerColor opposite() {
        return this == BLACK ? WHITE : BLACK;
    }
}
