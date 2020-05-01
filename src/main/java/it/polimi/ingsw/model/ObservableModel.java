package it.polimi.ingsw.model;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ObservableModel implements Observable {
    private Board board;
    private List<Observer> Observers = new ArrayList<>();
    public ObservableModel(Board board ){
        this.board=board;
    }
    @Override
    public void AddObserver(Observer observer) {
        Observers.add(observer);
    }

    @Override
    public void RemoveObserver(Observer observer) {
        Observers.remove(observer);
    }

    @Override
    public void Notify() {

    }
    public Board getBoard() {
        return board;
    }
}
