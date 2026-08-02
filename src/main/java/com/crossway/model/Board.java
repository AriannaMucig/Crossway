package com.crossway.model;

import java.util.ArrayList;
import java.util.List;

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

    public void placeStone(Position pos, PlayerColor color) {
        checkOutOfBoard(pos);
        if (!isCellEmpty(pos)) {
            throw new IllegalArgumentException("Cell at position " + pos + " is already occupied");
        }
        if(checkCrosscut(pos, color)){
            throw new IllegalArgumentException("Placement at " + pos + " is forbidden due to Crosscut rule");
        }
        grid[pos.x()][pos.y()].setColor(color);
    }

    public boolean isEmpty() {
        for (int r = 0; r < 19; r++) {
            for (int c = 0; c < 19; c++) {
                if (!grid[r][c].isEmpty())
                    return false;
            }
        }
        return true;
    }

    public boolean isCellEmpty(Position pos) {
        checkOutOfBoard(pos);
        return grid[pos.x()][pos.y()].isEmpty();
    }

    public PlayerColor getStone(Position pos) {
        checkOutOfBoard(pos);
        return grid[pos.x()][pos.y()].getColor();
    }

    private void checkOutOfBoard(Position pos) {
        if (!isInsideBoard(pos)) {
            throw new IllegalArgumentException("Position " + pos + "is out of the board");
        }
    }

    private boolean checkCrosscut(Position pos, PlayerColor color) {
        PlayerColor oppositeColor = (color == PlayerColor.BLACK) ? PlayerColor.WHITE : PlayerColor.BLACK;

        int[][] offsets = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] offset : offsets) {
            int dx = offset[0];
            int dy = offset[1];

            Position diag = new Position(pos.x() + dx, pos.y() + dy);
            Position adj1 = new Position(pos.x() + dx, pos.y());
            Position adj2 = new Position(pos.x(), pos.y() + dy);

            if (isInsideBoard(diag) && isInsideBoard(adj1) && isInsideBoard(adj2)) {
                if (getStone(diag) == color && getStone(adj1) == oppositeColor && getStone(adj2) == oppositeColor) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Position> getAdjacentPositions(Position center) {
        checkOutOfBoard(center);
        List<Position> adjacentPositions = new ArrayList<>();
        if (center.x() - 1 >= 0) {
            adjacentPositions.add(new Position(center.x() - 1, center.y()));
            if (center.y() - 1 >= 0) {
                adjacentPositions.add(new Position(center.x() - 1, center.y() - 1));
            }
            if (center.y() + 1 < 19) {
                adjacentPositions.add(new Position(center.x() - 1, center.y() + 1));
            }
        }
        if (center.x() + 1 < 19) {
            adjacentPositions.add(new Position(center.x() + 1, center.y()));
            if (center.y() - 1 >= 0) {
                adjacentPositions.add(new Position(center.x() + 1, center.y() - 1));
            }
            if (center.y() + 1 < 19) {
                adjacentPositions.add(new Position(center.x() + 1, center.y() + 1));
            }
        }

        if (center.y() - 1 >= 0) {
            adjacentPositions.add(new Position(center.x(), center.y() - 1));
        }
        if (center.y() + 1 < 19) {
            adjacentPositions.add(new Position(center.x(), center.y() + 1));
        }
        return adjacentPositions;
    }

    private boolean isInsideBoard(Position pos) {
        return pos.x() >= 0 && pos.x() < 19 && pos.y() >= 0 && pos.y() < 19;
    }

    public void changeStone(Position pos,PlayerColor color){
        checkOutOfBoard(pos);
        grid[pos.x()][pos.y()].setColor(color);
    }

}
