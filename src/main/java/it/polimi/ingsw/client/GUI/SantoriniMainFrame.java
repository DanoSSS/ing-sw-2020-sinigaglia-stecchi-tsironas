package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;

public class SantoriniMainFrame extends JFrame {
    private BoardPanel boardPanel;
    private ClientGUI clientGUI;

    public SantoriniMainFrame (ClientGUI clientGUI){
        super();
        this.clientGUI=clientGUI;
        this.setSize(600,600);
        boardPanel = new BoardPanel(5,5,clientGUI);
        this.getContentPane().add(BorderLayout.CENTER,boardPanel);
        this.pack();
        this.setMinimumSize(new Dimension(600,600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

}
