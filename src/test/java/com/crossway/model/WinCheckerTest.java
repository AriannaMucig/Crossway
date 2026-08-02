package com.crossway.model;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WinCheckerTest {
    @Test
    void testNoWinnerOnEmptyBoard(){
        Game game = new Game();
        assertThat(game.getWinner()).isNull();
    }

    @Test
    void testWhiteWinsWithStraightLine(){
        Game game = new Game();
        for (int i = 0; i < 19; i++) {
            game.playMove(new Position(i,0));
            game.playMove(new Position(i,7));
        }
        assertThat(game.getWinner()).isEqualTo(PlayerColor.WHITE);
    }
}
