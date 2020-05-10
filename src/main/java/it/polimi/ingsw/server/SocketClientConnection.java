package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
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
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            name = read;
            if(playerNumber==1) {
                send("how many players?\n2 or 3?");

                String read0 = in.nextLine();
                server.setnPlayers(Integer.parseInt(read0));
                send("select "+server.getnPlayers()+" gods\n");
                String read1 = in.nextLine();
                String[] inputs = read1.split(",");
                for(int i=0; i<server.getnPlayers(); i++){
                    server.setGods(inputs[i]);
                }
                server.removeFromWaitP2();
                server.putInWaitChallenger();
                send("your god is: " + server.getGods(0));
                read2 = server.getGods(0);
                server.removeFromWaitStart();
            }
            else if(playerNumber==2){
                server.putInWaitP2();
                if(server.getnPlayers()==3) {
                    send("select your god between: " + server.getGods(0)+ " "+ server.getGods(1)+ " " + server.getGods(2));
                    read2 = in.nextLine();
                    server.removeGods(read2);
                    server.removeFromWaitP3();
                }else if(server.getnPlayers()==2) {
                    send("select your god between: " + server.getGods(0)+ " " + server.getGods(1));
                    read2 = in.nextLine();
                    server.removeGods(read2);
                    server.removeFromWait();
                }
            }
            else if(playerNumber==3){
                    server.putInWaitP3();
                    send("select your god between: " + server.getGods(0)+ " " + server.getGods(1));
                    read2 = in.nextLine();
                    server.removeGods(read2);
                    server.removeFromWait();
            }
            server.lobby(this, name, God.valueOf(read2));
            if(playerNumber == 1){
                send("wait other player sets their workers");
                server.putInWaitP1();
                send("set your first worker in coordinate: (x,y)");
                String coordinate = in.nextLine();
                String input[] = coordinate.split(",");
                while(!server.addWorkersPositions(Integer.parseInt(input[0]), Integer.parseInt(input[1]))) {
                    send("this cell is yet occupied or is not in the board, set worker in new coordinate: (x,y) 0<x<5 0<y<5");
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
                notify(new Message(0, server.getWorkerPositions()));
            }
            else if(playerNumber == 2){
                server.putInWaitStart();
                send("set your first worker in coordinate: (x,y)");
                String coordinate = in.nextLine();
                String input[] = coordinate.split(",");
                while(!server.addWorkersPositions(Integer.parseInt(input[0]), Integer.parseInt(input[1]))) {
                    send("this cell is yet occupied or is not in the board, set worker in new coordinate: (x,y) 0<x<5 0<y<5");
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
                if(server.getnPlayers()==3){
                    server.removeFromWaitP3();
                }else{
                    server.removeFromWaitP1();
                }
            }
            else if(playerNumber == 3){
                send("wait other player sets their workers");
                server.putInWaitP3();
                send("set your first worker in coordinate: (x,y)");
                String coordinate = in.nextLine();
                String input[] = coordinate.split(",");
                while(!server.addWorkersPositions(Integer.parseInt(input[0]), Integer.parseInt(input[1]))) {
                    send("this cell is yet occupied or is not in the board, set worker in new coordinate: (x,y) 0<x<5 0<y<5");
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
                server.removeFromWaitP1();
            }
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

