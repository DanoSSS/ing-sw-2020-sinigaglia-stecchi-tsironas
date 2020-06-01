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
            case WORKER_SET:   //da fare, cosi è soltanto una prova per settare un worker in una tile
                clientController = message.getClientController().clone();
                List<Worker> keys= new ArrayList<Worker>(message.getWorkerPosition().keySet());
                santoriniMainFrame.getBoardPanel().drawWorker(message.getWorkerPosition().get(keys.get(0)).getX(),message.getWorkerPosition().get(keys.get(0)).getY(),1);
                if(clientController.getIdPlayer()==clientController.getCurrentRoundIdPlayer()){
                    setClientAction(Action.SELECT_ACTIVE_WORKER);
                }
                else if(clientController.getIdPlayer() != clientController.getCurrentRoundIdPlayer()) {
                    setClientAction(Action.NOT_YOUR_TURN);
                }
        }
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
