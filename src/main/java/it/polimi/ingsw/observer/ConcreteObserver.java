package it.polimi.ingsw.observer;

public class ConcreteObserver implements Observer {
    public ObservableModel subject;
    public ConcreteObserver(ObservableModel subject){
        this.subject=subject;
   }

    @Override
    public void update(Object message) {
        //subject.getLevel(coordinates);
        /*devo aggiornare la board della view attraverso il metodo getBoard di ObservableModel*/
    }
}
