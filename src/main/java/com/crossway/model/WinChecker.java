package com.crossway.model;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class WinChecker implements WinningRule {

    @Override
    public Optional<PlayerColor> getWinner(Board board) {
        if (checkWin(board, PlayerColor.WHITE)) {
            return Optional.of(PlayerColor.WHITE);
        }
        if (checkWin(board, PlayerColor.BLACK)) {
            return Optional.of(PlayerColor.BLACK);
        }
        return Optional.empty();
    }

    private boolean checkWin(Board board, PlayerColor color) {
        boolean[][] visited = new boolean[Board.BOARD_SIZE][Board.BOARD_SIZE];
        Queue<Position> queue = new ArrayDeque<>();

        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            Position startPos = (color == PlayerColor.WHITE) ? new Position(0, i) : new Position(i, 0);
            if (isStoneOfColor(board, startPos, color)) {
                queue.add(startPos);
                visited[startPos.x()][startPos.y()] = true;
            }
        }

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (hasReachedOppositeSide(current, color)) {
                return true;
            }

            for (Position neighbor : board.getAdjacentPositions(current)) {
                if (!visited[neighbor.x()][neighbor.y()] && isStoneOfColor(board, neighbor, color)) {
                    visited[neighbor.x()][neighbor.y()] = true;
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    private boolean isStoneOfColor(Board board, Position pos, PlayerColor color) {
        return board.getStone(pos)
                .map(color::equals)
                .orElse(false);
    }

    private boolean hasReachedOppositeSide(Position pos, PlayerColor color) {
        return (color == PlayerColor.WHITE && pos.x() == Board.BOARD_SIZE - 1) ||
                (color == PlayerColor.BLACK && pos.y() == Board.BOARD_SIZE - 1);
    }
}