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

    @Test
    void testAdjacencyAtCornersAndEdges() {
        Board board = new Board();
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(0, 18);
        Position pos3 = new Position(18, 18);
        Position pos4 = new Position(18, 0);
        Position pos5 = new Position(0, 5);
        Position pos6 = new Position(18, 5);
        Position pos7 = new Position(10, 0);
        Position pos8 = new Position(10, 18);

        List<Position> expectedAdjacents1 = List.of(new Position(0, 1), new Position(1, 1), new Position(1, 0));
        List<Position> adjacents1 = board.getAdjacentPositions(pos1);

        List<Position> expectedAdjacents2 = List.of(new Position(0, 17), new Position(1, 17), new Position(1, 18));
        List<Position> adjacents2 = board.getAdjacentPositions(pos2);

        List<Position> expectedAdjacents3 = List.of(new Position(18, 17), new Position(17, 17), new Position(17, 18));
        List<Position> adjacents3 = board.getAdjacentPositions(pos3);

        List<Position> expectedAdjacents4 = List.of(new Position(17, 0), new Position(17, 1), new Position(18, 1));
        List<Position> adjacents4 = board.getAdjacentPositions(pos4);

        List<Position> expectedAdjacents5 = List.of(new Position(0, 4), new Position(1, 4), new Position(1, 5), new Position(1, 6), new Position(0, 6));
        List<Position> adjacents5 = board.getAdjacentPositions(pos5);

        List<Position> expectedAdjacents6 = List.of(new Position(18, 4), new Position(17, 4), new Position(17, 5), new Position(17, 6), new Position(18, 6));
        List<Position> adjacents6 = board.getAdjacentPositions(pos6);

        List<Position> expectedAdjacents7 = List.of(new Position(9, 0), new Position(9, 1), new Position(10, 1), new Position(11, 1), new Position(11, 0));
        List<Position> adjacents7 = board.getAdjacentPositions(pos7);

        List<Position> expectedAdjacents8 = List.of(new Position(9, 18), new Position(9, 17), new Position(10, 17), new Position(11, 17), new Position(11, 18));
        List<Position> adjacents8 = board.getAdjacentPositions(pos8);

        assertThat(adjacents1).containsExactlyInAnyOrderElementsOf(expectedAdjacents1);
        assertThat(adjacents2).containsExactlyInAnyOrderElementsOf(expectedAdjacents2);
        assertThat(adjacents3).containsExactlyInAnyOrderElementsOf(expectedAdjacents3);
        assertThat(adjacents4).containsExactlyInAnyOrderElementsOf(expectedAdjacents4);
        assertThat(adjacents5).containsExactlyInAnyOrderElementsOf(expectedAdjacents5);
        assertThat(adjacents6).containsExactlyInAnyOrderElementsOf(expectedAdjacents6);
        assertThat(adjacents7).containsExactlyInAnyOrderElementsOf(expectedAdjacents7);
        assertThat(adjacents8).containsExactlyInAnyOrderElementsOf(expectedAdjacents8);
    }

    @Test
    void testDiagonalWhenNoCrosscut(){
        Board board = new Board();
        board.placeStone(new Position(4,5), PlayerColor.BLACK);

        Position pos1 =new Position(3,4);
        Position pos2 =new Position(3,6);
        Position pos3 =new Position(5,4);
        Position pos4 =new Position(5,6);

        board.placeStone(pos1, PlayerColor.BLACK);
        board.placeStone(pos2, PlayerColor.BLACK);
        board.placeStone(pos3, PlayerColor.BLACK);
        board.placeStone(pos4, PlayerColor.BLACK);

        assertThat(board.isCellEmpty(pos1)).isFalse();
        assertThat(board.getStone(pos1)).isEqualTo(PlayerColor.BLACK);

        assertThat(board.isCellEmpty(pos2)).isFalse();
        assertThat(board.getStone(pos2)).isEqualTo(PlayerColor.BLACK);

        assertThat(board.isCellEmpty(pos3)).isFalse();
        assertThat(board.getStone(pos3)).isEqualTo(PlayerColor.BLACK);

        assertThat(board.isCellEmpty(pos4)).isFalse();
        assertThat(board.getStone(pos4)).isEqualTo(PlayerColor.BLACK);
    }

    @Test
    void TestDiagonalWhenCrosscut(){
        Board board = new Board();
        board.placeStone(new Position(1,1), PlayerColor.BLACK);
        board.placeStone(new Position(1,0), PlayerColor.WHITE);
        board.placeStone(new Position(0,1), PlayerColor.WHITE);
        assertThrows(IllegalArgumentException.class, () -> board.placeStone(new Position(0,0), PlayerColor.BLACK));

    }
}
