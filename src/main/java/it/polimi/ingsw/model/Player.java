package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.utils.Color;

import java.io.Serializable;

public class Player implements Serializable {
    private String nickname;
    private String color;
    private int idPlayer;
    private Worker worker1;
    private Worker worker2;
    private God god;

    public Player(String name, String color, int id1, int id2, God god,int id){
        this.nickname=name;
        this.color=color;
        this.idPlayer=id;
        this.worker1 = new Worker(this,color,id1);
        this.worker2 = new Worker(this,color,id2);
        this.god = god;
    }

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

    public String getNickname(){return nickname;}

    public God getGod() {
        return god;
    }

    public int getIdPlayer() {
        return idPlayer;
    }


}
