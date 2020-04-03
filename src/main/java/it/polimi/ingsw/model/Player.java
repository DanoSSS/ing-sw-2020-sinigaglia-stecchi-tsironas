package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Game;

public class Player {
    private String nickname;
    private String color;
    private int location;
    private boolean IsChallenger;
    private Worker worker1;
    private Worker worker2;
    private God god;
    private Game game;
    private Worker activeworker;


    public Worker getWorker2() {
        return worker2;
    }

    public void setWorker2(Worker worker2) {
        this.worker2 = worker2;
    }

    public Worker getWorker1() {
        return worker1;
    }

    public void setWorker1(Worker worker1) {
        this.worker1 = worker1;
    }

    public void SetWorkerInCell(Worker worker) {

    }

        /*public void SetActiveWorker(Worker worker) {
            worker.setActiveWorker(true);
        }*/

    public God getGod() {
        return god;
    }

    public void setGod(God god) {
        this.god = god;
    }
}
