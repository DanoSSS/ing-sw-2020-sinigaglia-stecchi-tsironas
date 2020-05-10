package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.ObservableModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT=12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private Map<String, God> playerGodAssociation = new HashMap<>();
    private enum color {BLUE,GREEN,RED};
    int nPlayers=0;
    private int nPlayersConnected = 0;
    private ArrayList<God> gods = new ArrayList<God>();
    boolean godscreated = false, godselectedByP2 = false;

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


    public synchronized void lobby(ClientConnection c, String name, God god) {
        int idWorker[]= {1,2,3,4,5,6};
        waitingConnection.put(name, c);
        playerGodAssociation.put(name, god);
        if (waitingConnection.size() == 2 && nPlayers == 2) {
            System.out.println("entrato in lobby nel ramo nplayer == 2");
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            God god1 = playerGodAssociation.get(keys.get(0));
            God god2 = playerGodAssociation.get(keys.get(1));
            Player player1 = new Player(keys.get(0), "BLUE", idWorker[0], idWorker[1], god1);
            Player player2 = new Player(keys.get(1), "RED", idWorker[2], idWorker[3], god2);
            View player1View = new RemoteView(player1, c1);
            View player2View = new RemoteView(player2, c2);
            Board board = new Board(player1.getWorker1(), player1.getWorker2(), player2.getWorker1(), player2.getWorker2(), nPlayers);
            board.setObservableModel(board);
            Game game = new Game(board);
            game.RoundCreationP1(player1);
            game.RoundCreationP2(player2);
            board.getObservableModel().addObserver(player1View);
            board.getObservableModel().addObserver(player2View);
            game.addObserver(player1View);
            game.addObserver(player2View);
            player1View.addObserver(game);
            player2View.addObserver(game);
            player1View.addObserver(game.getRoundP1());
            player2View.addObserver(game.getRoundP2());
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
        }
        if (waitingConnection.size() == 3 && nPlayers == 3) {
            System.out.println("entrato in lobby nel ramo nplayer == 3");
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            ClientConnection c3 = waitingConnection.get(keys.get(2));
            God god1 = playerGodAssociation.get(keys.get(0));
            God god2 = playerGodAssociation.get(keys.get(1));
            God god3 = playerGodAssociation.get(keys.get(2));
            Player player1 = new Player(keys.get(0), "BLUE", idWorker[0], idWorker[1], god1);
            Player player2 = new Player(keys.get(1), "RED", idWorker[2], idWorker[3], god2);
            Player player3 = new Player(keys.get(2), "GREEN", idWorker[4], idWorker[5], god3);
            View player1View = new RemoteView(player1, c1);
            View player2View = new RemoteView(player2, c2);
            View player3View = new RemoteView(player3, c3);
            Board board = new Board(player1.getWorker1(), player1.getWorker2(), player2.getWorker1(), player2.getWorker2(), player3.getWorker1(), player3.getWorker2(), nPlayers);
            board.setObservableModel(board);
            Game game = new Game(board);
            game.RoundCreationP1(player1);
            game.RoundCreationP2(player2);
            game.RoundCreationP3(player3);
            board.getObservableModel().addObserver(player1View);
            board.getObservableModel().addObserver(player2View);
            board.getObservableModel().addObserver(player3View);
            player1View.addObserver(game);
            player2View.addObserver(game);
            player3View.addObserver(game);
            game.addObserver(player1View);
            game.addObserver(player2View);
            game.addObserver(player3View);
            player1View.addObserver(game.getRoundP1());
            player2View.addObserver(game.getRoundP2());
            player3View.addObserver(game.getRoundP3());
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            playingConnection.put(c3, c1);
            playingConnection.put(c1, c3);
            playingConnection.put(c3, c2);
            playingConnection.put(c2, c3);
            System.out.println("fine if 3");
        }
        System.out.println("\nMatch created!");
    }



    public synchronized void putInWaitP2() throws InterruptedException {
        while(!godscreated){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        godscreated = false;
    }

    public synchronized void putInWaitP3() throws InterruptedException {
        while(!godselectedByP2){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        godselectedByP2 = false;
    }

    public synchronized void putInWaitChallenger() throws InterruptedException {
        while(gods.size()!=1){
            try{
                wait();
            }catch (InterruptedException e){}
        }
    }

    public synchronized void removeFromWaitP2 (){
        godscreated = true;
        notifyAll();
    }

    public synchronized void removeFromWaitP3 (){
        godselectedByP2 = true;
        notifyAll();
    }

    public synchronized void removeFromWait (){
        notifyAll();
    }


    public void setGods(String god){
        gods.add(God.valueOf(god));
    }

    public String getGods(int i){
        return gods.get(i).toString();
    }

    public void removeGods(String god){
        gods.remove(God.valueOf(god));
    }

    public void setnPlayers(int i){
        nPlayers = i;
    }

    public int getnPlayers(){
        return nPlayers;
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                nPlayersConnected++;
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this, nPlayersConnected);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }


}

