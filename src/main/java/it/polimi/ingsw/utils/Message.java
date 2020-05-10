package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Coordinates;

import java.util.ArrayList;

public class Message {


    private enum actions {
        INITWORKERS(0),
        SELECTACTIVEWORKER(1);

        private int nAction;
        actions(int i) {
            this.nAction = i;
        }
    }

    private actions action;
    private ArrayList<Coordinates> coordinatesInitWorkers;


    public Message(int nAction, ArrayList<Coordinates> coordinates){

    }

}
