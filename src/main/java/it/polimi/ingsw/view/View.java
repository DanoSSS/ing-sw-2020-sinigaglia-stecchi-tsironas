package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;

public abstract class View extends Observable<Object> implements Observer<Object> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }


    protected void handleMessage(Object m,int idPlayer){  //getplayerbyid
                ((Message) m).setPlayerValue(idPlayer);
                notify(m);
    }
}

