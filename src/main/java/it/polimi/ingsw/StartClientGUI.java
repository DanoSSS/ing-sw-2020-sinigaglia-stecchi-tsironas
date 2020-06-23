package it.polimi.ingsw;

import it.polimi.ingsw.client.GUI.ClientGUI;
import java.io.IOException;
public class  StartClientGUI {
    public static void main(String[] args){
        ClientGUI client = new ClientGUI(args[0], Integer.parseInt(args[1]));
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
