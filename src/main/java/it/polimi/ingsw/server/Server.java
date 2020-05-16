package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.model.*;
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
    private static final int startPlayer = 2;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private Map<String, God> playerGodAssociation = new HashMap<>();
    private int idGenerate=0;
    int nPlayers=0;
    private int nPlayersConnected = 0;
    private ArrayList<String> nicknames = new ArrayList<String>();
    private ArrayList<God> gods = new ArrayList<God>();
    private ArrayList<Coordinates> workerPositions = new ArrayList<Coordinates>();
    boolean boolP1 = false, boolP2 = false, boolP3 = false, bool = false;



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
        waitingConnection.keySet().removeIf(s -> waitingConnection.get(s) == c);
    }

    public synchronized void lobby(ClientConnection c, String name, God god) {
        waitingConnection.put(name, c);
        playerGodAssociation.put(name, god);
        if (waitingConnection.size() == 2 && nPlayers == 2) {               // 2 players
            System.out.println("entrato in lobby nel ramo nplayer == 2");
            List<String> keys = new ArrayList<>(waitingConnection.keySet());

            startGameAndObservers2(keys);

            playingConnection.put(waitingConnection.get(keys.get(0)), waitingConnection.get(keys.get(1)));  //(c1,c2)
            playingConnection.put(waitingConnection.get(keys.get(1)), waitingConnection.get(keys.get(0)));  //(c2,c1)
            System.out.println("fine if 2");
        }


        else if (waitingConnection.size() == 3 && nPlayers == 3) {               //3 players
            System.out.println("entrato in lobby nel ramo nplayer == 3");
            List<String> keys = new ArrayList<>(waitingConnection.keySet());

            startGameAndObservers3(keys);

            playingConnection.put(waitingConnection.get(keys.get(0)), waitingConnection.get(keys.get(1)));  //(c1,c2)
            playingConnection.put(waitingConnection.get(keys.get(1)), waitingConnection.get(keys.get(0)));  //(c2,c1)
            playingConnection.put(waitingConnection.get(keys.get(2)), waitingConnection.get(keys.get(0)));  //(c3,c1)
            playingConnection.put(waitingConnection.get(keys.get(0)), waitingConnection.get(keys.get(2)));  //(c1,c3)
            playingConnection.put(waitingConnection.get(keys.get(2)), waitingConnection.get(keys.get(1)));  //(c3,c2)
            playingConnection.put(waitingConnection.get(keys.get(1)), waitingConnection.get(keys.get(2)));  //(c2,c3)
            System.out.println("fine if 3");
        }

        System.out.println("\nMatch created!");
    }

    public void startGameAndObservers2(List<String> keys){
        int[] idWorker = {0,1,2,3};
        Player[] players = new Player[2];
        ClientConnection c1 = waitingConnection.get(keys.get(0));
        ClientConnection c2 = waitingConnection.get(keys.get(1));
        //God god1 = playerGodAssociation.get(keys.get(0));
        //God god2 = playerGodAssociation.get(keys.get(1));
        Player player1 = new Player(keys.get(0), "RED", idWorker[0], idWorker[1], playerGodAssociation.get(keys.get(0)),2);
        Player player2 = new Player(keys.get(1), "GREEN", idWorker[2], idWorker[3], playerGodAssociation.get(keys.get(1)),1);
        players[0] = player1;
        players[1] = player2;
        View player1View = new RemoteView(player1, c1, player1.getIdPlayer());
        View player2View = new RemoteView(player2, c2, player2.getIdPlayer());
        Board board = new Board(players , player1.getWorker1(), player1.getWorker2(), player2.getWorker1(), player2.getWorker2(), nPlayers);
        board.setObservableModel(board);
        Game game = new Game(board,2,player1,player2);
        game.RoundCreationP1(player1);
        game.RoundCreationP2(player2);
        board.getObservableModel().addObserver(player1View);
        board.getObservableModel().addObserver(player2View);
        game.addObserver(player1View);
        game.addObserver(player2View);
        player1View.addObserver(game);
        player2View.addObserver(game);
        player1View.addObserver(game.getRoundP1(player1));
        player2View.addObserver(game.getRoundP2(player2));
    }

    public void startGameAndObservers3(List<String> keys){
        int[] idWorker = {0,1,2,3,4,5};
        Player[] players = new Player[3];
        ClientConnection c1 = waitingConnection.get(keys.get(0));
        ClientConnection c2 = waitingConnection.get(keys.get(1));
        ClientConnection c3 = waitingConnection.get(keys.get(2));
        God god1 = playerGodAssociation.get(keys.get(0));
        God god2 = playerGodAssociation.get(keys.get(1));
        God god3 = playerGodAssociation.get(keys.get(2));
        Player player1 = new Player(keys.get(0), "RED",idWorker[0], idWorker[1], god1,2);
        Player player2 = new Player(keys.get(1), "GREEN", idWorker[2], idWorker[3], god2,3);
        Player player3 = new Player(keys.get(2), "BLUE", idWorker[4], idWorker[5], god3,1);
        players[0]= player1;
        players[1]= player2;
        players[2]= player3;
        View player1View = new RemoteView(player1, c1, player1.getIdPlayer());
        View player2View = new RemoteView(player2, c2, player2.getIdPlayer());
        View player3View = new RemoteView(player3, c3, player3.getIdPlayer());
        Board board = new Board(players,player1.getWorker1(), player1.getWorker2(), player2.getWorker1(), player2.getWorker2(), player3.getWorker1(), player3.getWorker2(), nPlayers);
        board.setObservableModel(board);
        Game game = new Game(board,3,player1,player2,player3);
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
        player1View.addObserver(game.getRoundP1(player1));
        player2View.addObserver(game.getRoundP2(player2));
        player3View.addObserver(game.getRoundP3(player3));
    }

    public synchronized void putInWaitP1() throws InterruptedException {
        while(!boolP1){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        boolP1 = false;
    }

    public synchronized void putInWaitStart() throws InterruptedException {
        while(!bool){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        bool = false;
    }

    public synchronized void putInWaitP2() throws InterruptedException {
        while(!boolP2){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        boolP2 = false;
    }

    public synchronized void putInWaitP3() throws InterruptedException {
        while(!boolP3 ){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        boolP3 = false;
    }

    public synchronized void putInWaitChallenger() throws InterruptedException {
        while(gods.size()!=1){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void removeFromWaitStart (){
            bool = true;
            notifyAll();
    }

    public synchronized void removeFromWaitP1 (){
        boolP1 = true;
        notifyAll();
    }

    public synchronized void removeFromWaitP2 (){
        boolP2 = true;
        notifyAll();
    }

    public synchronized void removeFromWaitP3 (){
        boolP3 = true;
        notifyAll();
    }

    public synchronized void removeFromWait (){
        notifyAll();
    }

    public synchronized boolean nicknameCheck(String name){
        Iterator<String> it = nicknames.iterator();
        String nickname;
        while(it.hasNext()){
            nickname = it.next();
            if(name.equals(nickname)){
                return false;
            }
        }
        nicknames.add(name);
        return true;
    }

    public synchronized boolean addWorkersPositions(int x, int y){
        Iterator<Coordinates> it = workerPositions.iterator();
        Coordinates newC, oldC;
        if(!( (x<5 && x>=0)&&(y<5 && y>=0) )){return false;}
        newC = new Coordinates(x,y);
        while(it.hasNext()){
            oldC = it.next();
            if (newC.equals(oldC)){
                return false;
            }
        }
        workerPositions.add(newC);
        return true;
    }

    public ArrayList<Coordinates> getWorkerPositions() {
        return workerPositions;
    }

    public boolean setGods1(String read) {      //try to create god card from input of P1
        God g;
        String[] inputGod = read.split(",");
        if(inputGod.length!=nPlayers){return false;} //if the input size is <> nPlayer
        for(int i=0; i<nPlayers; i++){
            try {
            g=God.valueOf(inputGod[i].toUpperCase()); //match God class with the input
            } catch (IllegalArgumentException e){
                System.out.println("one of the god card doesn't match correctly\n");
                return false;
            }
            if(!((inputGod[i].toUpperCase()).equals(g.toString()))){
                return false;
            }
            setGods(inputGod[i].toUpperCase());
        }
        return true;
    }

    public void clearGods(){
        gods.clear();
    }

    public void setGods(String god){        //put god card in list
        gods.add(God.valueOf(god));
    }

    public String getGods(int i){
        return gods.get(i).toString();
    }

    public boolean removeGods1(String read) {
        God g;
        try {
            g=God.valueOf(read.toUpperCase()); //match God class with the input
        } catch (IllegalArgumentException e){
            System.out.println("one of the god card doesn't match correctly\n");
            return true;
        }
        if(!read.equals(g.toString())){
            return true;
        }
        removeGods(read);
        return false;
    }

    public void removeGods(String god){
        gods.remove(God.valueOf(god));
    }

    public void setNPlayers(int i){
        nPlayers = i;
    }

    public int getNPlayers(){
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

    public String getNicknamesByNumberOfTurns(int playerNumber) {
        return nicknames.get(playerNumber-1);

    }
    public static int getStartPlayer() {
        return startPlayer;
    }

    public int generateIdPlayer(){
        return idGenerate++;
    }

}

