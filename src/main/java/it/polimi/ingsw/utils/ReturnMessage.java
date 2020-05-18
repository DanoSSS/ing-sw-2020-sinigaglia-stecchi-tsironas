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



    public ReturnMessage(int nAction,String sentence){
        this.action = Action.values()[nAction];
        this.sentence = sentence;
        this.clientController=null;
    }

    public ReturnMessage(int nAction,int currentActiveWorker,ArrayList<Coordinates> currentPossibleMoves,int nCurrentPlayer){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.currentPossibleMoves = currentPossibleMoves;
        this.nCurrentPlayer = nCurrentPlayer;
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

    public ReturnMessage(int nAction, Map<Worker, Coordinates> workerPosition, String[] nicknames, Integer[] idPlayers, int NPlayer,int firstToStartID) {
        this.action = Action.values()[nAction];
        this.workerPosition = workerPosition;
        this.nicknames=nicknames.clone();
        this.idPlayers=idPlayers.clone();
        this.nCurrentPlayer=firstToStartID;
        this.NPlayer=NPlayer;
        this.clientController = null;
    }

    public ReturnMessage(int nAction,int currentActiveWorker,Coordinates oldC,Coordinates newC,ArrayList<Coordinates> currentPossibleBuilds,int nCurrentPlayer){
        this.action = Action.values()[nAction];
        this.currentActiveWorker = currentActiveWorker;
        this.coordinateOld = oldC;
        this.coordinate = newC;
        this.currentPossibleMoves = currentPossibleBuilds;
        this.nCurrentPlayer = nCurrentPlayer;
        this.clientController = null;
    }


    public ReturnMessage (int nAction, String[] workersSettingInBoard, ClientController clientController){
        this.clientController=clientController;
        this.action=Action.values()[nAction];
        this.nicknames = workersSettingInBoard.clone();
    }

    public ReturnMessage(int nAction){
        this.action = Action.values()[nAction];
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


}
