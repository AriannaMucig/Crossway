package com.crossway.model;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
class GameTest {
    @Test
    void testFirstTurnIsBlack(){
        Game game = new Game();
        assertThat(game.currentTurn()).isEqualTo(PlayerColor.BLACK);
    }
}
