package it.polimi.ingsw.controller;

import it.polimi.ingsw.observer.Observer;

public abstract class Round implements Observer<Object>{

    public abstract boolean ExecuteRound(boolean Gameover);

    @Override
    public abstract void update(Object message);
}
