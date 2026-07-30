package com.crossway.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {
    @Test
    void testInitialBoardIsEmpty() {
        Board board = new Board();
        assertThat(board.isEmpty()).isTrue();
    }

    @Test
    void testPlaceStoneInsideBoard() {
        Board board = new Board();
        Position pos = new Position(1, 1);

        board.placeStone(pos, PlayerColor.BLACK);
        assertThat(board.isCellEmpty(pos)).isFalse();
        assertThat(board.getStone(pos)).isEqualTo(PlayerColor.BLACK);
    }

    @Test
    void testPlaceStoneOutsideBoardThrowsException() {
        Board board = new Board();
        Position pos1 = new Position(1, -11);
        Position pos2 = new Position(19, 1);


        assertThrows(IllegalArgumentException.class, () -> board.placeStone(pos1, PlayerColor.BLACK));
        assertThrows(IllegalArgumentException.class, () -> board.placeStone(pos2, PlayerColor.WHITE));
    }

    @Test
    void testPlaceStoneOnOccupiedCellThrowsException() {
        Board board = new Board();
        Position pos = new Position(1, 1);
        board.placeStone(pos, PlayerColor.BLACK);

        assertThrows(IllegalArgumentException.class, () -> board.placeStone(pos, PlayerColor.BLACK));
        assertThrows(IllegalArgumentException.class, () -> board.placeStone(pos, PlayerColor.WHITE));
    }

    @Test
    void testAdjacencyEightDirections() {
        Board board = new Board();
        Position center = new Position(5, 5);

        List<Position> expectedAdjacents = List.of(new Position(4, 4), new Position(4, 5), new Position(4, 6), new Position(5, 4), new Position(5, 6), new Position(6, 4), new Position(6, 5), new Position(6, 6));

        List<Position> adjacents = board.getAdjacentPositions(center);

        assertThat(adjacents).containsExactlyInAnyOrderElementsOf(expectedAdjacents);

    }
}
