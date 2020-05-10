package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Coordinates;

import java.util.ArrayList;

public class Message {

    private Action action;
    private ArrayList<Coordinates> coordinatesInitWorkers;


    public Message(int nAction, ArrayList<Coordinates> coordinates){
        this.action = Action.values()[nAction];
        this.coordinatesInitWorkers = coordinates;
    }

    public Action getAction(){return action;}
    public ArrayList<Coordinates> getInitWorkerList(){
     return coordinatesInitWorkers;
    }

}
