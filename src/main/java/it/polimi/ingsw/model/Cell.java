package it.polimi.ingsw.model;



public class Cell {
    private Coordinates coordinates;
    private int level;
    private boolean IsOccupied;
    private boolean IsDome;
    private Worker worker;

    public Cell(int x,int y){    //MODIFICATO DA CONTROLLARE ANCHE COSTRUTTORE BOARD DELLA MATRICE
        coordinates.setX(x);
        coordinates.setY(y);
    }

    public boolean isOccupied() { return IsOccupied; }

    public void setOccupied(boolean occupied) {
        IsOccupied = occupied;
    }

    public boolean isDome() { return IsDome; }

    public void setDome() { IsDome = true; }

    public int getLevel() {
        return level;
    }

    public void setLevel() { this.level ++; }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
