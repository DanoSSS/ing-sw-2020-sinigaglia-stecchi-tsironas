package it.polimi.ingsw.model;



public class Cell {
    private Coordinates coordinates;
    private int level;
    private boolean IsOccupied;
    private boolean IsDome;
    private Worker worker;

    public Cell(int x,int y){    //MODIFICATO DA CONTROLLARE ANCHE COSTRUTTORE BOARD DELLA MATRICE
        coordinates= new Coordinates(x,y);
    }

    /**
     *
     * @return IsOccupied
     */
    public boolean isOccupied() { return IsOccupied; }

    /**
     *
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        IsOccupied = occupied;
    }

    /**
     *
     * @return IsDome
     */
    public boolean isDome() { return IsDome; }

    public void setDome() { IsDome = true; }

    /**
     *
     * @return level
     */
    public int getLevel() {
        return level;
    }

    public void setLevel() { this.level ++; }

    public void reduceLevel() {
        this.level--;
    }

    /**
     *
     * @return worker
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     *
     * @param worker
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
