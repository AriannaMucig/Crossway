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

    @Test
    void testTurnDoesNotChangeWithInvalidMove(){
        Game game =new Game();

        game.playMove(new Position(5, 6));
        assertThat(game.getCurrentTurn()).isEqualTo(PlayerColor.WHITE);
        assertThrows(IllegalArgumentException.class, () -> game.playMove(new Position(20, 6)));
        assertThat(game.getCurrentTurn()).isEqualTo(PlayerColor.WHITE);
    }

    @Test
    void testPieRuleSwap(){
        Game game =new Game();
        Position firstMove = new Position(5, 6);
        game.playMove(firstMove);
        assertThat(game.getCurrentTurn()).isEqualTo(PlayerColor.WHITE);

        game.applyPieRule();
        assertThat(game.getBoard().getStone(firstMove)).isEqualTo(PlayerColor.WHITE);
        assertThat(game.getCurrentTurn()).isEqualTo(PlayerColor.BLACK);
    }
}
