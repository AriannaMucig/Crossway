package com.crossway.model;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

    @Test
    void testFirstTurnIsBlack() {
        Game game = new Game();
        assertThat(game.getCurrentTurn()).isEqualTo(PlayerColor.BLACK);
    }

    @Test
    void testAlternateTurns(){
        Game game =new Game();

        game.playMove(new Position(5, 6));
        assertThat(game.getCurrentTurn()).isEqualTo(PlayerColor.WHITE);

        game.playMove(new Position(8, 6));
        assertThat(game.getCurrentTurn()).isEqualTo(PlayerColor.BLACK);
    }
}
