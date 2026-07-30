package com.crossway.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

}
