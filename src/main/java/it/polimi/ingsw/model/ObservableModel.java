package it.polimi.ingsw.model;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ObservableModel extends Observable<Object> {

    private Board board;

    public ObservableModel(Board board ){
        this.board=board;
    }

}
