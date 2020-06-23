package it.polimi.ingsw;

import it.polimi.ingsw.client.CLI.ClientCLI;

import java.io.IOException;

public class StartClientCLI {
    public static void main(String[] args){
        ClientCLI clientCLI = new ClientCLI(args[0], Integer.parseInt(args[1]));
        try{
            clientCLI.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
