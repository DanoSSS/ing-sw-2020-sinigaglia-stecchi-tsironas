package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Action;

import javax.print.attribute.standard.NumberUp;
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

                    }
                } catch (Exception e){
                        setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket( final ObjectOutputStream socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {

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


        try{
            Thread t0 = asyncReadFromSocket(SocketIn);
            Thread t1 = asyncWriteToSocket(SocketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {

            SocketIn.close();
            SocketOut.close();
            socket.close();
        }
    }


}
