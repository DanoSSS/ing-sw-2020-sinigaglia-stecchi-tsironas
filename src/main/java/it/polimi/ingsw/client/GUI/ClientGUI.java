package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import javax.print.attribute.standard.NumberUp;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimerTask;


public class ClientGUI  {
    private String ip;
    private int port;
    private Action clientAction;
    private ClientController clientController;
    private boolean active = true;
    private SantoriniMainFrame santoriniMainFrame;
    private StartingFrame startingFrame;
    private ObjectOutputStream socketOut;
    int np=0;
    private int choosePrometheus;
    private int loseRound=-1;


    public StartingFrame getStartingFrame() {
        return startingFrame;
    }

    public void setStartingFrame(StartingFrame startingFrame) {
        this.startingFrame = startingFrame;
    }

    public SantoriniMainFrame getSantoriniMainFrame() {
        return santoriniMainFrame;
    }

    public void setSantoriniMainFrame(SantoriniMainFrame santoriniMainFrame) {
        this.santoriniMainFrame = santoriniMainFrame;
    }

    public void setClientAction(Action a) {
        this.clientAction = a;
    }

    public Action getClientAction() {
        return clientAction;
    }

    public ClientGUI(String ip, int port){
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
                        messageHandler(inputObject);
                    }
                } catch (Exception e){
                        setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(Message message){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketOut.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return t;
    }

    public void messageHandler (ReturnMessage message) throws InterruptedException, IOException {
        Action a = message.getAction();
        switch (a){
            case FIRST_MESSAGE:
                Thread.sleep(3000);
            case NICKNAME_ALREADY_USED:
                String inputValue = JOptionPane.showInputDialog(message.getSentence());
                asyncWriteToSocket(new Message(a.getValue(), inputValue));
                break;
            case NUMBER_OF_PLAYERS:
                Object[] possibleValues = { "2", "3" };
                ImageIcon playerImage = new ImageIcon("src/main/resources/playernumber.png");
                Object selectedValue = JOptionPane.showInputDialog(null,
                        "how many players?", "game setup",
                        JOptionPane.INFORMATION_MESSAGE, playerImage,
                        possibleValues, possibleValues[0]);
                asyncWriteToSocket(new Message(a.getValue(), (String)selectedValue));
                np = Integer.parseInt((String)selectedValue);
                break;
            case SELECT_GODS_CHALLENGER:
                ArrayList<String> gods = new ArrayList<>();
                gods.add("APOLLO");
                gods.add("ARTEMIS");
                gods.add("ATHENA");
                gods.add("ATLAS");
                gods.add("DEMETER");
                gods.add("EPHAESTUS");
                gods.add("MINOTAUR");
                gods.add("PAN");
                gods.add("PROMETHEUS");
                GodSelectionFrame gsf = new GodSelectionFrame(9,"SELECT "+np+" GODS BETWEEN:",gods,np,this);
                break;
            case CHOOSE_GOD:
                gods = new ArrayList<>();
                if(message.getNPlayer()==3){
                    gods.add(message.getGod1());
                    gods.add(message.getGod2());
                    gods.add(message.getGod3());
                }
                else if(message.getNPlayer()==2){
                    gods.add(message.getGod1());
                    gods.add(message.getGod2());
                }
                gsf = new GodSelectionFrame(message.getNPlayer(),"SELECT YOUR GOD",gods,np,this);
                break;
            case SET_WORKER_POSITION:
            case ERROR_SET_WORKER_POSITION:
                ArrayList<String> pv = new ArrayList<>();

                for(int i=0;i<5;i++){
                    for(int j=0;j<5;j++){
                        if(message.getCurrentPossibleMoves().size()==0){
                            pv.add(i+","+j);
                        }
                        else {
                            Coordinates coordinate=(new Coordinates(i,j));
                            boolean flag=false;
                            for (Coordinates c : message.getCurrentPossibleMoves()) {
                                if (c.equals(coordinate)){
                                    flag =true;
                                }
                            }
                            if(!flag){
                                pv.add(i+","+j);
                                flag=false;
                            }
                        }
                    }
                }
                Object[] possibleValues1 = pv.toArray();
                String m;
                if(message.getAction()==Action.SET_WORKER_POSITION){
                    if(message.getLevel()==1){
                        m = "select coordinate for your first worker";
                    }
                    else {
                        m = "select coordinate for your second worker";
                    }
                }
                else{
                    if(message.getLevel()==1){
                        m = "error:this coordinate is not available,select again coordinate for your first worker";
                    }
                    else {
                        m = "error:this coordinate is not available,select again coordinate for your second worker";
                    }
                }
                Object selectedValue1 = JOptionPane.showInputDialog(null,
                        m, "worker coordinates",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        possibleValues1, possibleValues1[0]);
                asyncWriteToSocket(new Message(a.getValue(), (String)selectedValue1));
                break;
            case WORKER_SET:
                clientController = message.getClientController().clone();
                List<Worker> keys= new ArrayList<Worker>(message.getWorkerPosition().keySet());
                if(keys.size()==4){
                    for(int i=0;i<4;i++){
                        int x=message.getWorkerPosition().get(keys.get(i)).getX();
                        int y=message.getWorkerPosition().get(keys.get(i)).getY();

                        santoriniMainFrame.getBoardPanel().drawFirstWorker(x,y,keys.get(i).getIdWorker());
                        santoriniMainFrame.repaint();
                    }
                }
                else if(keys.size()==6) {
                    for (int i=0;i<6;i++) {
                        int x=message.getWorkerPosition().get(keys.get(i)).getX();
                        int y=message.getWorkerPosition().get(keys.get(i)).getY();
                        santoriniMainFrame.getBoardPanel().drawFirstWorker(x,y, keys.get(i).getIdWorker());
                        santoriniMainFrame.repaint();
                    }
                }
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                    santoriniMainFrame.getLog().append("It's your turn!\nSelect your active worker!");
                }
                else if(clientController.getIdPlayer() != clientController.getCurrentRoundIdPlayer()) {
                    setClientAction(Action.NOT_YOUR_TURN);
                    santoriniMainFrame.getLog().append("Wait your turn!");
                }
                santoriniMainFrame.repaint();
                break;
            case SELECT_COORDINATE_MOVE:
                clientController.setCurrentRoundIdPlayer(message.getnCurrentPlayer());
                int id = message.getCurrentActiveWorker();
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                    setClientAction(a);
                    ArrayList<Coordinates> possibleMoves = message.getCurrentPossibleMoves();
                    santoriniMainFrame.getLog().append("\nYour active worker is " + id + "\nSelect coordinate to move");
                    santoriniMainFrame.getBoardPanel().drawPossibleBorder(possibleMoves);
                }else if(clientController.getIdPlayer() != loseRound) {
                    int n = clientController.getCurrentRoundIdPlayer();
                    santoriniMainFrame.getLog().append("\nplayer" +n+ "select worker" +id);
                }
                break;
            case MOVE_AND_COORDINATE_BUILD:
                santoriniMainFrame.getBoardPanel().setDefaultBorder();
                id = message.getCurrentActiveWorker();
                if(message.getOppWorker()!=null) {
                    santoriniMainFrame.getBoardPanel().removeWorker(message.getCoordinate().getX(), message.getCoordinate().getY());
                }
                santoriniMainFrame.getBoardPanel().drawWorker(message.getCoordinate().getX(),message.getCoordinate().getY(),id);
                santoriniMainFrame.getBoardPanel().removeWorker(message.getCoordinateOld().getX(), message.getCoordinateOld().getY());
                santoriniMainFrame.getBoardPanel().repaint();
                if(message.getOppWorker()!=null) {
                    santoriniMainFrame.getBoardPanel().drawWorker(message.getOppWorker().getCoordinates().getX(), message.getOppWorker().getCoordinates().getY(), message.getOppWorker().getIdWorker());
                    santoriniMainFrame.getBoardPanel().repaint();
                }
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                    setClientAction(a);
                    ArrayList<Coordinates> possibleBuilds = message.getCurrentPossibleMoves();
                    santoriniMainFrame.getLog().append("\nSelect coordinate to build");
                    santoriniMainFrame.getBoardPanel().drawPossibleBorder(possibleBuilds);
                }
                break;
            case BUILD_END_TURN:
                santoriniMainFrame.getBoardPanel().setDefaultBorder();
                santoriniMainFrame.getBoardPanel().drawLevel(message.getCoordinate().getX(),message.getCoordinate().getY(),message.getLevel(),message.getDome());
                if(clientController.getIdPlayer()==message.getnCurrentPlayer()){
                    setClientAction(Action.NOT_YOUR_TURN);
                    santoriniMainFrame.getLog().append("\nWait your turn");
                }
                else if(clientController.getIdPlayer()==message.getNextNPlayer() && clientController.getIdPlayer() != loseRound){
                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                    santoriniMainFrame.getLog().append("\nIt's your turn!\nselect active worker:");
                }
                else if(clientController.getIdPlayer() != loseRound){
                    setClientAction(Action.NOT_YOUR_TURN);
                    santoriniMainFrame.getLog().append("\nWait your turn");
                }
                break;
            case ARTEMIS_FIRST_MOVE:
                santoriniMainFrame.getBoardPanel().setDefaultBorder();
                id = message.getCurrentActiveWorker();
                santoriniMainFrame.getBoardPanel().drawWorker(message.getCoordinate().getX(),message.getCoordinate().getY(),id);
                santoriniMainFrame.getBoardPanel().removeWorker(message.getCoordinateOld().getX(), message.getCoordinateOld().getY());
                santoriniMainFrame.getBoardPanel().repaint();
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                    setClientAction(Action.ARTEMIS_FIRST_MOVE);
                    Object[] possibleChoice = {"YES", "NO"};
                    Object answer = JOptionPane.showInputDialog(null,
                            "Do you want activate your god's power", "ARTEMIS POWER",
                            JOptionPane.INFORMATION_MESSAGE, null,
                            possibleChoice, possibleChoice[0]);
                    if (answer == "YES") {
                        santoriniMainFrame.getBoardPanel().drawPossibleBorder(message.getCurrentPossibleMoves());
                    } else if (answer == "NO") {
                        asyncWriteToSocket(new Message(getClientAction().getValue(), "NO"));
                    }
                }
                break;
            case ARTEMIS_SECOND_MOVE:
                boolean bool=false;
                santoriniMainFrame.getBoardPanel().setDefaultBorder();
                id = message.getCurrentActiveWorker();
                if(message.getCoordinate()!=null){
                    santoriniMainFrame.getBoardPanel().drawWorker(message.getCoordinate().getX(),message.getCoordinate().getY(),id);
                    santoriniMainFrame.getBoardPanel().removeWorker(message.getCoordinateOld().getX(), message.getCoordinateOld().getY());
                    santoriniMainFrame.getBoardPanel().repaint();
                    bool=true;
                }
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                    setClientAction(Action.MOVE_AND_COORDINATE_BUILD);
                    santoriniMainFrame.getLog().append("\nSelect coordinate to build");
                    santoriniMainFrame.getBoardPanel().drawPossibleBorder(message.getCurrentPossibleMoves());
                }
                else if(clientController.getIdPlayer() != loseRound){
                    int n = clientController.getCurrentRoundIdPlayer();
                    if (!bool) {
                        santoriniMainFrame.getLog().append("player"+n+" do not use his power");
                    }
                }
                break;
            case BUILD_ATLAS:
            case BUILD_EPHAESTUS:
                santoriniMainFrame.getBoardPanel().setDefaultBorder();
                id = message.getCurrentActiveWorker();
                santoriniMainFrame.getBoardPanel().drawWorker(message.getCoordinate().getX(),message.getCoordinate().getY(),id);
                santoriniMainFrame.getBoardPanel().removeWorker(message.getCoordinateOld().getX(), message.getCoordinateOld().getY());
                santoriniMainFrame.getBoardPanel().repaint();
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                    santoriniMainFrame.getBoardPanel().drawPossibleBorder(message.getCurrentPossibleMoves());
                    setClientAction(a);
                }
                break;
            case END_TURN:
                if(clientController.getIdPlayer()==message.getnCurrentPlayer()){
                    setClientAction(Action.NOT_YOUR_TURN);
                    santoriniMainFrame.getLog().append("wait your turn");
                }
                else if(clientController.getIdPlayer()==message.getNextNPlayer() && clientController.getIdPlayer() != loseRound){
                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                    santoriniMainFrame.getLog().append("it's your turn!\nselect active worker:");
                }
                else if(clientController.getIdPlayer() != loseRound){
                    setClientAction(Action.NOT_YOUR_TURN);
                    santoriniMainFrame.getLog().append("wait your turn");
                }
                break;
            case FIRST_BUILD_DEMETER:
                santoriniMainFrame.getBoardPanel().setDefaultBorder();
                santoriniMainFrame.getBoardPanel().drawLevel(message.getCoordinate().getX(),message.getCoordinate().getY(),message.getLevel(),message.getDome());
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                    setClientAction(a);
                    Object[] options = {"YES",
                            "NO"};
                    int i = JOptionPane.showOptionDialog(santoriniMainFrame,
                            "Do you want build a second time",
                            "DEMETER POWER",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,     //do not use a custom Icon
                            options,  //the titles of buttons
                            options[0]); //default button title
                    if(i==0){
                        santoriniMainFrame.getBoardPanel().drawPossibleBorder(message.getCurrentPossibleMoves());
                    }
                    if(i==1){
                        asyncWriteToSocket(new Message(getClientAction().getValue(), "NO"));
                    }
                }
                break;
            case PROMETHEUS_CHOOSE:
                clientController.setCurrentRoundIdPlayer(message.getnCurrentPlayer());
                id = message.getCurrentActiveWorker();
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                    setClientAction(a);
                    Object[] options = {"MOVE",
                            "BUILD"};
                    int i = JOptionPane.showOptionDialog(santoriniMainFrame,
                            "Do you want move or build",
                            "PROMETHEUS POWER",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,     //do not use a custom Icon
                            options,  //the titles of buttons
                            options[0]); //default button title
                    if(i==0) {
                        ArrayList<Coordinates> possibleMovesPrometheus = message.getCurrentPossibleMoves();
                        santoriniMainFrame.getBoardPanel().drawPossibleBorder(possibleMovesPrometheus);
                        choosePrometheus=0;
                    }
                    if(i==1) {
                        ArrayList<Coordinates> possibleBuildsPrometheus = message.getCurrentPossibleC2();
                        santoriniMainFrame.getBoardPanel().drawPossibleBorder(possibleBuildsPrometheus);
                        choosePrometheus=1;
                    }
                }else if(clientController.getIdPlayer() != loseRound) {
                    int n = clientController.getCurrentRoundIdPlayer();
                    santoriniMainFrame.getLog().append("\nplayer" +n+ "select worker" +id);
                }
                break;
            case FIRST_BUILD_PROMETHEUS:
                santoriniMainFrame.getBoardPanel().setDefaultBorder();
                santoriniMainFrame.getBoardPanel().drawLevel(message.getCoordinate().getX(),message.getCoordinate().getY(),message.getLevel(),message.getDome());
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()) {
                    setClientAction(Action.SELECT_COORDINATE_MOVE);
                    santoriniMainFrame.getLog().append("Select coordinate to move");
                    ArrayList<Coordinates> possibleMoves = message.getCurrentPossibleMoves();
                    santoriniMainFrame.getBoardPanel().drawPossibleBorder(possibleMoves);
                }
                break;
            case LOSE3P:
                Worker wk1=message.getOppWorker();
                santoriniMainFrame.getBoardPanel().removeWorker(wk1.getCoordinates().getX(),wk1.getCoordinates().getY());
                Worker wk2=message.getOtherWorker();
                santoriniMainFrame.getBoardPanel().removeWorker(wk2.getCoordinates().getX(),wk2.getCoordinates().getY());
                loseRound=message.getnCurrentPlayer();
                if(clientController.getIdPlayer()==message.getnCurrentPlayer()){
                    setClientAction(Action.LOSE);
                    santoriniMainFrame.getLog().append("\nyou lose, wait other player finish the game");
                }
                else if(clientController.getIdPlayer()==message.getNextNPlayer()){
                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                    santoriniMainFrame.getLog().append("\nplayer"+message.getnCurrentPlayer()+" lose\nit's your turn!\nselect active worker:");
                }
                else{
                    santoriniMainFrame.getLog().append("\nplayer"+message.getnCurrentPlayer()+" lose");
                }
                break;
            case GAME_OVER:
                setClientAction(a);
                santoriniMainFrame.dispose();
                if(clientController.getIdPlayer()==message.getnCurrentPlayer()) {
                    if (message.getnCurrentPlayer() == 1) {
              //          asyncWriteToSocket(new Message(4, 0));
                        JOptionPane.showMessageDialog(null, "you win " + clientController.getIdPlayer(), "WINNER!", JOptionPane.ERROR_MESSAGE);
                    } else if (message.getnCurrentPlayer() == 0) {
              //          asyncWriteToSocket(new Message(4, 1));
                        JOptionPane.showMessageDialog(null, "you lose " + clientController.getIdPlayer(), "LOOSER!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(clientController.getIdPlayer() != loseRound){
                    if (message.getnCurrentPlayer() == 0) {
               //         asyncWriteToSocket(new Message(4, 0));
                        JOptionPane.showMessageDialog(null, "you win " + clientController.getIdPlayer(), "WINNER!", JOptionPane.ERROR_MESSAGE);
                    } else if (message.getnCurrentPlayer() == 1) {
               //         asyncWriteToSocket(new Message(4, 1));
                        JOptionPane.showMessageDialog(null, "you lose " + clientController.getIdPlayer(), "LOOSER!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.exit(-1);
                break;
        }
    }

    public int getChoosePrometheus(){
        return choosePrometheus;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream SocketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());


        try{
            SantoriniMainFrame santoriniMainFrame = new SantoriniMainFrame(this);
            StartingFrame startingFrame = new StartingFrame(santoriniMainFrame);
            this.setSantoriniMainFrame(santoriniMainFrame);
            this.setStartingFrame(startingFrame);
            Thread t0 = asyncReadFromSocket(SocketIn);
            t0.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            SocketIn.close();
            socket.close();
        }
    }


}
