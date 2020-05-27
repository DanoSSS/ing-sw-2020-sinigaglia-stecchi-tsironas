package it.polimi.ingsw;

import it.polimi.ingsw.client.GUI.ClientGUI;

import java.io.IOException;

public class StartClientGUI {
    public static void main(String[] args){
        ClientGUI client = new ClientGUI("127.0.0.1", 12345);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
