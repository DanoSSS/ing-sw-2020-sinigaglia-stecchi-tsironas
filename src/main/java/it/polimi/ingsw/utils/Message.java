package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

    private Action action;
    private ArrayList<Coordinates> coordinatesInitWorkers;
    private Player player;
    private String sentence;
    private int playerValue;

    public Message(int nAction, ArrayList<Coordinates> coordinates){
        this.action = Action.values()[nAction];
        this.coordinatesInitWorkers = coordinates;
    }

    public Message(int nAction,String sentence){
        this.action = Action.values()[nAction];
        this.sentence = sentence;
    }
    public Message (int nAction, int playerNumber){
        this.action = Action.values()[nAction];
        this.playerValue = playerNumber;
    }


    public Action getAction(){return action;}
    public ArrayList<Coordinates> getInitWorkerList(){
     return coordinatesInitWorkers;
    }
    public Player getPlayer(){return player;}
    public String getSentence(){return sentence;}
    public int getPlayerValue() {
        return playerValue;
    }
    public void setPlayerValue(int idPlayer){
        this.playerValue=idPlayer;
    }
}
