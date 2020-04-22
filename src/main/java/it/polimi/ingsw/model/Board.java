package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.view.ObservableModel;

public class Board{
    private int NumberOfPlayers;
    private static final int HEIGHT = 4;
    private static final int WIDTH = 4;
    private Cell [][] board;
    private Game game;
    private Player player1,player2,player3;     //non dovrebbero servire
    private Worker worker1,worker2,worker3,worker4,worker5,worker6;
    private int nround=0;
    private ObservableModel observableModel;

    public Board(/*Worker worker /*vanno passati tutti gli worker(forse anche i player) e l observableModel*/) {
       // this.worker1 = worker;

        board = new Cell[HEIGHT][WIDTH];                        //i==row && j==col
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = new Cell(i,j);
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
    public void setDome(Coordinates coordinates){
        board[coordinates.getX()][coordinates.getY()].setDome();
        observableModel.Notify();
    }

    //method to know if there is a dome in the cell x,y
    public boolean isDome(Coordinates coordinates){
        return board[coordinates.getX()][coordinates.getY()].isDome();
    }

    //method to know if the cell x,y is occupied by a worker
    public boolean isOccupied(Coordinates coordinates){
        return board[coordinates.getX()][coordinates.getY()].isOccupied();
    }

    //method to know the level of the cell x,y
    public int getLevel(Coordinates coordinates){
        return board[coordinates.getX()][coordinates.getY()].getLevel();
    }

    //method to build in the cell x,y
    public void setLevel(Coordinates coordinates) {
        board[coordinates.getX()][coordinates.getY()].setLevel();
        //observableModel.Notify();          commento da togliere messo solo per i test
    }

    //method to move the worker
    public void moveWorker(Coordinates coordinates,Worker worker){
        worker.setCell(coordinates.getX(),coordinates.getY());
        board[coordinates.getX()][coordinates.getY()].setWorker(worker);
        board[coordinates.getX()][coordinates.getY()].setOccupied(true);
        observableModel.Notify();
    }

    //method to know which worker is in the cell x,y
    public Worker getWorker(Coordinates coordinates){
        return board[coordinates.getY()][coordinates.getY()].getWorker();
    }

    //method to remove a worker from a cell when it's moved
    public void freeCellFromWorker(Coordinates coordinates){
        board[coordinates.getX()][coordinates.getY()].setWorker(null);
        board[coordinates.getX()][coordinates.getY()].setOccupied(false);
        observableModel.Notify();
    }

   /* public boolean CheckIfMoveIsPossible(Cell cell){

    }

    public boolean CheckIfBuildIsPossible(Cell cell){

    } */
}
