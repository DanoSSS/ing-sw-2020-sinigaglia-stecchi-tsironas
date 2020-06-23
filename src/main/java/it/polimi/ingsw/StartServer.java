package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class StartServer {
    public static void main( String[] args ){
        Server server;
        try {
            server = new Server(Integer.parseInt(args[0]));
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
