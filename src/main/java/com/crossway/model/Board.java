package com.crossway.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Board {
    private final Cell[][] grid;
    public static final int BOARD_SIZE = 19;

    public Board() {
        this.grid = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                grid[r][c] = new Cell();
            }
        }
    }

    public void placeStone(Position pos, PlayerColor color) {
        checkOutOfBoard(pos);
        if (!isCellEmpty(pos)) {
            throw new IllegalArgumentException("Cell at position " + pos + " is already occupied");
        }
        if (checkCrosscut(pos, color)) {
            throw new IllegalArgumentException("Placement at " + pos + " is forbidden due to Crosscut rule");
        }
        grid[pos.x()][pos.y()].setColor(color);
    }

    public boolean isEmpty() {
        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isEmpty);
    }

    public boolean isCellEmpty(Position pos) {
        checkOutOfBoard(pos);
        return grid[pos.x()][pos.y()].isEmpty();
    }

    public Optional<PlayerColor> getStone(Position pos) {
        checkOutOfBoard(pos);
        return grid[pos.x()][pos.y()].getColor();
    }

    private void checkOutOfBoard(Position pos) {
        if (!isInsideBoard(pos)) {
            throw new IllegalArgumentException("Position " + pos + " is out of the board");
        }
    }

    private boolean checkCrosscut(Position pos, PlayerColor color) {
        PlayerColor oppositeColor = color.opposite();

        int[][] offsets = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] offset : offsets) {
            int dx = offset[0];
            int dy = offset[1];

            Position diag = new Position(pos.x() + dx, pos.y() + dy);
            Position adj1 = new Position(pos.x() + dx, pos.y());
            Position adj2 = new Position(pos.x(), pos.y() + dy);

            if (isInsideBoard(diag) && isInsideBoard(adj1) && isInsideBoard(adj2)) {
                boolean isDiagSameColor = getStone(diag).map(c -> c == color).orElse(false);
                boolean isAdj1Opposite = getStone(adj1).map(c -> c == oppositeColor).orElse(false);
                boolean isAdj2Opposite = getStone(adj2).map(c -> c == oppositeColor).orElse(false);

                if (isDiagSameColor && isAdj1Opposite && isAdj2Opposite) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Position> getAdjacentPositions(Position center) {
        checkOutOfBoard(center);
        int[] offsets = {-1, 0, 1};

        return Arrays.stream(offsets)
                .boxed()
                .flatMap(dx -> Arrays.stream(offsets)
                        .filter(dy -> !(dx == 0 && dy == 0))
                        .mapToObj(dy -> new Position(center.x() + dx, center.y() + dy)))
                .filter(this::isInsideBoard)
                .toList();
    }

    private boolean isInsideBoard(Position pos) {
        return pos.x() >= 0 && pos.x() < BOARD_SIZE && pos.y() >= 0 && pos.y() < BOARD_SIZE;
    }

    public void applyPieRule(Position pos, PlayerColor color) {
        checkOutOfBoard(pos);
        grid[pos.x()][pos.y()].setColor(color);
    }

}
