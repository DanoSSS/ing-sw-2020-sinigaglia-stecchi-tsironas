package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Coordinates;

public interface Observable {
    public void AddObserver(Observer observer);
    public void RemoveObserver(Observer observer);
    public void Notify();
}
