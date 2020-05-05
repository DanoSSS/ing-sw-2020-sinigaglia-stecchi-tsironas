package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection,Runnable {
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

    @Override
    public void addObserver(Observer<String> observer) {

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
        String name;
        String read2 = "";
        boolean flag1 = false, flag2 = false;
        int nplayers = 0;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            name = read;
            if(playerNumber==1) {
                send("how many players?\n2 or 3?");
                nplayers = in.nextInt();
                send("select " + nplayers + " gods\n");
                String read1 = in.nextLine();
                String[] inputs = read1.split(",");
                for(int i=0; i<nplayers; i++){
                    server.setGods(inputs[i]);
                }
                notifyAll();
                while(!flag2){
                    wait();
                }
                send("your god is: " + server.getGods(0));
                read2 = server.getGods(0);
            }
            else if(playerNumber==2){
                wait();
                if(nplayers==3) {
                    send("select your god between: " + server.getGods(0) + server.getGods(1) + server.getGods(2));
                    read2 = in.nextLine();
                    server.removeGods(read2);
                    flag1 = true;
                    notifyAll();
                }else if(nplayers==2) {
                    send("select your god between: " + server.getGods(0) + server.getGods(1));
                    read2 = in.nextLine();
                    server.removeGods(read2);
                    flag2 = true;
                    notifyAll();
                }
            }
            else if(playerNumber==3){
                while(!flag1){
                    wait();
                    send("select your god between: " + server.getGods(0) + server.getGods(1));
                    read2 = in.nextLine();
                    server.removeGods(read2);
                    flag2 = true;
                    notifyAll();
                }
            }
            server.lobby(this, name, God.valueOf(read2));
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

}

