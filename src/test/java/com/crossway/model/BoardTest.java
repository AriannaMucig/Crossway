package com.crossway.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {
    @Test
    void testInitialBoardIsEmpty(){
        Board board = new Board();
        assertThat(board.isEmpty()).isTrue();
    }


