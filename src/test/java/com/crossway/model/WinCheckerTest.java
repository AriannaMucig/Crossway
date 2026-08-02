package com.crossway.model;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WinCheckerTest {
    @Test
    void testNoWinnerOnEmptyBoard(){
        Game game = new Game();
        assertThat(game.getWinner()).isNull();
    }
}
