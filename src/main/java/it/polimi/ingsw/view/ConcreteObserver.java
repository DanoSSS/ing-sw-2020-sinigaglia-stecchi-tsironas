package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;

public class ConcreteObserver implements Observer {
    public ObservableModel subject;
    public Board board;
    public ConcreteObserver(ObservableModel subject){
        this.subject=subject;
   }
    @Override
    public void update() {
         //subject.getLevel(coordinates);
        /*devo aggiornare la board della view attraverso il metodo getBoard di ObservableModel*/
    }

}
