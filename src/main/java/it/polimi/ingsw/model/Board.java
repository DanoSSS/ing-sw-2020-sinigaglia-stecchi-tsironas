package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.view.ObservableModel;

public class Board{
    private int NumberOfPlayers;
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private Cell [][] board;
    private Game game;
    private Player player1,player2,player3;     //non dovrebbero servire
    private Worker worker1,worker2,worker3,worker4,worker5,worker6;
    private int nround=0;
    private ObservableModel observableModel;

    public Board(Worker worker /*vanno passati tutti gli worker(forse anche i player) e l observableModel*/) {
        this.worker1 = worker;

        board = new Cell[HEIGHT][WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[j][i] = new Cell(j,i);
            }

        }

    }

    public int getNumberOfPlayers() {
        return NumberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        NumberOfPlayers = numberOfPlayers;
    }

    public int getNround() {
        return nround;
    }

    public void setNround(int nround) {
        this.nround = nround;
    }

    //method to build a dome in the cell x,y
    public void setDome(int x,int y){
        board[x][y].setDome();
        observableModel.Notify();
    }

    //method to know if there is a dome in the cell x,y
    public boolean isDome(int x,int y){
        return board[x][y].isDome();
    }

    //method to know if the cell x,y is occupied by a worker
    public boolean isOccupied(int x,int y){
        return board[x][y].isOccupied();
    }

    //method to know the level of the cell x,y
    public int getLevel(int x, int y){
        return board[x][y].getLevel();
    }

    //method to build in the cell x,y
    public void setLevel(int x, int y) {
        board[x][y].setLevel();
        observableModel.Notify();
    }

    //method to move the worker
    public void moveWorker(int x,int y,Worker worker){
        worker.setCell(x,y);
        board[x][y].setWorker(worker);
        observableModel.Notify();
    }

    //method to know which worker is in the cell x,y
    public Worker getWorker(int x,int y){
        return board[x][y].getWorker();
    }

    //method to remove a worker from a cell when it's moved
    public void freeCellFromWorker(int x,int y){
        board[x][y].setWorker(null);
        observableModel.Notify();
    }

   /* public boolean CheckIfMoveIsPossible(Cell cell){

    }

    public boolean CheckIfBuildIsPossible(Cell cell){

    } */
}
