package com.crossway.model;

public class Game {
    private PlayerColor currentTurn;
    private Board board;

    public Game() {
        this.board = new Board();
        this.currentTurn = PlayerColor.BLACK;
    }

    public PlayerColor getCurrentTurn() {
        return currentTurn;
    }

    public void playMove(Position pos){
        PlayerColor color = currentTurn;
        board.placeStone(pos,color);
        currentTurn = (color == PlayerColor.BLACK) ? PlayerColor.WHITE : PlayerColor.BLACK;
    }


}
