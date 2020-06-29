package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable <T>{
    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * method to add observer
     * @param observer
     */
    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * method to remove observer
     * @param observer
     */
    public void removeObserver(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * method to notify all observer
     * @param message
     */
    public void notify(T message){
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.update(message);
            }
        }
    }

}
