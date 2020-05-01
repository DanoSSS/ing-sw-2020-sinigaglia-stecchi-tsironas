package it.polimi.ingsw.observer;

import it.polimi.ingsw.observer.Observer;

public interface Observable {
    public void AddObserver(Observer observer);
    public void RemoveObserver(Observer observer);
    public void Notify();
}
