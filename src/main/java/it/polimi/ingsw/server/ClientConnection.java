package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;

public interface ClientConnection {

    void addObserver(Observer<String> observer);

    void closeConnection();

}
