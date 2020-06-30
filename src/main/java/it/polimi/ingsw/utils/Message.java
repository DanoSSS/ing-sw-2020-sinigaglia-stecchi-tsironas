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
    private int idWorker;
    private int playerValue;
    private Coordinates coordinates;

    /**
     * constructor message to send array of coordinate to set worker in their initial cell
     * @param nAction
     * @param coordinates
     */
    public Message(int nAction, ArrayList<Coordinates> coordinates){
        this.action = Action.values()[nAction];
        this.coordinatesInitWorkers = coordinates;
    }

    /**
     * constructor message to send string
     * @param nAction
     * @param sentence
     */
    public Message(int nAction,String sentence){
        this.action = Action.values()[nAction];
        this.sentence = sentence;
    }

    /**
     * constructor message to send idworker
     * @param nAction
     * @param idWorker
     */
    public Message (int nAction, int idWorker){
        this.action = Action.values()[nAction];
        this.idWorker = idWorker;
    }

    /**
     * constructor message to send coordinate
     * @param nAction
     * @param coordinates
     */
    public Message (int nAction, Coordinates coordinates){
        this.action = Action.values()[nAction];
        this.coordinates = coordinates;
    }



    public void setPlayerValue(int playerValue) {
        this.playerValue = playerValue;
    }
    public Action getAction(){return action;}
    public ArrayList<Coordinates> getInitWorkerList(){
     return coordinatesInitWorkers;
    }
    public Player getPlayer(){return player;}
    public String getSentence(){return sentence;}
    public int getIdWorker() {
        return idWorker;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
}
