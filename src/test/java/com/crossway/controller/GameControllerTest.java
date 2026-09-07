package com.crossway.controller;

import com.crossway.model.Game;
import com.crossway.model.PlayerColor;
import com.crossway.model.Position;
import com.crossway.view.GameView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameView viewMock;

    private Game gameSpy;
    private GameController controller;

    @BeforeEach
    void setUp() {
        gameSpy = spy(new Game());
        controller = new GameController(gameSpy, viewMock);
    }

    @Test
    void testStartGameDisplaysRulesAndBoard() {
        doReturn(Optional.empty())
                .doReturn(Optional.of(PlayerColor.BLACK))
                .when(gameSpy).getWinner();

        when(viewMock.askPlayAgain(PlayerColor.BLACK)).thenReturn(false);

        controller.start();

        verify(viewMock, atLeastOnce()).printRules();
        verify(viewMock, atLeastOnce()).printBoard(any());
    }

    @Test
    void testValidMoveProcessing() {
        Position movePos = new Position(0, 0);

        when(viewMock.askForMove(PlayerColor.BLACK)).thenReturn(movePos);
        when(viewMock.isRestartRequested()).thenReturn(false);

        doReturn(Optional.empty())
                .doReturn(Optional.of(PlayerColor.BLACK))
                .when(gameSpy).getWinner();

        when(viewMock.askPlayAgain(PlayerColor.BLACK)).thenReturn(false);

        controller.start();

        verify(viewMock).askForMove(PlayerColor.BLACK);
    }

    @Test
    void testRestartRequestedWorkflow() {
        when(viewMock.askForMove(any())).thenReturn(null);
        when(viewMock.isRestartRequested()).thenReturn(true, false);
        when(viewMock.askPlayAgain(PlayerColor.WHITE)).thenReturn(false);

        doReturn(Optional.empty())
                .doReturn(Optional.of(PlayerColor.WHITE))
                .when(gameSpy).getWinner();

        controller.start();

        verify(gameSpy, times(1)).reset();
        verify(viewMock, times(1)).resetView();
        verify(viewMock, atLeast(2)).printRules();
    }

    @Test
    void testInvalidMoveHandling() {
        Position occupiedPos = new Position(1, 1);

        when(viewMock.askForMove(PlayerColor.BLACK)).thenReturn(occupiedPos);
        when(viewMock.askPieRule()).thenReturn(false);
        when(viewMock.askForMove(PlayerColor.WHITE)).thenReturn(occupiedPos);
        when(viewMock.isRestartRequested()).thenReturn(false);

        doReturn(Optional.empty())
                .doReturn(Optional.empty())
                .doReturn(Optional.empty())
                .doReturn(Optional.of(PlayerColor.BLACK))
                .when(gameSpy).getWinner();

        when(viewMock.askPlayAgain(any())).thenReturn(false);
        controller.start();

        verify(viewMock, atLeastOnce()).printError(any());
    }

    @Test
    void testPieRuledApplied(){
        gameSpy.playMove(new Position(0, 0));

        when(viewMock.askPieRule()).thenReturn(true);

        doReturn(Optional.empty())
                .doReturn(Optional.of(PlayerColor.BLACK))
                .when(gameSpy).getWinner();

        when(viewMock.askPlayAgain(any())).thenReturn(false);

        controller.start();

        verify(gameSpy, times(1)).applyPieRule();
        verify(viewMock, times(1)).printMessage(contains("Pie Rule applied!"));
    }

    @Test
    void testRestartRequestedAfterWin() {
        when(viewMock.askPlayAgain(PlayerColor.WHITE)).thenReturn(true,false);

        doReturn(Optional.of(PlayerColor.WHITE))
                .doReturn(Optional.of(PlayerColor.WHITE))
                .when(gameSpy).getWinner();

        controller.start();

        verify(gameSpy, times(1)).reset();
        verify(viewMock, times(1)).resetView();
        verify(viewMock, atLeast(2)).printRules();
    }
}
