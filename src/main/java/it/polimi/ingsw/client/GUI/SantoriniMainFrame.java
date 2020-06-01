package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;

public class SantoriniMainFrame extends JFrame {
    private BoardPanel boardPanel;
    private ClientGUI clientGUI;

    public SantoriniMainFrame (ClientGUI clientGUI){
        super();
        this.clientGUI=clientGUI;
        this.setSize(700,700);
        boardPanel = new BoardPanel(5,5,clientGUI);
        this.getContentPane().add(BorderLayout.CENTER,boardPanel);
        this.pack();
        this.setMinimumSize(new Dimension(700,700));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

}
