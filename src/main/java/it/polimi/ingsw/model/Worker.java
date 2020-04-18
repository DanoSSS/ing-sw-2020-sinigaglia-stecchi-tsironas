package it.polimi.ingsw.model;

public class Worker {
    private int IdWorker;
    private boolean ActiveWorker=false;
    private String Color;
    private int x;
    private int y;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public boolean isActiveWorker() {
        return ActiveWorker;
    }

    public void setActiveWorker(boolean activeWorker) {
        ActiveWorker = activeWorker;
    }

    //get the worker's coordinates
    public int getXcoordinate() { return x; }
    public int getYcoordinate() { return y; }

    //change the worker's coordinates
    public void setCell(int x, int y) { // uguale alla locateworker
        this.x = x;
        this.y = y;
    }

}
