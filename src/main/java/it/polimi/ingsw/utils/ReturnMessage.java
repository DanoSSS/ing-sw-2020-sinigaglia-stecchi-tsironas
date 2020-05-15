package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReturnMessage implements Serializable {

    private Action action;
    private int nCurrentPlayer;
    private Map<Worker, Coordinates> workerPosition = new HashMap<>();
    private String sentence;

    public ReturnMessage(int nAction,int nCurrentPlayer){
        this.action = Action.values()[nAction];
        this.nCurrentPlayer = nCurrentPlayer;
    }

    public ReturnMessage(int nAction,Map workerPosition){
        this.action = Action.values()[nAction];
        this.workerPosition = workerPosition;
    }

    public ReturnMessage(int nAction){
        this.action = Action.values()[nAction];
    }

    public ReturnMessage(int nAction,String sentence){
        this.action = Action.values()[nAction];
        this.sentence = sentence;
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
}
