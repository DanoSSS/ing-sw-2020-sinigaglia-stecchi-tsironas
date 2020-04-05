package it.polimi.ingsw.model;



public class Cell {
    private int x;
    private int y;
    private int level;
    private boolean IsOccupied;
    private boolean IsDome;
    private Worker worker;

    public Cell(int x,int y){
        this.x=x;
        this.y=y;
    }

    public boolean isOccupied() { return IsOccupied;
    }

    public void setOccupied(boolean occupied) {
        IsOccupied = occupied;
    }

    public boolean isDome() { return IsDome; }

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
