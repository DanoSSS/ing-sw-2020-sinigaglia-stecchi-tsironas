package it.polimi.ingsw.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.ReturnMessage;

import java.util.ArrayList;

public class Board extends Observable<Object> {
    private int NumberOfPlayers;
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private Cell [][] board;
    private Worker[] workers = new Worker[6];
    private Player[] players;
    private int nround=0;
    private Worker currentActiveWorker;
    private ArrayList<Coordinates> currentPossibleMoves;
    private ArrayList<Coordinates> currentPossibleBuilds;
    private int currentRound=2;
    private int loseRound;
    private boolean flag=false;
    private int chronusPlayer=-1;
    private int heraPlayer=-1;
    private int numberOfDome=0;


    /**
     * constructor 2 players
     * @param players
     * @param worker1
     * @param worker2
     * @param worker3
     * @param worker4
     * @param NPlayer
     */
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

    /**
     * constructor 3 players
     * @param players
     * @param worker1
     * @param worker2
     * @param worker3
     * @param worker4
     * @param worker5
     * @param worker6
     * @param NPlayer
     */
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

    /**
     *
     * @param currentRound
     */
    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    /**
     *
     * @return currentPossibleMoves
     */
    public ArrayList<Coordinates> getCurrentPossibleMoves() {
        return currentPossibleMoves;
    }

    /**
     *
     * @return NumberOfPlayers
     */
    public int getNumberOfPlayers() {
        return NumberOfPlayers;
    }

    /**
     *
     * @param numberOfPlayers
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        NumberOfPlayers = numberOfPlayers;
    }

    /**
     *
     * @return loseRound
     */
    public int getLoseRound() {
        return loseRound;
    }

    /**
     *
     * @param loseRound
     */
    public void setLoseRound(int loseRound) {
        this.loseRound = loseRound;
    }

    /**
     *
     * @return nround
     */
    public int getNround() {
        return nround;
    }

    /**
     *
     * @param nround
     */
    public void setNround(int nround) {
        this.nround = nround;
    }

    /**
     *
     * @return currentPossibleBuilds
     */
    public ArrayList<Coordinates> getCurrentPossibleBuilds() {
        return currentPossibleBuilds;
    }

    /**
     * method to build a dome in the cell x,y
     * @param coordinates
     */
    public void setDome(Coordinates coordinates){
        board[coordinates.getX()][coordinates.getY()].setDome();
    }

    /**
     * method to know if there is a dome in the cell x,y
     * @param coordinates
     * @return
     */
    public boolean isDome(Coordinates coordinates){
        return board[coordinates.getX()][coordinates.getY()].isDome();
    }

    /**
     * method to know if the cell x,y is occupied by a worker
     * @param coordinates
     * @return
     */
    public boolean isOccupied(Coordinates coordinates){
        return board[coordinates.getX()][coordinates.getY()].isOccupied();
    }

    /**
     * method to know the level of the cell x,y
     * @param coordinates
     * @return
     */
    public int getLevel(Coordinates coordinates){
        return board[coordinates.getX()][coordinates.getY()].getLevel();
    }

    /**
     *
     * @param coordinates
     */
    public void reduceLevel(Coordinates coordinates){
        board[coordinates.getX()][coordinates.getY()].reduceLevel();
    }

    /**
     * method to build in the cell x,y
     * @param coordinates
     */
    public void setLevel(Coordinates coordinates) {
        board[coordinates.getX()][coordinates.getY()].setLevel();
    }

    /**
     * method to move the worker
     * @param coordinates
     * @param worker
     */
    public void moveWorker(Coordinates coordinates,Worker worker){
        worker.setCell(coordinates.getX(),coordinates.getY());
        board[coordinates.getX()][coordinates.getY()].setWorker(worker);
        board[coordinates.getX()][coordinates.getY()].setOccupied(true);
    }

    /**
     *
     * @param coordinates
     * @return
     */
    public Worker getWorker(Coordinates coordinates){
        return board[coordinates.getX()][coordinates.getY()].getWorker();
    }

    /**
     *
     * @param id
     * @return
     */
    public Worker getWorkerById(int id){
        return this.workers[id];
    }

    /**
     * method to remove a worker from a cell when it's moved
     * @param coordinates
     */
    public void freeCellFromWorker(Coordinates coordinates){
        board[coordinates.getX()][coordinates.getY()].setWorker(null);

        board[coordinates.getX()][coordinates.getY()].setOccupied(false);
    }

    /**
     *
     * @param coordinates
     * @param id
     */
    public void setWorker(Coordinates coordinates,int id){
        workers[id].setCell(coordinates.getX(),coordinates.getY());
        board[coordinates.getX()][coordinates.getY()].setWorker(workers[id]);
        board[coordinates.getX()][coordinates.getY()].setOccupied(true);
    }

    /**
     * metthod that return array with players' nicknames
     * @return players
     */
    public String[] getPlayerNicknames() {
        String players[] = NumberOfPlayers==2 ? new String[2] : new String[3];
        for (int i=0;i<this.players.length;i++){
            players[i]=this.players[i].getNickname();
        }
        return players;
    }

    /**
     * metthod that return array with players' id
     * @return idPlayers
     */
    public Integer[] getIdPlayers(){
        Integer idPlayers[] = NumberOfPlayers==2 ? new Integer[2] : new Integer[3];
        for (int i=0;i<this.players.length;i++){
            idPlayers[i]=this.players[i].getIdPlayer();
        }
        return idPlayers;
    }

    /**
     *
     * @return numberOfDome
     */
    public int getNumberOfDome() {
        return numberOfDome;
    }

    /**
     *
     * @param numberOfDome
     */
    public void setNumberOfDome(int numberOfDome) {
        this.numberOfDome = numberOfDome;
    }

    /**
     *
     * @return chronusPlayer
     */
    public int getChronusPlayer() {
        return chronusPlayer;
    }

    /**
     *
     * @param chronusPlayer
     */
    public void setChronusPlayer(int chronusPlayer) {
        this.chronusPlayer = chronusPlayer;
    }

    /**
     *
     * @return heraPlayer
     */
    public int getHeraPlayer() {
        return heraPlayer;
    }

    /**
     *
     * @param heraPlayer
     */
    public void setHeraPlayer(int heraPlayer) {
        this.heraPlayer = heraPlayer;
    }

    /**
     * method that every end turn calculate new current round
     * @return currentRound
     */
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

    /**
     *
     * @return currentActiveWorker
     */
    public Worker getCurrentActiveWorker() {
        return currentActiveWorker;
    }

    /**
     * method called in demeter and hestia round that notifies remoteview of a build
     * @param c
     * @param currentPossibleBuilds
     */
    public void firstBuildDemeterAndHestia(Coordinates c,ArrayList<Coordinates> currentPossibleBuilds){
        this.currentPossibleBuilds=currentPossibleBuilds;
        int level = getLevel(c);
        boolean dome = isDome(c);
        notify(new ReturnMessage(14,currentActiveWorker.getIdWorker(),c,level,dome,currentPossibleBuilds));
    }

    /**
     *  method called in Prometheus round that notifies remoteview with current active worker and possibleBuild and possibleMove
     * @param activeWorker
     * @param currentPossibleMoves
     * @param currentPossibleBuilds
     */
    public void setCurrentActiveWorkerAndChoosePrometheus(Worker activeWorker,ArrayList<Coordinates> currentPossibleMoves,ArrayList<Coordinates> currentPossibleBuilds){
        this.currentActiveWorker=activeWorker;
        this.currentPossibleMoves=currentPossibleMoves;
        this.currentPossibleBuilds=currentPossibleBuilds;
        notify(new ReturnMessage(15,currentActiveWorker.getIdWorker(),currentPossibleMoves,currentPossibleBuilds,currentRound));
    }

    /**
     * method called in Prometheus round that notifies remoteview of a build before move
     * @param coordinates
     * @param possibleMoves
     */
    public void buildBeforePrometheus(Coordinates coordinates,ArrayList<Coordinates> possibleMoves){
        int level = getLevel(coordinates);
        boolean dome = isDome(coordinates);
        this.currentPossibleMoves = possibleMoves;
        notify(new ReturnMessage(16,currentActiveWorker.getIdWorker(),coordinates,level,dome,possibleMoves));
    }

    /**
     *
     * @return currentRound
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * method that notifies remoteview with active worker's id and possiblemove
     * @param currentActiveWorker
     * @param currentPossibleMoves
     */
    public void setCurrentActiveWorkerAndPossibleMoves(Worker currentActiveWorker,ArrayList<Coordinates> currentPossibleMoves){
        this.currentActiveWorker=currentActiveWorker;
        this.currentPossibleMoves=currentPossibleMoves;
        notify(new ReturnMessage(6,currentActiveWorker.getIdWorker(),currentPossibleMoves,currentRound));
    }

    /**
     * method that notifies remoteview with move coordinate and possibleBuild
     * @param oldC
     * @param newC
     * @param currentPossibleBuilds
     * @param oppWorker
     */
    public void moveWorkerAndPossibleBuilds(Coordinates oldC,Coordinates newC,ArrayList<Coordinates> currentPossibleBuilds,Worker oppWorker){
        this.currentPossibleBuilds=currentPossibleBuilds;
        if(players[currentRound-1].getGod()==God.ATLAS) {
            notify(new ReturnMessage(11, currentActiveWorker.getIdWorker(), oldC, newC, currentPossibleBuilds, currentRound, oppWorker));
        }else if (players[currentRound-1].getGod()==God.EPHAESTUS){
            notify(new ReturnMessage(12, currentActiveWorker.getIdWorker(), oldC, newC, currentPossibleBuilds, currentRound, oppWorker));
        }else notify(new ReturnMessage(7,currentActiveWorker.getIdWorker(),oldC,newC,currentPossibleBuilds,currentRound,oppWorker));
    }

    /**
     * method that notifies remoteview of a build and that round it's over
     * @param c
     */
    public void buildEndTurn(Coordinates c) {
        int oldRound = currentRound;
        int newRound = UpdateRound();
        if (c != null) {
            int level = getLevel(c);
            boolean dome = isDome(c);
            notify(new ReturnMessage(8, currentActiveWorker.getIdWorker(), c, level, oldRound, newRound, dome));
        } else {
            notify(new ReturnMessage(13, currentActiveWorker.getIdWorker(), oldRound, newRound));
        }
    }

    /**
     * method called in ares round that notifies remteview that ares round it's over
     * @param c
     */
    public void aresEndTurn(Coordinates c){
        int oldRound = currentRound;
        int newRound = UpdateRound();
        if (c != null) {
            int level = getLevel(c);
            boolean dome = isDome(c);
            notify(new ReturnMessage(31, currentActiveWorker.getIdWorker(), c, level, oldRound, newRound, dome));
        } else {
            notify(new ReturnMessage(13, currentActiveWorker.getIdWorker(), oldRound, newRound));
        }
    }

    /**
     * method called in ares roun that notifies remoteview of a build
     * @param c
     */
    public void buildAres(Coordinates c){
        Worker otherWorker;
        ArrayList<Coordinates> possiblesAresPowerCoordinates = new ArrayList<>();
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
        notify(new ReturnMessage(30,currentActiveWorker.getIdWorker(), c, level, dome, possiblesAresPowerCoordinates));
    }

    /**
     * method that notifies remoteview that one player lose and if there are 3 player in game remove losing player's workers
     */
    public void loseGame(){
        if(getNumberOfPlayers()==3 &&!flag){
            setLoseRound(currentRound);
            flag=true;
            int newRound=UpdateRound();
            freeCellFromWorker(players[loseRound-1].getWorker1().getCoordinates());
            freeCellFromWorker(players[loseRound-1].getWorker2().getCoordinates());
            notify(new ReturnMessage(17,loseRound,newRound,players[loseRound-1].getWorker1(),players[loseRound-1].getWorker2()));
        }else if(getNumberOfPlayers()==3 && flag){
            notify(new ReturnMessage(5,currentRound));
        }else{
            notify(new ReturnMessage(5,currentRound));
        }
    }

    /**
     * method that notifies win
     */
    public void winGame(){
        notify(new ReturnMessage(18,currentRound));
    }

    /**
     *
     * @param currentActiveWorker
     */
    public void setCurrentActiveWorker(Worker currentActiveWorker) {
        this.currentActiveWorker = currentActiveWorker;
    }
}

