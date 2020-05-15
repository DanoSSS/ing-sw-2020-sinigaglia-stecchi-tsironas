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
        Message request = (Message)m;
        Action a =((Message) m).getAction();
        int actionValue;
        switch (a){
            case CURRENTPLAYERNUMBER:
                actionValue = a.getValue();
                notify(new Message(actionValue,idPlayer));
/*creare id dei player nella classe player e un getter,
* inserirlo nel notify sopra e inizializzare lo stato della board in modo corretto*/
        }

    }
}

