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
    private int loseRound;
    private boolean flag=false;
    private int chronusPlayer=-1;
    private int heraPlayer=-1;
    private int numberOfDome=0;



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

    public int getLoseRound() {
        return loseRound;
    }

    public void setLoseRound(int loseRound) {
        this.loseRound = loseRound;
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

    public void reduceLevel(Coordinates coordinates){
        board[coordinates.getX()][coordinates.getY()].reduceLevel();
    }

    //method to build in the cell x,y
    public void setLevel(Coordinates coordinates) {
        board[coordinates.getX()][coordinates.getY()].setLevel();
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


    public int getNumberOfDome() {
        return numberOfDome;
    }

    public void setNumberOfDome(int numberOfDome) {
        this.numberOfDome = numberOfDome;
    }

    public int getChronusPlayer() {
        return chronusPlayer;
    }

    public void setChronusPlayer(int chronusPlayer) {
        this.chronusPlayer = chronusPlayer;
    }

    public int getHeraPlayer() {
        return heraPlayer;
    }

    public void setHeraPlayer(int heraPlayer) {
        this.heraPlayer = heraPlayer;
    }

    public int UpdateRound(){
        currentRound++;
        if(currentRound==loseRound){
            currentRound++;
        }
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

    public void firstBuildDemeterAndHestia(Coordinates c,ArrayList<Coordinates> currentPossibleBuilds){
        this.currentPossibleBuilds=currentPossibleBuilds;
        int level = getLevel(c);
        boolean dome = isDome(c);
        observableModel.notify(new ReturnMessage(14,currentActiveWorker.getIdWorker(),c,level,dome,currentPossibleBuilds));
    }

    public void setCurrentActiveWorkerAndChoosePrometheus(Worker activeWorker,ArrayList<Coordinates> currentPossibleMoves,ArrayList<Coordinates> currentPossibleBuilds){
        this.currentActiveWorker=activeWorker;
        this.currentPossibleMoves=currentPossibleMoves;
        this.currentPossibleBuilds=currentPossibleBuilds;
        observableModel.notify(new ReturnMessage(15,currentActiveWorker.getIdWorker(),currentPossibleMoves,currentPossibleBuilds,currentRound));
    }

    public void buildBeforePrometheus(Coordinates coordinates,ArrayList<Coordinates> possibleMoves){
        int level = getLevel(coordinates);
        boolean dome = isDome(coordinates);
        this.currentPossibleMoves = possibleMoves;
        observableModel.notify(new ReturnMessage(16,currentActiveWorker.getIdWorker(),coordinates,level,dome,possibleMoves));
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

    public void aresEndTurn(Coordinates c){
        int oldRound = currentRound;
        int newRound = UpdateRound();
        if (c != null) {
            int level = getLevel(c);
            boolean dome = isDome(c);
            observableModel.notify(new ReturnMessage(31, currentActiveWorker.getIdWorker(), c, level, oldRound, newRound, dome));
        } else {
            observableModel.notify(new ReturnMessage(13, currentActiveWorker.getIdWorker(), oldRound, newRound));
        }
    }

    public void buildAres(Coordinates c){
        Worker otherWorker;
        ArrayList<Coordinates> possiblesAresPowerCoordinates = new ArrayList<Coordinates>();
        if(currentActiveWorker.getPlayer().getWorker1().getIdWorker()==currentActiveWorker.getIdWorker()){
            otherWorker = currentActiveWorker.getPlayer().getWorker2();
        }else {
            otherWorker=currentActiveWorker.getPlayer().getWorker1();
        }
        for (int i = otherWorker.getCoordinates().getX() - 1; i <= otherWorker.getCoordinates().getX() + 1; i++) {
            for (int j = otherWorker.getCoordinates().getY() - 1; j <= otherWorker.getCoordinates().getY() + 1; j++) {
                if (i >= 0 && i <= 4 && j >= 0 && j <= 4) {
                    Coordinates newCoordinates = new Coordinates(i, j);
                    if (!isOccupied(newCoordinates) && !isDome(newCoordinates) && getLevel(newCoordinates) > 0) {
                        possiblesAresPowerCoordinates.add(newCoordinates);
                    }
                }
            }
        }
        int level= getLevel(c);
        boolean dome= isDome(c);
        observableModel.notify(new ReturnMessage(30,currentActiveWorker.getIdWorker(), c, level, dome, possiblesAresPowerCoordinates));
    }

    public void loseGame(){
        if(getNumberOfPlayers()==3 &&!flag){
            setLoseRound(currentRound);
            flag=true;
            int newRound=UpdateRound();
            freeCellFromWorker(players[loseRound-1].getWorker1().getCoordinates());
            freeCellFromWorker(players[loseRound-1].getWorker2().getCoordinates());
            observableModel.notify(new ReturnMessage(17,loseRound,newRound,players[loseRound-1].getWorker1(),players[loseRound-1].getWorker2()));
        }else if(getNumberOfPlayers()==3 && flag){
            observableModel.notify(new ReturnMessage(5,currentRound));
        }else{
            observableModel.notify(new ReturnMessage(5,currentRound));
        }
    }

    public void winGame(){
        observableModel.notify(new ReturnMessage(18,currentRound));
    }

    public void setCurrentActiveWorker(Worker currentActiveWorker) {
        this.currentActiveWorker = currentActiveWorker;
    }
}

