package it.polimi.ingsw.model;

import it.polimi.ingsw.view.ObservableModel;

public class Cell {
    private int x;
    private int y;
    private int level;
    private boolean IsOccupied;
    private boolean IsDome;
    private Worker worker;
    private Board board;


    public boolean isOccupied() { return IsOccupied;
    }

    public void setOccupied(boolean occupied) {
        IsOccupied = occupied;
    }

    public boolean isDome() {
        return IsDome;
    }

    public void setDome(boolean dome) { IsDome = dome; }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
