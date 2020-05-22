package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ReturnMessage;

import java.util.ArrayList;

public class Board{
    private int NumberOfPlayers;
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private Cell [][] board;
    private Worker[] workers = new Worker[6];
    private Player[] players;
    private int nround=0;
    private ObservableModel observableModel;
    private Worker currentActiveWorker;
    private ArrayList<Coordinates> currentPossibleMoves, currentPossibleBuilds;
    private int currentRound=2;


    public Board(Player[] players, Worker worker1, Worker worker2, Worker worker3, Worker worker4, int NPlayer) {
        this.players =players;
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

    public Board(Player[] players , Worker worker1, Worker worker2, Worker worker3, Worker worker4, Worker worker5, Worker worker6, int NPlayer) {
        this.players =players;
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

    public ObservableModel getObservableModel(){
        return observableModel;
    }

    public String[] getPlayerNicknames() {
        String players[] = NumberOfPlayers==2 ? new String[2] : new String[3];
        for (int i=0;i<this.players.length;i++){
            players[i]=this.players[i].getNickname();
        }
        return players;
    }

    public Integer[] getIdPlayers(){
        Integer idPlayers[] = NumberOfPlayers==2 ? new Integer[2] : new Integer[3];
        for (int i=0;i<this.players.length;i++){
            idPlayers[i]=this.players[i].getIdPlayer();
        }
        return idPlayers;
    }

    public int UpdateRound(){
        currentRound++;
        if(NumberOfPlayers==2 && currentRound==3){
            currentRound=1;
        }
        else if(NumberOfPlayers==3 && currentRound==4){
            currentRound=1;
        }
        return currentRound;
    }

    public Worker getCurrentActiveWorker() {
        return currentActiveWorker;
    }

    public void firstBuildDemeter(Coordinates c,ArrayList<Coordinates> currentPossibleBuilds){
        this.currentPossibleBuilds=currentPossibleBuilds;
        int level = getLevel(c);
        boolean dome = isDome(c);
        observableModel.notify(new ReturnMessage(14,currentActiveWorker.getIdWorker(),c,level,dome,currentPossibleBuilds));
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentActiveWorkerAndPossibleMoves(Worker currentActiveWorker,ArrayList<Coordinates> currentPossibleMoves){
        this.currentActiveWorker=currentActiveWorker;
        this.currentPossibleMoves=currentPossibleMoves;
        observableModel.notify(new ReturnMessage(6,currentActiveWorker.getIdWorker(),currentPossibleMoves,currentRound));
    }

    public void moveWorkerAndPossibleBuilds(Coordinates oldC,Coordinates newC,ArrayList<Coordinates> currentPossibleBuilds,Worker oppWorker){
        this.currentPossibleBuilds=currentPossibleBuilds;
        if(players[currentRound-1].getGod()==God.ATLAS) {
            observableModel.notify(new ReturnMessage(11, currentActiveWorker.getIdWorker(), oldC, newC, currentPossibleBuilds, currentRound, oppWorker));
        }else if (players[currentRound-1].getGod()==God.EPHAESTUS){
            observableModel.notify(new ReturnMessage(12, currentActiveWorker.getIdWorker(), oldC, newC, currentPossibleBuilds, currentRound, oppWorker));
        }else observableModel.notify(new ReturnMessage(7,currentActiveWorker.getIdWorker(),oldC,newC,currentPossibleBuilds,currentRound,oppWorker));
    }

    public void buildEndTurn(Coordinates c) {
        int oldRound = currentRound;
        int newRound = UpdateRound();
        if (c != null) {
            int level = getLevel(c);
            boolean dome = isDome(c);
            observableModel.notify(new ReturnMessage(8, currentActiveWorker.getIdWorker(), c, level, oldRound, newRound, dome));
        } else {
            observableModel.notify(new ReturnMessage(13, currentActiveWorker.getIdWorker(), oldRound, newRound));
        }
    }

}

