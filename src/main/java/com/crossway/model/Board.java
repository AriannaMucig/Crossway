package com.crossway.model;

public class Board {
    private final Cell[][] grid;

    public Board() {
        this.grid = new Cell[19][19];
        for (int r = 0; r < 19; r++) {
            for (int c = 0; c < 19; c++) {
                grid[r][c] = new Cell();
            }
        }
    }

    public void placeStone(Position pos, PlayerColor color){
        checkOutOfBoard(pos);
        grid[pos.x()][pos.y()].setColor(color);
    }

    public boolean isEmpty() {
        for (int r = 0; r < 19; r++) {
            for (int c = 0; c < 19; c++) {
                if(!grid[r][c].isEmpty())
                    return false;
            }
        }
        return true;
    }
    public boolean isCellEmpty(Position pos){
        checkOutOfBoard(pos);
        return grid[pos.x()][pos.y()].isEmpty();
    }

    public PlayerColor getStone(Position pos){
        checkOutOfBoard(pos);
        return grid[pos.x()][pos.y()].getColor();
    }

    private void checkOutOfBoard(Position pos){
        if (pos.x()<0 | pos.x() >=19|pos.y()<0 | pos.y() >=19){
            throw new IllegalArgumentException("Position "+ pos + "is out of the board");
        }
    }

}
