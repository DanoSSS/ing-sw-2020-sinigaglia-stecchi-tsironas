package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Color;

import java.io.Serializable;

public class Worker implements Serializable {
    private int IdWorker;
    private String color;
    private Coordinates coordinates= new Coordinates(0,0);
    private Player player;

    public Worker(Player player,String color,int id){
        this.player=player;
        this.color=color;
        this.IdWorker=id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    //get the worker's coordinates  MODIFICATO DA CONTROLLARE
    public Coordinates getCoordinates() { return coordinates; }


    //change the worker's coordinates  MODIFICATO DA CONTROLLARE
    public void setCell(int x, int y) { // uguale alla locateworker
        coordinates.setX(x);
        coordinates.setY(y);
    }

    public int getIdWorker() {
        return IdWorker;
    }
}
