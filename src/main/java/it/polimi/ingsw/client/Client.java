package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private Action a=Action.STRING;
    private ClientController clientController;

    public void setA(Action a) {
        this.a = a;
    }



    public Action getA() {
        return a;
    }



    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    private boolean active = true;

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
                        ReturnMessage inputObject =(ReturnMessage) socketIn.readObject();
                        Action a = inputObject.getAction();
                        System.out.println("received" + a.toString());
                        if (a==Action.STRING) {
                            System.out.println(inputObject.getSentence());
                        }else if(a==Action.CURRENTPLAYERNUMBER){}
                        else if (a==Action.SELECTACTIVEWORKER){}
                        else if (a==Action.INITWORKERS){}
                        else if(a==Action.WORKERSET){
                            clientController = inputObject.getClientController().clone();
                            for (int i=0;i<inputObject.getNicknames().length;i++){
                                System.out.println(inputObject.getNicknames()[i]); //get the String[] with the output
                            }
                        }else {

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
                        socketOut.writeObject(new Message(4,inputObject));
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
