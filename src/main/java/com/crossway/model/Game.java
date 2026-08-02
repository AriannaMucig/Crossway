package com.crossway.model;

public class Game {
    private PlayerColor currentTurn;
    private Board board;
    private int turnsCount;
    private Position firstMovePosition;
    private PlayerColor winner;

    public Game() {
        this.board = new Board();
        this.currentTurn = PlayerColor.BLACK;
        this.turnsCount = 1;
    }

    public PlayerColor getCurrentTurn() {
        return currentTurn;
    }

    public void playMove(Position pos) {
        PlayerColor color = currentTurn;
        if (turnsCount==1){
            firstMovePosition= pos;
        }
        board.placeStone(pos, color);
        currentTurn = (color == PlayerColor.BLACK) ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    public void applyPieRule(){
        if (turnsCount != 1 && currentTurn==PlayerColor.WHITE) {
            throw new IllegalStateException("Pie rule can only be applied on the second turn!");
        }

        board.changeStone(firstMovePosition, PlayerColor.WHITE);

        currentTurn=PlayerColor.BLACK;
        turnsCount++;
    }

    public Board getBoard() {
        return board;
    }

    public PlayerColor getWinner() {
        return winner;
    }
}
