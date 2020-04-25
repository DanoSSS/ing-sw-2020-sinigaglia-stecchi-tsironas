package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT=12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }


    public synchronized void lobby(ClientConnection c, String name) {
        int nPlayers=2;
        waitingConnection.put(name, c);
        if (waitingConnection.size() == nPlayers /*2 or 3*/) {
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
      /*      Player player1 = new Player(keys.get(0));
            Player player2 = new Player(keys.get(0));
            View player1View = new RemoteView(player1, keys.get(1), c1);
            View player2View = new RemoteView(player2, keys.get(0), c2);
            Model model = new Model();
            Controller controller = new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);*/
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            if (nPlayers == 3) {
                ClientConnection c3 = waitingConnection.get(keys.get(2));
          /*      Player player3 = new Player(args for constructor of class Player);
                View player3View = new RemoteView(player2, player, c3);
                player3View.addObserver(controller);
                model.addObserver(player3View);     */
                playingConnection.put(c3, c1);
                playingConnection.put(c1, c3);
                playingConnection.put(c3, c2);
                playingConnection.put(c2, c3);
            }
            waitingConnection.clear();
        }
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}
