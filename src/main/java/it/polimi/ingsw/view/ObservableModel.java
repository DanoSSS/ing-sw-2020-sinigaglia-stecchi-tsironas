package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;

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
