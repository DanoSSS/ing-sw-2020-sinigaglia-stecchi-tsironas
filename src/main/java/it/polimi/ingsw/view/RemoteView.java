package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.Message;

public class RemoteView extends View{
    private ClientConnection clientConnection;

    private class MessageReceiver implements Observer<Object> {
        private Player player;

        private MessageReceiver(Player player) {
            this.player=player;
        }

        @Override
        public void update(Object m) {
            System.out.println("Received");
            handleMessage(m);
        }
    }



    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        clientConnection.addObserver(new MessageReceiver(player));
    }

    @Override
    public void update(Object message) {

    }
}
