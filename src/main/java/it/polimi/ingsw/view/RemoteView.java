package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoteView extends View{
    private ClientConnection clientConnection;
    private int numberRW;

    private class MessageReceiver implements Observer<Object> {
        private Player player;

        private MessageReceiver(Player player) {
            this.player=player;
        }

        @Override
        public void update(Object m) {
            System.out.println("Received");      //debug here!!!
            handleMessage(m);
        }
    }

    public RemoteView(Player player, ClientConnection c, int i) {
        super(player);
        this.clientConnection = c;
        this.numberRW = i;
        c.addObserver(new MessageReceiver(player));
    }

    @Override
    public void update(Object message) {
        Action a = ((ReturnMessage) message).getAction();
        Map<Worker,Coordinates> m;

        switch (a) {
            case CURRENTPLAYERNUMBER:
                int currentplayer = ((ReturnMessage)message).getnCurrentPlayer();
                if(this.numberRW == currentplayer){
                    clientConnection.asyncSend("It's your turn!");
                    handleMessage(new Message(3,this.getPlayer()));
                }else{
                    clientConnection.asyncSend("Wait your turn!");
                }
            case WORKERSET:
                m=((ReturnMessage)message).getWorkerPosition();
                List<Worker> workers = new ArrayList<>(m.keySet());
                for(int i=0;i<workers.size();i++) {
                    clientConnection.asyncSend(workers.get(i).getIdWorker() + " set in cell " + m.get(workers.get(i)).getX() + "," + m.get(workers.get(i)).getY() );
                }
        }
    }
}
