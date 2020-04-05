package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;

public class ConcreteObserver implements Observer {
    public ObservableModel subject;
    public Board board;
    public ConcreteObserver(ObservableModel subject){
        this.subject=subject;
   }
    @Override
    public void update() {
        //board = subject.getBoard();
        /*devo aggiornare la board della view attraverso il metodo getBoard di ObservableModel*/
    }
}
