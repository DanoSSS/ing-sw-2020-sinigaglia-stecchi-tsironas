package it.polimi.ingsw.model;

public class Worker {
    private int IdWorker;
    private boolean ActiveWorker=false;
    private String Color;
    private Cell cell;
    private Player player;

    public boolean isActiveWorker() {
        return ActiveWorker;
    }

    public void setActiveWorker(boolean activeWorker) {
        ActiveWorker = activeWorker;
    }


    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) { // uguale alla locateworker
        this.cell = cell;
    }

}
