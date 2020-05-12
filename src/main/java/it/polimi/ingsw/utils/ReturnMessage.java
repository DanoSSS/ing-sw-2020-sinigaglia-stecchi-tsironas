package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.HashMap;
import java.util.Map;

public class ReturnMessage {

    private Action action;
    private int nCurrentPlayer;
    private Map<Worker, Coordinates> workerPosition = new HashMap<>();

    public ReturnMessage(int nAction,int nCurrentPlayer){
        this.action = Action.values()[nAction];
        this.nCurrentPlayer = nCurrentPlayer;
    }

    public ReturnMessage(int nAction,Map workerPosition){
        this.action = Action.values()[nAction];
        this.workerPosition = workerPosition;
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
}
