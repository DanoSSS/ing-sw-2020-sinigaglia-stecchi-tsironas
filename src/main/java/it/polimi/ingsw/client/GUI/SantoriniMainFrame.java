package it.polimi.ingsw.client.GUI;

import javax.swing.*;

public class SantoriniMainFrame extends JFrame {
    public SantoriniMainFrame (){
        JFrame finestraSantorini = new JFrame();
        finestraSantorini.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField jTextField = new JTextField("prova");
        finestraSantorini.add(jTextField);
        this.getContentPane().add(jTextField);
        finestraSantorini.pack();
        finestraSantorini.setVisible(false);
    }
}
