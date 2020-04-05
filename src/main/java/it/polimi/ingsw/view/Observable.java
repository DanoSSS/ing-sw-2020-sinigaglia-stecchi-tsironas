package it.polimi.ingsw.view;

public interface Observable {
    public void AddObserver(Observer observer);
    public void RemoveObserver(Observer observer);
}
