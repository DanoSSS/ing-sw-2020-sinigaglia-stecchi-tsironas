package it.polimi.ingsw.server;

import it.polimi.ingsw.model.God;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<Object> implements ClientConnection,Runnable {
    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private boolean active = true;
    private int playerNumber;

    public SocketClientConnection(Socket socket,Server server,int playerNumber){
        this.socket = socket;
        this.server = server;
        this.playerNumber = playerNumber;
    }

    private synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch( IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void addObserver(Observer<Object> observer) {

    }

    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    @Override
    public void asyncSend(Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void run() {
        Scanner in;
        //ObjectInputStream in;
        String name;
        String read2 = "";
        boolean flag1 = false, flag2 = false;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");                   //for all players
            String read = in.nextLine();
            name = read;
            if(playerNumber==1) {                                   //playerNOne

                send("how many players?\n2 or 3?");     //setting numberOfPlayers
                String read0 = in.nextLine();
                server.setNPlayers(Integer.parseInt(read0));

                send("select "+ server.getNPlayers() +" gods\n");   //Setting god cards
                String read1 = in.nextLine();
                while (!server.setGods1(read1)){
                    send("god Cards doesn't exist: select "+ server.getNPlayers() +" gods ( ATHENA,\t APOLLO,\tPAN,...)\n");
                    read1 = in.nextLine();
                }

                server.removeFromWaitP2();      //Remove P2 from wait
                server.putInWaitChallenger();  //Wait for P2,P3

                send("your god is: " + server.getGods(0));
                read2 = server.getGods(0);
                server.removeFromWaitStart(); //messo in server.lobby dentro if
            }
            
            
            else if(playerNumber==2) {                               //PlayerNTwo
                send("wait the P1... is taking the cards");
                server.putInWaitP2(); //Wait for the P1 to choose the cards
                if (server.getNPlayers() == 3) {       //3
                    send("select your god between: " + server.getGods(0) + "\t" + server.getGods(1) + "\t" + server.getGods(2));
                    read2 = in.nextLine();
                    while (server.removeGods1(read2)) {
                        send("you have entered a god card that doesn't exist, select your god between: " + server.getGods(0) + "\t" + server.getGods(1) + "\t" + server.getGods(2));
                        read2 = in.nextLine();
                    }
                    server.removeFromWaitP3();
                } else if (server.getNPlayers() == 2) {    //2
                    send("select your god between: " + server.getGods(0) + " " + server.getGods(1));
                    read2 = in.nextLine();
                    while (server.removeGods1(read2)) {
                        send("you have entered a god card that doesn't exist, select your god between: " + server.getGods(0) + "\t" + server.getGods(1) + "\n");
                        read2 = in.nextLine();
                    }
                    server.removeFromWait();
                }
            }

            else if(playerNumber==3){                               //PlayerNThree
                send("wait the P1... is taking the cards");
                server.putInWaitP3(); //Wait P1 to choose the cards
                send("select your god between: " + server.getGods(0)+ " " + server.getGods(1));
                read2 = in.nextLine();
                while (server.removeGods1(read2)){
                    send("you have entered a god card that doesn't exist, select your god between: " + server.getGods(0)+ "\t"+ server.getGods(1)+ "\n");
                        read2 = in.nextLine();
                }
                server.removeFromWait();
            }//END CARDS
            
            server.lobby(this, name, God.valueOf(read2));
            
            if(playerNumber == 1){
                send("wait other player sets their workers");
                server.putInWaitP1();
                askForCoordinates(in);
                notify(new Message(0, server.getWorkerPositions()));
            }
            else if(playerNumber == 2){
                send("waiting P1,P3 choosing their card...");
                server.putInWaitStart();
                askForCoordinates(in);
                if(server.getNPlayers()==3){
                    server.removeFromWaitP3();
                }else if(server.getNPlayers()==2){
                    server.removeFromWaitP1();
                }
            }
            else if(playerNumber == 3){
                send("wait other player sets their workers");
                server.putInWaitP3();
                askForCoordinates(in);
                server.removeFromWaitP1();
            }

            //GAME STARTED
            while(isActive()){
                read = in.nextLine();
                notify(read);

            }
        } catch (IOException | NoSuchElementException | InterruptedException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
        }
    }

    private void askForCoordinates(Scanner in) {
        send("set your first worker in coordinate: (x,y)");
        String coordinate = in.nextLine();
        String[] input = coordinate.split(",");
        while(!server.addWorkersPositions(Integer.parseInt(input[0]), Integer.parseInt(input[1]))) {
            send("this cell is yet occupied or is not in the board, set worker in new coordinate: (x,y) 0<=x<5 0<=y<5");
            coordinate = in.nextLine();
            input = coordinate.split(",");
        }

        send("set your second worker in coordinate: (x,y)");
        coordinate = in.nextLine();
        input = coordinate.split(",");
        while(!server.addWorkersPositions(Integer.parseInt(input[0]), Integer.parseInt(input[1]))) {
            send("this cell is yet occupied or is not in the board, set worker in new coordinate: (x,y) 0<x<5 0<y<5");
            coordinate = in.nextLine();
            input = coordinate.split(",");
        }
    }
}

