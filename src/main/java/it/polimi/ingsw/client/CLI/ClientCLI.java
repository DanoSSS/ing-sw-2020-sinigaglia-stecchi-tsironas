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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientCLI {
    private String ip;
    private int port;
    private Action clientAction;
    private ClientController clientController;
    private boolean active = true;
    private CellMessage[][] board;
    private int loseRound=-1;
    private ArrayList<Coordinates> possibleMoves = null;
    private ArrayList<Coordinates> possibleBuilds = null;


    public ClientCLI(String ip, int port){
        this.ip = ip;
        this.port = port;
    }


    public void setClientAction(Action a) {
        this.clientAction = a;
    }

    public Action getClientAction() {
        return clientAction;
    }

    public ArrayList<Coordinates> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<Coordinates> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public ArrayList<Coordinates> getPossibleBuilds() {
        return possibleBuilds;
    }

    public void setPossibleBuilds(ArrayList<Coordinates> possibleBuilds) {
        this.possibleBuilds = possibleBuilds;
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
                                int j = inputObject.getNPlayer();
                                if (j == 3) {
                                    System.out.println("select your god between: " + inputObject.getGod1() + "\t" + inputObject.getGod2() + "\t" + inputObject.getGod3());
                                } else {
                                    System.out.println("select your god between: " + inputObject.getGod1() + "\t" + inputObject.getGod2());
                                }
                                break;
                            case WORKER_SET:
                                clientController = inputObject.getClientController().clone();
                                List<Worker> keys= new ArrayList<Worker>(inputObject.getWorkerPosition().keySet());
                                if(keys.size()==4){
                                    for(int i=0;i<4;i++){
                                        int x=inputObject.getWorkerPosition().get(keys.get(i)).getX();
                                        int y=inputObject.getWorkerPosition().get(keys.get(i)).getY();
                                        int id=keys.get(i).getIdWorker();
                                        setWorkerCellMessage(id,x*2+1,y);
                                    }
                                }
                                else if(keys.size()==6) {
                                    for (int i=0;i<6;i++) {
                                        int x=inputObject.getWorkerPosition().get(keys.get(i)).getX();
                                        int y=inputObject.getWorkerPosition().get(keys.get(i)).getY();
                                        int id=keys.get(i).getIdWorker();
                                        setWorkerCellMessage(id,x*2+1,y);
                                    }
                                }
                                for (int i = 0; i < inputObject.getNicknames().length; i++) {
                                    System.out.println(inputObject.getNicknames()[i]); //get the String[] with the output
                                }
                                if (clientController.getIdPlayer() == clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                                    print();
                                    System.out.println("it's your turn!\nselect active worker:");
                                } else if (clientController.getIdPlayer() != clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    print();
                                    System.out.println("wait your turn");
                                }
                                break;
                            case SELECT_COORDINATE_MOVE:
                                clientController.setCurrentRoundIdPlayer(inputObject.getnCurrentPlayer());
                                int id = inputObject.getCurrentActiveWorker();
                                if (clientController.getIdPlayer() == clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleMoves = inputObject.getCurrentPossibleMoves();
                                    setPossibleMoves(possibleMoves);
                                    System.out.println("your active worker is " + id + " select coordinate to move among the following:");
                                    for (Coordinates c : possibleMoves) {
                                        System.out.println(c.getX() + "," + c.getY());
                                    }
                                } else if (clientController.getIdPlayer() != loseRound) {
                                    int n = clientController.getCurrentRoundIdPlayer();
                                    System.out.println("player" + n + " select worker" + id);
                                }
                                break;
                            case MOVE_AND_COORDINATE_BUILD:
                                id = inputObject.getCurrentActiveWorker();
                                setWorkerCellMessage(id, inputObject.getCoordinate().getX() * 2 + 1, inputObject.getCoordinate().getY());
                                if (inputObject.getOppWorker() != null) {
                                    setWorkerCellMessage(inputObject.getOppWorker().getIdWorker(), inputObject.getOppWorker().getCoordinates().getX() * 2 + 1, inputObject.getOppWorker().getCoordinates().getY());
                                    if (inputObject.getOppWorker().getCoordinates().getX() != inputObject.getCoordinateOld().getX() && inputObject.getOppWorker().getCoordinates().getY() != inputObject.getCoordinateOld().getY()) {
                                        freeWorkerCellMessage(inputObject.getCoordinateOld().getX() * 2 + 1, inputObject.getCoordinateOld().getY());
                                    }
                                } else {
                                    freeWorkerCellMessage(inputObject.getCoordinateOld().getX() * 2 + 1, inputObject.getCoordinateOld().getY());
                                }
                                if (clientController.getIdPlayer() == clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    setPossibleMoves(possibleBuilds);
                                    print();
                                    System.out.println("your worker" + id + " is now in coordinate " + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY() + "\nSelect coordinate to build among the following:");
                                    for (Coordinates c : possibleBuilds) {
                                        System.out.println(c.getX() + "," + c.getY());
                                    }
                                } else if (clientController.getIdPlayer() != loseRound) {
                                    int n = clientController.getCurrentRoundIdPlayer();
                                    print();
                                    System.out.println("player" + n + " moves his worker" + id + " in cell:" + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY());
                                }
                                break;
                            case BUILD_END_TURN:
                                id = inputObject.getCurrentActiveWorker();
                                buildCellMessage(inputObject.getCoordinate().getX() * 2, inputObject.getCoordinate().getY(), inputObject.getLevel(), inputObject.getDome());
                                int n = inputObject.getnCurrentPlayer();
                                if (clientController.getIdPlayer() == inputObject.getnCurrentPlayer()) {
                                    print();
                                    System.out.println("your worker" + id + "build in " + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY() + " level:" + inputObject.getLevel() + " dome:" + inputObject.getDome());
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                } else if (clientController.getIdPlayer() == inputObject.getNextNPlayer() && clientController.getIdPlayer() != loseRound) {
                                    print();
                                    System.out.println("player" + n + " build with his worker" + id + " in cell:" + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY() + " level:" + inputObject.getLevel() + " dome:" + inputObject.getDome());
                                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                                    System.out.println("it's your turn!\nselect active worker:");
                                } else if (clientController.getIdPlayer() != loseRound) {
                                    print();
                                    System.out.println("player" + n + " build with his worker" + id + " in cell:" + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY() + " level:" + inputObject.getLevel() + " dome:" + inputObject.getDome());
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }
                                break;
                            case ARTEMIS_FIRST_MOVE:
                                id = inputObject.getCurrentActiveWorker();
                                setWorkerCellMessage(id, inputObject.getCoordinate().getX() * 2 + 1, inputObject.getCoordinate().getY());
                                freeWorkerCellMessage(inputObject.getCoordinateOld().getX() * 2 + 1, inputObject.getCoordinateOld().getY());
                                if (clientController.getIdPlayer() == clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    print();
                                    System.out.println("your worker" + id + " is now in cell " + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY());
                                    ArrayList<Coordinates> possibleMoves = inputObject.getCurrentPossibleMoves();
                                    setPossibleMoves(possibleMoves);
                                    if (possibleMoves.size() != 0) {
                                        System.out.println("If you want activate power and move second select coordinate among the following otherwise write NO");
                                        for (Coordinates c : possibleMoves) {
                                            System.out.println(c.getX() + "," + c.getY());
                                        }
                                    } else System.out.println("you cannot activate Artemis power and move second");
                                } else if (clientController.getIdPlayer() != loseRound) {
                                    n = clientController.getCurrentRoundIdPlayer();
                                    print();
                                    System.out.println("player" + n + " moves his worker in cell" + inputObject.getCoordinate().getX() + "," + inputObject.getCoordinate().getY());

                                }
                                break;
                            case ARTEMIS_SECOND_MOVE:
                                boolean bool=false;
                                id = inputObject.getCurrentActiveWorker();
                                if(inputObject.getCoordinate()!=null){
                                    setWorkerCellMessage(id,inputObject.getCoordinate().getX()*2+1,inputObject.getCoordinate().getY());
                                    freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                    print();
                                    bool=true;
                                }
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                                    setClientAction(Action.MOVE_AND_COORDINATE_BUILD);
                                    ArrayList<Coordinates> possibleMoves = inputObject.getCurrentPossibleMoves();
                                    setPossibleMoves(possibleMoves);

                                    if(inputObject.getCoordinate()==null){
                                        System.out.println("Select coordinate to build among the following:");
                                        for(Coordinates c: possibleMoves){
                                            System.out.println(c.getX()+","+c.getY());
                                        }
                                    }else{
                                        System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following:");
                                        for(Coordinates c: possibleMoves){
                                            System.out.println(c.getX()+","+c.getY());
                                        }
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
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
                                setWorkerCellMessage(id,inputObject.getCoordinate().getX()*2+1,inputObject.getCoordinate().getY());
                                freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    setPossibleMoves(possibleBuilds);
                                    print();
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following.\n(If you want activate power and build a dome write dome follow by coordinate otherwise write std follow by coordinate)");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    n=clientController.getCurrentRoundIdPlayer();
                                    print();
                                    System.out.println("player"+n+" moves his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                }
                                break;
                            case BUILD_EPHAESTUS:
                                id = inputObject.getCurrentActiveWorker();
                                setWorkerCellMessage(id,inputObject.getCoordinate().getX()*2+1,inputObject.getCoordinate().getY());
                                freeWorkerCellMessage(inputObject.getCoordinateOld().getX()*2+1,inputObject.getCoordinateOld().getY());
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    print();
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following.\n(If you want activate power and build a second time in the same space write yes follow by coordinate otherwise write no follow by coordinate)");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    n=clientController.getCurrentRoundIdPlayer();
                                    print();
                                    System.out.println("player"+n+" moves his worker"+id+" in cell:"+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY());
                                }
                                break;
                            case END_TURN:
                                id = inputObject.getNextNPlayer();
                                n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer() == n){
                                    setClientAction(Action.NOT_YOUR_TURN);
                                    System.out.println("wait your turn");
                                }
                                else if(clientController.getIdPlayer() == id && clientController.getIdPlayer() != loseRound){
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
                                buildCellMessage(inputObject.getCoordinate().getX()*2,inputObject.getCoordinate().getY(),inputObject.getLevel(),inputObject.getDome());
                                n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                                    setClientAction(a);
                                    ArrayList<Coordinates> possibleBuilds = inputObject.getCurrentPossibleMoves();
                                    setPossibleMoves(possibleBuilds);
                                    print();
                                    System.out.println("your worker"+id+" is now in coordinate "+inputObject.getCoordinate().getX()+","+inputObject.getCoordinate().getY()+"\nSelect coordinate to build among the following.\n(If you want activate power and build a second time write coordinate otherwise write NO)");
                                    for(Coordinates c: possibleBuilds){
                                        System.out.println(c.getX()+","+c.getY());
                                    }
                                }
                                else if(clientController.getIdPlayer() != loseRound){
                                    print();
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
                                    setPossibleMoves(possibleMoves);
                                    setPossibleBuilds(possibleBuilds);
                                    print();
                                    System.out.println("(BUILD \"x,y\") your active worker is " + id + "\nIf you want activate your power and build before move write BUILD and select coordinate among the following: ");
                                    for (Coordinates c : possibleBuilds) {
                                        System.out.println(c.getX() + "," + c.getY());
                                    }
                                    System.out.println("(MOVE \"x,y\") otherwise write MOVE and select coordinate among the following: ");
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
                                buildCellMessage(inputObject.getCoordinate().getX()*2,inputObject.getCoordinate().getY(),inputObject.getLevel(),inputObject.getDome());
                                n =inputObject.getnCurrentPlayer();
                                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                                    setClientAction(Action.SELECT_COORDINATE_MOVE);
                                    ArrayList<Coordinates> possibleMoves = inputObject.getCurrentPossibleMoves();
                                    setPossibleMoves(possibleMoves);
                                    print();
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
                                freeWorkerCellMessage(wk1.getCoordinates().getX(),wk1.getCoordinates().getY());
                                Worker wk2=inputObject.getOtherWorker();
                                freeWorkerCellMessage(wk2.getCoordinates().getX(),wk2.getCoordinates().getY());
                                loseRound=inputObject.getnCurrentPlayer();
                                print();
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
                            case GAME_OVER:
                                setClientAction(a);
                                if(clientController.getIdPlayer()==inputObject.getnCurrentPlayer()) {
                                    if (inputObject.getLevel() == 1) {
                                        System.out.println("YOU WIN!!\nwrite something to continue");
                                    } else if (inputObject.getLevel() == 0) {
                                        System.out.println("YOU LOSE!!\nwrite something to continue");
                                    }
                                }
                                else if (clientController.getIdPlayer()!=loseRound){
                                    if (inputObject.getLevel() == 0) {
                                        System.out.println("YOU WIN!!\nwrite something to continue");
                                    } else if (inputObject.getLevel() == 1) {
                                        System.out.println("YOU LOSE!!\nwrite something to continue");
                                    }
                                }
                                break;
                            case PLAYER_DISCONNECTED:
                                setClientAction(a);
                                int playerDisconnected = inputObject.getnCurrentPlayer();
                                System.out.println("player n°" + playerDisconnected + " disconnected from the server.\ngame over.");
                                System.exit(-1);
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
                        boolean correctInput =false;
                        String inputObject = stdin.nextLine();
                        switch (getClientAction()) {
                            case STRING:
                            case FIRST_MESSAGE:
                            case NOT_YOUR_TURN:
                            case ARTEMIS_FIRST_MOVE:
                            case BUILD_EPHAESTUS:
                            case FIRST_BUILD_DEMETER:
                            case BUILD_ATLAS:
                            case NUMBER_OF_PLAYERS:
                            case WRONG_GODS:
                            case CHOOSE_GOD:
                            case NICKNAME_ALREADY_USED:
                            case SELECT_GODS_CHALLENGER:
                            case ERROR_SET_WORKER_POSITION:
                                socketOut.writeObject(new Message(getClientAction().getValue(), inputObject));
                                break;
                            case SET_WORKER_POSITION:
                                    String[] testInput = inputObject.split(","); //test if correct the input (es: '1,1' and not '1m1')
                                    if(testInput.length==2) {
                                        socketOut.writeObject(new Message(getClientAction().getValue(), inputObject));
                                    } else {
                                        System.out.println("ERROR: format -> x,y");
                                    }
                                break;
                            case SELECT_ACTIVE_WORKER:
                                try {
                                    int i = Integer.parseInt(inputObject);
                                    if(clientController.checkIdWorker(i)) {
                                        socketOut.writeObject(new Message(getClientAction().getValue(),i));
                                    }else{
                                        int[] idWorkers = clientController.getIdWorkers();
                                        System.out.println("choose one of yours:\t" + idWorkers[0] + " or " + idWorkers[1]);
                                    }

                                }catch (NumberFormatException e){
                                    System.out.println("ERROR:\nYou must choose an ID representing one of your worker");
                                }
                                break;
                            case SELECT_COORDINATE_MOVE:
                            case MOVE_AND_COORDINATE_BUILD:
                                String[] input = inputObject.split(",");
                                try {
                                    int x =Integer.parseInt(input[0]);
                                    int y = Integer.parseInt(input[1]);
                                    Coordinates coordinates = new Coordinates(x,y);
                                    if (coordinates==null){throw new NumberFormatException();} //if the creation of coordinate stopped anomaly, throw exception
                                    for (Coordinates c : possibleMoves) {
                                        if (c.equals(coordinates) && !correctInput) {
                                            correctInput = true;
                                        }
                                    }
                                    if (correctInput) {
                                        socketOut.writeObject(new Message(getClientAction().getValue(), coordinates));
                                    } else if (!correctInput) {
                                        System.out.println("ERROR: write one of the previous coordinate");
                                    }
                                }catch (NumberFormatException e){
                                    System.out.println("ERROR: Write one of the previous coordinate in the same format: \"x,y\"");
                                }
                                break;
                            case PROMETHEUS_CHOOSE:
                                String[] choose = inputObject.split(" ");
                                if(choose[0].toUpperCase() == "BUILD"){
                                    correctInput = isCorrectInput(choose, possibleBuilds);
                                } else if (choose[0].toUpperCase() =="MOVE"){
                                    correctInput = isCorrectInput(choose, possibleMoves);
                                }
                                if(!correctInput){
                                        System.out.println("ERROR: write MOVE \"x,y\" or \"BUILD x,y\".\nx,y must be correct.");
                                    }else if(correctInput) {
                                    socketOut.writeObject(new Message(getClientAction().getValue(), inputObject));
                                }
                                break;
                            case GAME_OVER:
                                socketOut.writeObject(new Message(4,inputObject));
                                System.exit(-1);
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

    private boolean isCorrectInput(String[] choose, ArrayList<Coordinates> possibleMoves) {
        String[] coordinate = choose[1].split(",");
        int x = Integer.parseInt(coordinate[0]);
        int y = Integer.parseInt(coordinate[1]);
        Coordinates coordinate1= new Coordinates(x,y);
        for(Coordinates c : possibleMoves){
            if(coordinate1.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public void startCLIBoard() {
        board = new CellMessage[10][5];
        for (int i = 0; i < 10; i=i+2) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = CellMessage.LV0;
            }
        }
        for(int l=1; l<10; l=l+2){
            for (int j = 0; j < 5; j++) {
                board[l][j] = CellMessage.free;
            }
        }
    }

    public void setWorkerCellMessage(int id,int x,int y){
        switch (id){
            case 0:
                board[x][y] = CellMessage.W0;
                break;
            case 1:
                board[x][y] = CellMessage.W1;
                break;
            case 2:
                board[x][y] = CellMessage.W2;
                break;
            case 3:
                board[x][y] = CellMessage.W3;
                break;
            case 4:
                board[x][y] = CellMessage.W4;
                break;
            case 5:
                board[x][y] = CellMessage.W5;
                break;
        }
    }

    public void freeWorkerCellMessage(int x,int y){
        board[x][y] = CellMessage.free;
    }

    public void buildCellMessage(int x,int y,int level,boolean dome) {
        if (dome) {
            board[x][y] = CellMessage.DOME;
        } else {
            switch (level) {
                case 1:
                    board[x][y] = CellMessage.LV1;
                    break;
                case 2:
                    board[x][y] = CellMessage.LV2;
                    break;
                case 3:
                    board[x][y] = CellMessage.LV3;
                    break;
                case 4:
                    board[x][y] = CellMessage.DOME;
                    break;
            }
        }
    }

    public void print(){
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
        startCLIBoard();

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
