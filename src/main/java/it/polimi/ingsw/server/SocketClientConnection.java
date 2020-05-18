package it.polimi.ingsw.server;

import it.polimi.ingsw.model.God;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<Object> implements ClientConnection,Runnable {
    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean active = true;
    private int playerNumber;

    public SocketClientConnection(Socket socket, Server server, int playerNumber) throws IOException {
        this.socket = socket;
        this.server = server;
        this.playerNumber = playerNumber;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    private synchronized boolean isActive() {
        return active;
    }

    private synchronized String read() throws IOException, ClassNotFoundException {
        String s = null;
        Message recv = (Message)in.readObject();
        s = recv.getSentence();
        return s;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public synchronized void closeConnection() {
        send(new ReturnMessage(4,"Connection closed!"));
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
        String name;
        int NPlayers = 0;

        try {
            send(new ReturnMessage(4,"Welcome to SANTORINI!A beatiful city but also a beautiful game!\nWhat is your nickname?"));                   //for all players
            String read = read();
            while (!server.nicknameCheck(read)) {
                send(new ReturnMessage(4,"Your selected nickname is already in use.\nSelect an other nickname: "));                   //for all players
                read = read();
            }
            name = read.valueOf(read.toCharArray(), 0, read.length());  //copia il nickname in name
            if (playerNumber == 1) {                                   //playerNOne
                send(new ReturnMessage(4,"how many players?\n2 or 3?"));     //setting numberOfPlayers
                read = read();
                NPlayers = Integer.parseInt(read);
                server.setNPlayers(NPlayers);

                send(new ReturnMessage(4,"select " + server.getNPlayers() + " gods between:\nAPOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, EPHAESTUS, MINOTAUR, PAN, PROMETHEUS"));   //Setting god cards
                read = read();
                while (!server.setGods1(read)) {
                    server.clearGods();
                    send(new ReturnMessage(4,"gods Cards doesn't exist or they has been misspelled:\nselect " + server.getNPlayers() + " gods between:\nAPOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, EPHAESTUS, MINOTAUR, PAN, PROMETHEUS"));
                    read = read();
                }

                server.removeFromWaitP2();      //Remove P2 from wait
                send(new ReturnMessage(4,"\nwait your turn...."));
                server.putInWaitChallenger();  //Wait for P2,P3
                read=server.getGods(0);
                send(new ReturnMessage(4,"your god is: " + read));
                //read = server.getGods(0);
                send(new ReturnMessage(4, "\nGameBoard settled! Now P2 Choose his workers :\n"));
                server.removeFromWaitStart(); //LIBERA P2: comincia a chiedere al giocatre 2 le coordinate

            }
            else if (playerNumber == 2) {                                                             //PlayerNTwo
                send(new ReturnMessage(4,"wait the P1... is taking the cards"));
                server.putInWaitP2();       //Wait for the P1 to choose the cards
                NPlayers = server.getNPlayers();

                if (NPlayers == 3) {       //3
                    send(new ReturnMessage(4,"select your god between: " + server.getGods(0) + "\t" + server.getGods(1) + "\t" + server.getGods(2)));
                    read = godSelection(NPlayers);

                    server.removeFromWaitP3();
                }
                else if (NPlayers == 2) {    //2
                    send(new ReturnMessage(4,"select your god between: " + server.getGods(0) + " " + server.getGods(1)));
                    read = godSelection(NPlayers);

                    server.removeFromWait();
                }
            }
            else if (playerNumber == 3) {                               //PlayerNThree
                send(new ReturnMessage(4,"wait the P1... is taking the cards"));
                server.putInWaitP3(); //Wait P1 to choose the cards
                NPlayers = server.getNPlayers();
                send(new ReturnMessage(4,"select your god between: " + server.getGods(0) + " " + server.getGods(1)));
                read = godSelection(NPlayers);

                server.removeFromWait();
            }//END CARDS

            server.lobby(this, name, God.valueOf(read),playerNumber);

            if (playerNumber == 1) {
                send(new ReturnMessage(4,"wait other player set their workers"));
                server.putInWaitP1();
                askForCoordinates();
                send(new ReturnMessage(4,"OK!"));
                notify(new Message(0, server.getWorkerPositions()));
            } else if (playerNumber == 2) {
                if (NPlayers == 3) {
                    send(new ReturnMessage(4,"waiting P3 and P1 to choose his card...\n"));
                    server.putInWaitStart();
                    askForCoordinates();
                    send(new ReturnMessage(4,"OK! now P3 and P1"));
                    server.removeFromWaitP3();
                } else if (NPlayers == 2) {
                    send(new ReturnMessage(4,"waiting P1 to choose his card...\n"));
                    server.putInWaitStart();
                    askForCoordinates();
                    send(new ReturnMessage(4,"OK! now P1 :"));
                    server.removeFromWaitP1();
                }
            } else if (playerNumber == 3) {
                send(new ReturnMessage(4,"waiting P1 to choose his card...\nwait " +
                    "P2 set his workers"));
                server.putInWaitP3();
                askForCoordinates();
                send(new ReturnMessage(4,"OK! now P1 :\n"));
                server.removeFromWaitP1();
            }
            while (isActive()) {
                Message recv = (Message)in.readObject();
                notify(recv);
            }
        } catch (IOException | NoSuchElementException | InterruptedException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private String godSelection(int n) throws IOException, ClassNotFoundException {
        String read = read();
        while (server.removeGods1(read)) {
            if (n==3) {
                send(new ReturnMessage(4, "you have entered a god card that doesn't exist, select your god between: " + server.getGods(0) + "\t" + server.getGods(1) + "\t" + server.getGods(2)));
            } else {
                send(new ReturnMessage(4,"you have entered a god card that doesn't exist, select your god between: " + server.getGods(0) + "\t" + server.getGods(1)));
            }
            read = read();
        }
        return read;
    }

    private synchronized void askForCoordinates() throws IOException, ClassNotFoundException {
        try {
            send(new ReturnMessage(4,"set your first worker in coordinate: (x,y)"));
            String coordinate = read();
            String[] input = coordinate.split(",");
            while (!server.addWorkersPositions(Integer.parseInt(input[0]), Integer.parseInt(input[1]))) {
                send(new ReturnMessage(4,"this cell is already occupied or is not in the board, set worker in new coordinate: (x,y) 0<=x<5 0<=y<5"));
                coordinate = read();
                input = coordinate.split(",");
            }

            send(new ReturnMessage(4,"set your second worker in coordinate: (x,y)"));
            coordinate = read();
            input = coordinate.split(",");
            while (!server.addWorkersPositions(Integer.parseInt(input[0]), Integer.parseInt(input[1]))) {
                send(new ReturnMessage(4,"this cell is already occupied or is not in the board, set worker in new coordinate: (x,y) 0<x<5 0<y<5"));
                coordinate = read();
                input = coordinate.split(",");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }
    }
}
