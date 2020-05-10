package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView extends View{
    private ClientConnection clientConnection;

    private class MessageReceiver implements Observer<String> {
        private Player player;

        private MessageReceiver(Player player) {
            this.player=player;
        }

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            try{
                String[] inputs = message.split(",");
            }catch(IllegalArgumentException e){
                clientConnection.asyncSend("Error!");
            }
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
