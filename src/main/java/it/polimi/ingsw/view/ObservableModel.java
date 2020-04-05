package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;

import java.util.ArrayList;
import java.util.List;

public class ObservableModel implements Observable {
    private Board board;
    private List<Observer> Observers = new ArrayList<>();
    public ObservableModel(Board board1 ){
        this.board=board1;
    }
    @Override
    public void AddObserver(Observer observer) {
        Observers.add(observer);
    }

    @Override
    public void RemoveObserver(Observer observer) {
        Observers.remove(observer);
    }

    public Board getBoard() {
        return board;
    }

    public static void Notify(){

    }



}
