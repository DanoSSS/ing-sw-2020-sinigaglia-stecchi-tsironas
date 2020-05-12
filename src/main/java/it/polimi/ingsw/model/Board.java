package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ReturnMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Board{
    private int NumberOfPlayers;
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private Cell [][] board;
    private Worker[] workers = new Worker[6];
    //private Worker worker1,worker2,worker3,worker4,worker5,worker6;
    private int nround=0;
    private ObservableModel observableModel;

    public Board(Worker worker1, Worker worker2, Worker worker3, Worker worker4, int NPlayer) {

        board = new Cell[HEIGHT][WIDTH];                        //i==row && j==col
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = new Cell(i,j);
            }
        }
        this.workers[0] = worker1;
        this.workers[1] = worker2;
        this.workers[2] = worker3;
        this.workers[3] = worker4;

        this.NumberOfPlayers = NPlayer;

    }

    public void setObservableModel(Board board){
        this.observableModel= new ObservableModel(board);
    }

    public Board(Worker worker1, Worker worker2, Worker worker3, Worker worker4, Worker worker5, Worker worker6, int NPlayer) {

        board = new Cell[HEIGHT][WIDTH];                        //i==row && j==col
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = new Cell(i,j);
            }
        }

        this.workers[0] = worker1;
        this.workers[1] = worker2;
        this.workers[2] = worker3;
        this.workers[3] = worker4;
        this.workers[4] = worker5;
        this.workers[5] = worker6;

        this.NumberOfPlayers = NPlayer;

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
   //     observableModel.Notify();   commento da togliere messo solo per i test
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
        // observableModel.Notify();        commento da togliere messo solo per i test
    }

    //method to know which worker is in the cell x,y
    public Worker getWorker(Coordinates coordinates){
        return board[coordinates.getX()][coordinates.getY()].getWorker();
    }

    public Worker getWorkerById(int id){
        return this.workers[id];
    }

    //method to remove a worker from a cell when it's moved
    public void freeCellFromWorker(Coordinates coordinates){
        board[coordinates.getX()][coordinates.getY()].setWorker(null);

        board[coordinates.getX()][coordinates.getY()].setOccupied(false);
       // observableModel.Notify();    commento da togliere fatto solo per i test
    }

    public void setWorker(Coordinates coordinates,int id){
        workers[id].setCell(coordinates.getX(),coordinates.getY());
        board[coordinates.getX()][coordinates.getY()].setWorker(workers[id]);
        board[coordinates.getX()][coordinates.getY()].setOccupied(true);
    }

    public void initRound(int i){
        observableModel.notify(new ReturnMessage(2,i));
    }

    public ObservableModel getObservableModel(){
        return observableModel;
    }
}
