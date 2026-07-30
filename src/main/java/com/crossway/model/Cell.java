package com.crossway.model;

public class Cell {
    private PlayerColor color =null;

    public boolean isEmpty(){
        return this.color==null;
    }
    public void setColor(PlayerColor color){
        this.color=color;
    }
    public PlayerColor getColor(){
        return this.color;
    }
}
