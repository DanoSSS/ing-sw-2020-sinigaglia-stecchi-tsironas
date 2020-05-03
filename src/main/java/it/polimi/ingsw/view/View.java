package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ObservableModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public abstract class View extends Observable implements Observer<String> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    public void handleGods(String god1,String god2){
        notify(new GodChoice(player,god1,god2,this));
    }
}

