package com.crossway.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {
    @Test
    void testInitialBoardIsEmpty() {
        Board board = new Board();
        assertThat(board.isEmpty()).isTrue();
    }

    @Test
    void testPlaceStoneInsideBoard(){
        Board board = new Board();
        Position pos = new Position(1,1);

        board.placeStone(pos, PlayerColor.BLACK);
        assertThat(board.isCellEmpty(pos)).isFalse();
        assertThat(board.getStone(pos)).isEqualTo(PlayerColor.BLACK);
    }
    @Test
    void testPlaceStoneOutsideBoardThrowsException(){
        Board board = new Board();
        Position pos1 = new Position(1,-11);
        Position pos2 = new Position(19,1);


        assertThrows(IllegalArgumentException.class, () -> board.placeStone(pos1, PlayerColor.BLACK));
        assertThrows(IllegalArgumentException.class, () -> board.placeStone(pos2, PlayerColor.WHITE));
    }

}
