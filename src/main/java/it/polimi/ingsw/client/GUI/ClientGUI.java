package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientController;
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
import java.util.NoSuchElementException;


public class ClientGUI  {
    private String ip;
    private int port;
    private Action clientAction;
    private ClientController clientController;
    private boolean active = true;
    private SantoriniMainFrame santoriniMainFrame;
    private StartingFrame startingFrame;
    private ObjectOutputStream socketOut;

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

    public void messageHandler (ReturnMessage message){
        Action a = message.getAction();
        switch (a){
            case FIRST_MESSAGE:

                String inputValue = JOptionPane.showInputDialog("Choose your nickname");
                asyncWriteToSocket(new Message(a.getValue(), inputValue));

        }
    }


    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream SocketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());


        try{
            SantoriniMainFrame santoriniMainFrame = new SantoriniMainFrame();
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
