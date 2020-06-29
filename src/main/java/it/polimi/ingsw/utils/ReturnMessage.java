package it.polimi.ingsw.utils;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReturnMessage implements Serializable {

    private Action action;
    private int nCurrentPlayer;
    private Map<Worker, Coordinates> workerPosition = new HashMap<>();
    private String sentence;
    private int whoToSend[] = new int[2];
    private String nicknames[];
    private Integer idPlayers[];
    private int NPlayer;
    private final ClientController clientController;
    private int currentActiveWorker;
    private ArrayList<Coordinates> currentPossibleMoves;
    private ArrayList<Coordinates> currentPossibleC2;
    private Coordinates coordinate;
    private Coordinates coordinateOld;
    private boolean dome;
    private int level;
    private int nextNPlayer;
    private Worker oppWorker,otherWorker;
    private String god1,god2,god3;


    /**
     * constructor returnMessage for string
     * @param nAction
     * @param sentence
     */
    public ReturnMessage(int nAction,String sentence){
        this.action = Action.values()[nAction];
        this.sentence = sentence;
        this.clientController=null;
    }

    /**
     * constructor returnMessage to set worker in initial position
     * @param nAction
     * @param sentence
     * @param workerPositions
     * @param n
     */
    public ReturnMessage(int nAction,String sentence,ArrayList<Coordinates> workerPositions, int n){
        this.action = Action.values()[nAction];
        this.sentence = sentence;
        this.clientController=null;
        this.currentPossibleMoves=workerPositions;
        this.level=n;
    }


    /**
     * constructor returnMessage for current active worker and possible move cells
     * @param nAction
     * @param currentActiveWorker
     * @param currentPossibleMoves
     * @param nCurrentPlayer
     */
    public ReturnMessage(int nAction,int currentActiveWorker,ArrayList<Coordinates> currentPossibleMoves,int nCurrentPlayer){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.currentPossibleMoves = currentPossibleMoves;
        this.nCurrentPlayer = nCurrentPlayer;
        this.clientController=null;
    }

    /**
     * constructor returnMessage to build and end turn
     * @param nAction
     * @param currentActiveWorker
     * @param coordinate
     * @param level
     * @param nCurrentPlayer
     * @param nextNPlayer
     * @param dome
     */
    public ReturnMessage(int nAction,int currentActiveWorker,Coordinates coordinate,int level,int nCurrentPlayer,int nextNPlayer,boolean dome){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.coordinate = coordinate;
        this.level = level;
        this.nCurrentPlayer = nCurrentPlayer;
        this.nextNPlayer = nextNPlayer;
        this.dome = dome;
        this.clientController = null;
    }

    /**
     * constructor returnMessage to move and send possible build cells
     * @param nAction
     * @param currentActiveWorker
     * @param oldC
     * @param newC
     * @param currentPossibleBuilds
     * @param nCurrentPlayer
     * @param oppWorker
     */
    public ReturnMessage(int nAction,int currentActiveWorker,Coordinates oldC,Coordinates newC,ArrayList<Coordinates> currentPossibleBuilds,int nCurrentPlayer,Worker oppWorker){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.coordinateOld = oldC;
        this.coordinate = newC;
        this.currentPossibleMoves = currentPossibleBuilds;
        this.nCurrentPlayer = nCurrentPlayer;
        this.oppWorker = oppWorker;
        this.clientController = null;
    }


    /**
     * WORKERSET
     * @param nAction
     * @param workersSettingInBoard
     * @param clientController
     * @param workerPosition
     */
    public ReturnMessage (int nAction, String[] workersSettingInBoard, ClientController clientController,Map<Worker, Coordinates> workerPosition){
        this.clientController=clientController;
        this.action=Action.values()[nAction];
        this.nicknames = workersSettingInBoard.clone();
        this.workerPosition = workerPosition;
    }

    /**
     * WORKERSET
     * @param nAction
     * @param workerPosition
     * @param nicknames
     * @param idPlayers
     * @param NPlayer
     * @param firstToStartID
     */
    public ReturnMessage(int nAction, Map<Worker, Coordinates> workerPosition, String[] nicknames, Integer[] idPlayers, int NPlayer,int firstToStartID) {
        this.action = Action.values()[nAction];
        this.workerPosition = workerPosition;
        this.nicknames=nicknames.clone();
        this.idPlayers=idPlayers.clone();
        this.nCurrentPlayer=firstToStartID;
        this.NPlayer=NPlayer;
        this.clientController = null;
    }

    /**
     * constructor ReturnMessage used in firstmoveArtemis
     * @param nAction
     * @param coordinateOld
     * @param coordinate
     * @param currentActiveWorker
     * @param currentPossibleMoves
     */
    public ReturnMessage(int nAction,Coordinates coordinateOld,Coordinates coordinate,int currentActiveWorker,ArrayList<Coordinates> currentPossibleMoves) {
        this.action = Action.values()[nAction];
        this.coordinate = coordinate;
        this.currentActiveWorker = currentActiveWorker;
        this.currentPossibleMoves = currentPossibleMoves;
        this.coordinateOld=coordinateOld;
        this.clientController = null;
    }

    /**
     * constructor returnMessage to end turn
     * @param nAction
     * @param currentActiveWorker
     * @param nCurrentPlayer
     * @param nextNPlayer
     */
    public ReturnMessage(int nAction,int currentActiveWorker,int nCurrentPlayer,int nextNPlayer){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.nCurrentPlayer = nCurrentPlayer;
        this.nextNPlayer = nextNPlayer;
        this.clientController = null;
    }

    /**
     * constructor returnMessage used in firstbuilddemeterandHestia, buildbeforePrometheus and build ares
     * @param nAction
     * @param currentActiveWorker
     * @param coordinates
     * @param level
     * @param dome
     * @param currentPossibleBuilds
     */
    public ReturnMessage(int nAction,int currentActiveWorker,Coordinates coordinates,int level,boolean dome,ArrayList<Coordinates> currentPossibleBuilds){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.coordinate = coordinates;
        this.level = level;
        this.dome = dome;
        this.currentPossibleMoves = currentPossibleBuilds;
        this.clientController = null;
    }

    public ReturnMessage(int nAction){
        this.action = Action.values()[nAction];
        this.clientController=null;
    }

    /**
     * constructor returnMessage used to send currentRound or number of player
     * @param nAction
     * @param nCurrentPlayer
     */
    public ReturnMessage(int nAction, int nCurrentPlayer) {
        this.action = Action.values()[nAction];
        this.nCurrentPlayer = nCurrentPlayer;
        this.clientController = null;
    }

    /**
     * constructor returnMessage used for loseGame
     * @param nAction
     * @param nCurrentPlayer
     * @param nextNPlayer
     * @param wk1
     * @param wk2
     */
    public ReturnMessage(int nAction,int nCurrentPlayer,int nextNPlayer,Worker wk1, Worker wk2){
        this.action = Action.values()[nAction];
        this.nCurrentPlayer = nCurrentPlayer;
        this.nextNPlayer = nextNPlayer;
        this.oppWorker = wk1;
        this.otherWorker = wk2;
        this.clientController = null;
    }

    public ReturnMessage(int nAction, int whoToSent[], String sentence) {
        this.action = Action.values()[nAction];
        this.whoToSend = whoToSent;
        this.sentence = sentence;
        this.clientController = null;
    }

    /**
     * constructor returnMessage used in choosePrometheus
     * @param nAction
     * @param idWorker
     * @param currentPossibleMoves
     * @param currentPossibleBuilds
     * @param currentRound
     */
    public ReturnMessage(int nAction, int idWorker, ArrayList<Coordinates> currentPossibleMoves, ArrayList<Coordinates> currentPossibleBuilds, int currentRound) {
        this.action = Action.values()[nAction];
        this.currentActiveWorker = idWorker;
        this.currentPossibleMoves = currentPossibleMoves;
        this.currentPossibleC2 = currentPossibleBuilds;
        this.nCurrentPlayer = currentRound;
        this.clientController = null;
    }

    /**
     * constructor returnMessage used to set gods
     * @param nAction
     * @param NPlayer
     * @param god1
     * @param god2
     * @param god3
     */
    public ReturnMessage(int nAction,int NPlayer,String god1,String god2,String god3){
        this.action = Action.values()[nAction];
        this.NPlayer = NPlayer;
        this.god1 = god1;
        this.god2 = god2;
        this.god3 = god3;
        this.clientController = null;
    }

    /**
     * constructor returnMessage used for win
     * @param nAction
     * @param winner
     * @param nCurrentPlayer
     */
    public ReturnMessage(int nAction, int winner, int nCurrentPlayer) {
        this.action = Action.values()[nAction];
        this.level=winner;
        this.nCurrentPlayer=nCurrentPlayer;
        this.clientController=null;
    }


    public Map<Worker, Coordinates> getWorkerPosition() {
        return workerPosition;
    }
    public int getnCurrentPlayer() {
        return nCurrentPlayer;
    }
    public Action getAction() {
        return action;
    }
    public String getSentence() {
        return sentence;
    }
    public int[] getWhoToSend() {
        return whoToSend;
    }
    public String[] getNicknames() {
        return nicknames;
    }
    public Integer[] getIdPlayers() {
        return idPlayers;
    }
    public int getNPlayer() {
        return NPlayer;
    }
    public ClientController getClientController() {
        return clientController;
    }
    public ArrayList<Coordinates> getCurrentPossibleMoves() {
        return currentPossibleMoves;
    }
    public int getCurrentActiveWorker() {
        return currentActiveWorker;
    }
    public Coordinates getCoordinateOld() {
        return coordinateOld;
    }
    public Coordinates getCoordinate() {
        return coordinate;
    }
    public boolean getDome(){
        return dome;
    }
    public int getNextNPlayer() {
        return nextNPlayer;
    }
    public int getLevel() {
        return level;
    }
    public Worker getOppWorker(){
        return oppWorker;
    }
    public ArrayList<Coordinates> getCurrentPossibleC2(){
        return currentPossibleC2;
    }
    public Worker getOtherWorker(){
        return otherWorker;
    }
    public String getGod1() {
        return god1;
    }
    public String getGod2() {
        return god2;
    }
    public String getGod3() {
        return god3;
    }

}
