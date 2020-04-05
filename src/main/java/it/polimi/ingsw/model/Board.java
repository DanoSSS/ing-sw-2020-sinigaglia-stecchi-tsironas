package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Game;

public class Board {
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private Cell [][] board;
    private Game game;
    private Player player1,player2,player3;
    private Worker worker1,worker2,worker3,worker4,worker5,worker6;

    public Board(Worker worker /*vanno passati tutti gli worker(forse anche i player)*/) {
        this.worker1 = worker;

        board = new Cell[HEIGHT][WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[j][i] = null;
            }
        }

    }

   /* public boolean CheckIfMoveIsPossible(Cell cell){

    }

    public boolean CheckIfBuildIsPossible(Cell cell){

    } */
}
