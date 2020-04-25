package it.polimi.ingsw.observer;

public interface Observable {
    public void AddObserver(Observer observer);
    public void RemoveObserver(Observer observer);
    public void Notify();
}
