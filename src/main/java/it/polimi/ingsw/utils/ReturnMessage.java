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
    private Coordinates coordinate;
    private Coordinates coordinateOld;
    private boolean dome;

    public int getLevel() {
        return level;
    }

    private int level;

    public int getNextNPlayer() {
        return nextNPlayer;
    }

    private int nextNPlayer;


    //case STRING
    public ReturnMessage(int nAction,String sentence){
        this.action = Action.values()[nAction];
        this.sentence = sentence;
        this.clientController=null;
    }

    //case SELECTCOORDINATEMOVE
    public ReturnMessage(int nAction,int currentActiveWorker,ArrayList<Coordinates> currentPossibleMoves,int nCurrentPlayer){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.currentPossibleMoves = currentPossibleMoves;
        this.nCurrentPlayer = nCurrentPlayer;
        this.clientController=null;
    }

    //case BUILDENDTURN
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

    //case MOVEANDCOORDINATEBUILD
    public ReturnMessage(int nAction,int currentActiveWorker,Coordinates oldC,Coordinates newC,ArrayList<Coordinates> currentPossibleBuilds,int nCurrentPlayer){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.coordinateOld = oldC;
        this.coordinate = newC;
        this.currentPossibleMoves = currentPossibleBuilds;
        this.nCurrentPlayer = nCurrentPlayer;
        this.clientController = null;
    }

    //case ACTIVEWORKER
    public ReturnMessage (int nAction, String[] workersSettingInBoard, ClientController clientController){
        this.clientController=clientController;
        this.action=Action.values()[nAction];
        this.nicknames = workersSettingInBoard.clone();
    }

    //case WORKERSET
    public ReturnMessage(int nAction, Map<Worker, Coordinates> workerPosition, String[] nicknames, Integer[] idPlayers, int NPlayer,int firstToStartID) {
        this.action = Action.values()[nAction];
        this.workerPosition = workerPosition;
        this.nicknames=nicknames.clone();
        this.idPlayers=idPlayers.clone();
        this.nCurrentPlayer=firstToStartID;
        this.NPlayer=NPlayer;
        this.clientController = null;
    }

    //case ARTEMIS
    public ReturnMessage(int nAction,Coordinates coordinate,int currentActiveWorker,ArrayList<Coordinates> currentPossibleMoves) {
        this.action = Action.values()[nAction];
        this.coordinate = coordinate;
        this.currentActiveWorker = currentActiveWorker;
        this.currentPossibleMoves = currentPossibleMoves;
        this.clientController = null;
    }

    public ReturnMessage(int nAction){
        this.action = Action.values()[nAction];
        this.clientController=null;
    }

    public ReturnMessage(int nAction, int nCurrentPlayer) {
        this.action = Action.values()[nAction];
        this.nCurrentPlayer = nCurrentPlayer;
        this.clientController = null;
    }

    public ReturnMessage(int nAction, int whoToSent[], String sentence) {
        this.action = Action.values()[nAction];
        this.whoToSend = whoToSent;
        this.sentence = sentence;
        this.clientController = null;
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
    public String getDome(){
        if(dome){
            return "yes";
        }
        else return "no";
    }


}
