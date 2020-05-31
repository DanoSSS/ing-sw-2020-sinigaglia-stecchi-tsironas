package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Worker;
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

public class ClientCLI {
    private String ip;
    private int port;
    private Action clientAction;
    private ClientController clientController;
    private boolean active = true;
    private CellMessage[][] board;

    public void setClientAction(Action a) {
        this.clientAction = a;
    }

    public Action getClientAction() {
        return clientAction;
    }

    public ClientCLI(String ip, int port){
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
                    int loseRound=5;
                    while (isActive()) {
                        ReturnMessage inputObject = (ReturnMessage) socketIn.readObject();
                        Action a = inputObject.getAction();
                        System.out.println("received" + a.toString());
                        switch (a) {
                            case STRING:
                            case FIRST_MESSAGE:
                            case NOT_YOUR_TURN:
                            case SELECT_ACTIVE_WORKER:
                            case NUMBER_OF_PLAYERS:
                            case WRONG_GODS:
                            case NICKNAME_ALREADY_USED:
                            case SELECT_GODS_CHALLENGER:
                            case SET_WORKER_POSITION:
                            case ERROR_SET_WORKER_POSITION:
                                setClientAction(a);
                                System.out.println(inputObject.getSentence());
                                break;
                            case CHOOSE_GOD:
                                setClientAction(a);
                                int j=inputObject.getNPlayer();
                                if(j==3){
                                    System.out.println("select your god between: " +inputObject.getGod1()+ "\t" +inputObject.getGod2()+ "\t" +inputObject.getGod3());
                                }else{
                                    System.out.println("select your god between: " +inputObject.getGod1()+ "\t" +inputObject.getGod2());
                                }
                                break;
                            case WORKER_SET:
                                clientController = inputObject.getClientController().clone();
                                board = clientController.getBoard();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    for (int i = 0; i < inputObject.getNicknames().length; i++) {
                                        System.out.println(inputObject.getNicknames()[i]); //get the String[] with the output
                                    }
                                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                                    print(board);
                                    System.out.println("it's your turn!\nselect active worker:");
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    for (int i = 0; i < inputObject.getNicknames().length; i++) {
                                        System.out.println(inputObject.getNicknames()[i]); //get the String[] with the output
                                    }
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    print(board);
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
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    int n=clientController.getCurrentRoundIdPlayer();
                                    System.out.println("player"+n+" select worker"+id);
                                }
                                break;
                            case MOVE_AND_COORDINATE_BUILD:
                                id = inputObject.getCurrentActiveWorker();
                                clientController.setWorkerCellMessage(id,inputObject.getCoordinate().getX()*2+1,inputObject.getCoordinate().getY());
                                if(inputObject.getOppWorker()!=null){
                                    clientController.setWorkerCellMessage(inputObject.getOppWorker().getIdWorker(),inputObject.getOppWorker().getCoordinates().getX()*2+1,inputObject.getOppWorker().getCoordinates().getY());
                                    if (inputObject.getOppWorker().getCoordinates().getX()!=inputObject.getCoordinateOld().getX() && inputObject.getOppWorker().getCoordinates().getY()!=inputObject.getCoordinateOld().getY()){
                                        clientController.freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                    }
                                }
                                else {
                                    clientController.freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                }
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    print(board);
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following:");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    int n=clientController.getCurrentRoundIdPlayer();
                                    print(board);
                                    System.out.println("player"+n+" moves his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                }
                                break;
                            case BUILD_END_TURN:
                                id = inputObject.getCurrentActiveWorker();
                                clientController.buildCellMessage(inputObject.getCoordinate().getX()*2,inputObject.getCoordinate().getY(),inputObject.getLevel(),inputObject.getDome());
                                int n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer()==inputObject.getnCurrentPlayer()){
                                    print(board);
                                    System.out.println("your worker"+id+"build in "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel()+" dome:"+inputObject.getDome());
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }
                                else if(clientController.getIdPlayer()==inputObject.getNextNPlayer() && clientController.getIdPlayer() != loseRound){
                                    print(board);
                                    System.out.println("player"+n+" build with his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel()+" dome:"+inputObject.getDome());
                                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                                    System.out.println("it's your turn!\nselect active worker:");
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    print(board);
                                    System.out.println("player"+n+" build with his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel()+" dome:"+inputObject.getDome());
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }
                                break;
                            case ARTEMIS_FIRST_MOVE:
                                id = inputObject.getCurrentActiveWorker();
                                clientController.setWorkerCellMessage(id,inputObject.getCoordinate().getX()*2+1,inputObject.getCoordinate().getY());
                                clientController.freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                                    setClientAction(a);
                                    print(board);
                                    System.out.println("your worker"+id+" is now in cell "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                    if(inputObject.getCurrentPossibleMoves().size() != 0){
                                        System.out.println("If you want activate power and move second select coordinate among the following otherwise write NO");
                                        for (Coordinates c : inputObject.getCurrentPossibleMoves()) {
                                            System.out.println(c.getX() + "," + c.getY());
                                        }
                                    }else System.out.println("you cannot activate Artemis power and move second");
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    n=clientController.getCurrentRoundIdPlayer();
                                    print(board);
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
                                        clientController.setWorkerCellMessage(id,inputObject.getCoordinate().getX()*2+1,inputObject.getCoordinate().getY());
                                        clientController.freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                        bool=true;
                                        print(board);
                                        System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following:");
                                        for(Coordinates c: inputObject.getCurrentPossibleMoves()){
                                            System.out.println(c.getX()+","+c.getY());
                                        }
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    n = clientController.getCurrentRoundIdPlayer();
                                    if (bool) {
                                        print(board);
                                        System.out.println("player"+n+" moves his worker in cell" + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY());
                                    } else {
                                        System.out.println("player"+n+" do not use his power");
                                    }
                                }
                                break;
                            case BUILD_ATLAS:
                                id = inputObject.getCurrentActiveWorker();
                                clientController.setWorkerCellMessage(id,inputObject.getCoordinate().getX()*2+1,inputObject.getCoordinate().getY());
                                clientController.freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    print(board);
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following.\n(If you want activate power and build a dome write dome follow by coordinate otherwise write std follow by coordinate)");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    n=clientController.getCurrentRoundIdPlayer();
                                    print(board);
                                    System.out.println("player"+n+" moves his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                }
                                break;
                            case BUILD_EPHAESTUS:
                                id = inputObject.getCurrentActiveWorker();
                                clientController.setWorkerCellMessage(id,inputObject.getCoordinate().getX()*2+1,inputObject.getCoordinate().getY());
                                clientController.freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    print(board);
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following.\n(If you want activate power and build a second time in the same space write yes follow by coordinate otherwise write no follow by coordinate)");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    n=clientController.getCurrentRoundIdPlayer();
                                    print(board);
                                    System.out.println("player"+n+" moves his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                }
                                break;
                            case END_TURN:
                                id = inputObject.getCurrentActiveWorker();
                                n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer()==inputObject.getnCurrentPlayer()){
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }
                                else if(clientController.getIdPlayer()==inputObject.getNextNPlayer() && clientController.getIdPlayer() != loseRound){
                                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                                    System.out.println("it's your turn!\nselect active worker:");
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }
                                break;
                            case FIRST_BUILD_DEMETER:
                                id = inputObject.getCurrentActiveWorker();
                                clientController.buildCellMessage(inputObject.getCoordinate().getX()*2,inputObject.getCoordinate().getY(),inputObject.getLevel(),inputObject.getDome());
                                n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    print(board);
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following.\n(If you want activate power and build a second time write coordinate otherwise write NO)");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    print(board);
                                    System.out.println("player"+n+" moves his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                }
                                break;
                            case PROMETHEUS_CHOOSE:
                                clientController.setCurrentRoundIdPlayer(inputObject.getnCurrentPlayer());
                                id = inputObject.getCurrentActiveWorker();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleMoves = inputObject.getCurrentPossibleMoves();
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleC2();
                                    print(board);
                                    System.out.println("your active worker is " + id + "\nIf you want activate your power and build before move write BUILD and select coordinate among the following: ");
                                    for (Coordinates c : possibleBuilds) {
                                        System.out.println(c.getX() + "," + c.getY());
                                    }
                                    System.out.println("otherwise write MOVE and select coordinate among the following: ");
                                    for (Coordinates c : possibleMoves) {
                                        System.out.println(c.getX() + "," + c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    n=clientController.getCurrentRoundIdPlayer();
                                    System.out.println("player"+n+" select worker"+id);
                                }
                                break;
                            case FIRST_BUILD_PROMETHEUS:
                                id = inputObject.getCurrentActiveWorker();
                                clientController.buildCellMessage(inputObject.getCoordinate().getX()*2,inputObject.getCoordinate().getY(),inputObject.getLevel(),inputObject.getDome());
                                n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                                    setClientAction(Action.SELECT_COORDINATE_MOVE);
                                    ArrayList<Coordinates> possibleMoves = inputObject.getCurrentPossibleMoves();
                                    print(board);
                                    System.out.println("your worker"+id+"build in "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel()+" dome:"+inputObject.getDome());
                                    System.out.println("select coordinate to move among the following:");
                                    for (Coordinates c : possibleMoves) {
                                        System.out.println(c.getX() + "," + c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    System.out.println("player"+n+" build with his power with his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+" level:"+inputObject.getLevel()+" dome:"+inputObject.getDome());
                                }
                                break;
                            case LOSE3P:
                                Worker wk1=inputObject.getOppWorker();
                                clientController.freeWorkerCellMessage(wk1.getCoordinates().getX(),wk1.getCoordinates().getY());
                                Worker wk2=inputObject.getOtherWorker();
                                clientController.freeWorkerCellMessage(wk2.getCoordinates().getX(),wk2.getCoordinates().getY());
                                loseRound=inputObject.getnCurrentPlayer();
                                print(board);
                                if(clientController.getIdPlayer()==inputObject.getnCurrentPlayer()){
                                    setClientAction(Action.LOSE);
                                    System.out.println("you lose, wait other player finish the game");
                                }
                                else if(clientController.getIdPlayer()==inputObject.getNextNPlayer()){
                                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                                    System.out.println("player"+inputObject.getnCurrentPlayer()+" lose\nit's your turn!\nselect active worker:");
                                }
                                else{
                                    System.out.println("player"+inputObject.getnCurrentPlayer()+" lose");
                                }
                                break;
                            case LOSE:
                                if(clientController.getIdPlayer()==inputObject.getnCurrentPlayer()){
                                    setClientAction(a);
                                    System.out.println("you lose.\nPress Q to exit");
                                }
                                else{
                                    setClientAction(Action.WIN);
                                    System.out.println("player"+inputObject.getnCurrentPlayer()+" lose.\nCONGRATULATIONS, YOU WIN!!!\nPress Q to exit");
                                }
                                break;
                            case WIN:
                                if(clientController.getIdPlayer()==inputObject.getnCurrentPlayer()){
                                    setClientAction(a);
                                    System.out.println("CONGRATULATIONS, YOU WIN!!!\nPress Q to exit");
                                }
                                else{
                                    setClientAction(Action.LOSE);
                                    System.out.println("player"+inputObject.getnCurrentPlayer()+"win.\nyou lose.\nPress Q to exit");
                                }
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
                            case FIRST_MESSAGE:
                            case NOT_YOUR_TURN:
                            case ARTEMIS_FIRST_MOVE:
                            case BUILD_ATLAS:
                            case BUILD_EPHAESTUS:
                            case FIRST_BUILD_DEMETER:
                            case PROMETHEUS_CHOOSE:
                            case LOSE:
                            case WIN:
                            case NUMBER_OF_PLAYERS:
                            case WRONG_GODS:
                            case CHOOSE_GOD:
                            case NICKNAME_ALREADY_USED:
                            case SELECT_GODS_CHALLENGER:
                            case SET_WORKER_POSITION:
                            case ERROR_SET_WORKER_POSITION:
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

    public void print(CellMessage[][] board){
        System.out.println("---------------------------------------------");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++){
                System.out.print("|  "+board[i][j]+"  |");
            }System.out.println();
            if(i>0 && i%2!=0) {
                System.out.println("---------------------------------------------");
            }
        }
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
