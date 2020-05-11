package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.utils.Color;

public class Player {
    private String nickname;
    private String color;
    private int location;
    private boolean IsChallenger;
    private Worker worker1;
    private Worker worker2;
    private God god;
    private Worker activeworker;

    public Player(String name, String color, int id1, int id2, God god){
        this.nickname=name;
        this.color=color;
        this.worker1 = new Worker(this,color,id1);
        this.worker2 = new Worker(this,color,id2);
        if(this.worker1.getIdWorker()==1){
            this.IsChallenger = true;
        }
        this.god = god;
    }


    public Worker getWorker2() {
        return worker2;
    }

    public void setWorker(Worker worker2) {
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

    public String getNickname(){return nickname;}

    public God getGod() {
        return god;
    }

    public boolean isChallenger() {
        return IsChallenger;
    }
}
