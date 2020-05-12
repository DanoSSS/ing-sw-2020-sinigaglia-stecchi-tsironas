package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
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
        Map<Player,Coordinates> m;

        switch (a) {
            case CURRENTPLAYERNUMBER:
                int currentplayer = ((ReturnMessage)message).getnCurrentPlayer();
                if(this.numberRW == currentplayer){
                    clientConnection.asyncSend("It's your turn!");
                    handleMessage(new Message(4,this.getPlayer()));
                }else{
                    clientConnection.asyncSend("Wait your turn!");
                }
            case WORKERSET:
                m=((ReturnMessage)message).getWorkerPosition();
                List<Player> players = new ArrayList<>(m.keySet());
                for(int i=0;i<players.size()*2;i=i+2) {
                    clientConnection.asyncSend(players.get(i/2).getNickname() + " put his workers in: " + m.get(players.get(i)) + " and " + m.get(players.get(i + 1)));
                }
        }
    }
}
