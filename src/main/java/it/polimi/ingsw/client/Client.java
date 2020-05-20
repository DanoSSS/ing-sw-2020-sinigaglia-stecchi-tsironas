package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                            case NOT_YOUR_TURN:
                            case SELECT_ACTIVE_WORKER:
                                setClientAction(a);
                                System.out.println(inputObject.getSentence());
                                break;
                            case WORKER_SET:
                                clientController = inputObject.getClientController().clone();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    for (int i = 0; i < inputObject.getNicknames().length; i++) {
                                        System.out.println(inputObject.getNicknames()[i]); //get the String[] with the output
                                    }
                                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                                    System.out.println("it's your turn!\nselect active worker:");
                                }
                                else {
                                    for (int i = 0; i < inputObject.getNicknames().length; i++) {
                                        System.out.println(inputObject.getNicknames()[i]); //get the String[] with the output
                                    }
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }
                                break;
                            case SELECT_COORDINATE_MOVE:
                                clientController.setCurrentRoundIdPlayer(inputObject.getnCurrentPlayer());
                                int id = inputObject.getCurrentActiveWorker();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleMoves = inputObject.getCurrentPossibleMoves();
                                    System.out.println("your active worker is " + id + " select coordinate to move among the following:");
                                    for (Coordinates c : possibleMoves) {
                                        System.out.println(c.getX() + "," + c.getY());
                                    }
                                }else{
                                    int n=clientController.getCurrentRoundIdPlayer();
                                    System.out.println("player"+n+" select worker"+id);
                                }
                                break;
                            case MOVE_AND_COORDINATE_BUILD:
                                id = inputObject.getCurrentActiveWorker();
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
                            case BUILD_END_TURN:
                                id = inputObject.getCurrentActiveWorker();
                                int n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer()==inputObject.getnCurrentPlayer()){
                                    System.out.println("your worker"+id+"build in "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel()+" dome:"+inputObject.getDome());
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }else if(clientController.getIdPlayer()==inputObject.getNextNPlayer()){
                                    System.out.println("player"+n+" build with his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel());
                                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                                    System.out.println("it's your turn!\nselect active worker:");
                                }else{
                                    System.out.println("player"+n+" build with his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel());
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }
                                break;
                            case ARTEMIS_FIRST_MOVE:
                                id = inputObject.getCurrentActiveWorker();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                                    setClientAction(a);
                                    System.out.println("your worker"+id+" is now in cell "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                    if(inputObject.getCurrentPossibleMoves().size() != 0){
                                        System.out.println("If you want activate power and move second select coordinate among the following otherwise write NO");
                                        for (Coordinates c : inputObject.getCurrentPossibleMoves()) {
                                            System.out.println(c.getX() + "," + c.getY());
                                        }
                                    }else System.out.println("you cannot activate Artemis power and move second");
                                }else {
                                    n=clientController.getCurrentRoundIdPlayer();
                                    System.out.println("player"+n+" moves his worker in cell"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());

                                }
                                break;
                            case ARTEMIS_SECOND_MOVE:
                                boolean bool=false;
                                id = inputObject.getCurrentActiveWorker();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                                    setClientAction(Action.MOVE_AND_COORDINATE_BUILD);
                                    if(inputObject.getCoordinate()==null){
                                        System.out.println("Select coordinate to build among the following:");
                                        for(Coordinates c: inputObject.getCurrentPossibleMoves()){
                                            System.out.println(c.getX()+","+c.getY());
                                        }
                                    }else{
                                        bool=true;
                                        System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following:");
                                        for(Coordinates c: inputObject.getCurrentPossibleMoves()){
                                            System.out.println(c.getX()+","+c.getY());
                                        }
                                    }
                                }else {
                                    n = clientController.getCurrentRoundIdPlayer();
                                    if (bool) {
                                        System.out.println("player"+n+" moves his worker in cell" + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY());
                                    } else {
                                        System.out.println("player"+n+" do not use his power");
                                    }
                                }
                                break;
                            case BUILD_ATLAS:
                                id = inputObject.getCurrentActiveWorker();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following.\n(If you want activate power and build a dome write dome follow by coordinate otherwise write std follow by coordinate)");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }else{
                                    n=clientController.getCurrentRoundIdPlayer();
                                    System.out.println("player"+n+" moves his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
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
                            case NOT_YOUR_TURN:
                            case ARTEMIS_FIRST_MOVE:
                            case BUILD_ATLAS:
                                socketOut.writeObject(new Message(getClientAction().getValue(),inputObject));
                                break;
                            case SELECT_ACTIVE_WORKER:
                                int i=Integer.parseInt(inputObject);
                                socketOut.writeObject(new Message(getClientAction().getValue(),i));
                                break;
                            case SELECT_COORDINATE_MOVE:
                            case MOVE_AND_COORDINATE_BUILD:
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
