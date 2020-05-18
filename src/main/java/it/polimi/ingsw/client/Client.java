package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private Action clientAction;
    private ClientController clientController;
    private boolean active = true;

    public void setClientAction(Action a) {
        this.clientAction = a;
    }

    public Action getClientAction() {
        return clientAction;
    }

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        ReturnMessage inputObject = (ReturnMessage) socketIn.readObject();
                        Action a = inputObject.getAction();
                        System.out.println("received" + a.toString());
                        switch (a) {
                            case STRING:
                            case NOTYOURTURN:
                            case SELECTACTIVEWORKER:
                                setClientAction(a);
                                System.out.println(inputObject.getSentence());
                                break;
                            case WORKERSET:
                                clientController = inputObject.getClientController().clone();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    for (int i = 0; i < inputObject.getNicknames().length; i++) {
                                        System.out.println(inputObject.getNicknames()[i]); //get the String[] with the output
                                    }
                                    setClientAction(Action.SELECTACTIVEWORKER);
                                    System.out.println("it's your turn!\nselect active worker:");
                                }
                                else {
                                    for (int i = 0; i < inputObject.getNicknames().length; i++) {
                                        System.out.println(inputObject.getNicknames()[i]); //get the String[] with the output
                                    }
                                    setClientAction(Action.NOTYOURTURN);
                                    System.out.println("wait your turn");
                                }
                                break;
                            case SELECTCOORDINATEMOVE:
                                setClientAction(a);
                                int id = inputObject.getCurrentActiveWorker();
                                ArrayList<Coordinates> possibleMoves = inputObject.getCurrentPossibleMoves();
                                System.out.println("your active worker is "+id+" select coordinate to move among the following:");
                                for(Coordinates c: possibleMoves){
                                    System.out.println(c.getX()+","+c.getY());
                                }
                                break;
                            case MOVEANDCOORDINATEBUILD:
                                id = inputObject.getCurrentActiveWorker();
                                clientController.setCurrentRoundIdPlayer(inputObject.getnCurrentPlayer());
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following:");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }else{
                                    int n=clientController.getCurrentRoundIdPlayer();
                                    System.out.println("player"+n+" moves his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                }
                                break;
                            case BUILDENDTURN:
                                id = inputObject.getCurrentActiveWorker();
                                int n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer()==inputObject.getnCurrentPlayer()){
                                    System.out.println("your worker"+id+"build in "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel());
                                    setClientAction(Action.NOTYOURTURN);
                                    System.out.println("wait your turn");
                                }else if(clientController.getIdPlayer()==inputObject.getNextNPlayer()){
                                    System.out.println("player"+n+" build with his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel());
                                    setClientAction(Action.SELECTACTIVEWORKER);
                                    System.out.println("it's your turn!\nselect active worker:");
                                }else{
                                    System.out.println("player"+n+" build with his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel());
                                    setClientAction(Action.NOTYOURTURN);
                                    System.out.println("wait your turn");
                                }
                                break;

                        }
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final ObjectOutputStream socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        String inputObject = stdin.nextLine();
                        switch (getClientAction()){
                            case STRING:
                            case NOTYOURTURN:
                                socketOut.writeObject(new Message(getClientAction().getValue(),inputObject));
                                break;
                            case SELECTACTIVEWORKER:
                                int i=Integer.parseInt(inputObject);
                                socketOut.writeObject(new Message(getClientAction().getValue(),i));
                                break;
                            case SELECTCOORDINATEMOVE:
                            case MOVEANDCOORDINATEBUILD:
                                String[] input = inputObject.split(",");
                                Coordinates coordinates = new Coordinates(Integer.parseInt(input[0]),Integer.parseInt(input[1]));
                                socketOut.writeObject(new Message(getClientAction().getValue(),coordinates));
                                break;



                        }

                        socketOut.flush();
                    }
                }catch(Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream SocketIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream SocketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncReadFromSocket(SocketIn);
            Thread t1 = asyncWriteToSocket(stdin, SocketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            SocketIn.close();
            SocketOut.close();
            socket.close();
        }
    }
}
