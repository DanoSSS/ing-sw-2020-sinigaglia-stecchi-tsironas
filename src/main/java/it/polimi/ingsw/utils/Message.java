package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class Message {

    private Action action;
    private ArrayList<Coordinates> coordinatesInitWorkers;
    private Player player;


    public Message(int nAction, ArrayList<Coordinates> coordinates){
        this.action = Action.values()[nAction];
        this.coordinatesInitWorkers = coordinates;
    }

    public Message(int nAction,Player currentPlayer){
        this.action = Action.values()[nAction];
        this.player = currentPlayer;
    }

    public Action getAction(){return action;}
    public ArrayList<Coordinates> getInitWorkerList(){
     return coordinatesInitWorkers;
    }
    public Player getPlayer(){return player;}

}
