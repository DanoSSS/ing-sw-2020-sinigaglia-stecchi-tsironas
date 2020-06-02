package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;

public class SantoriniMainFrame extends JFrame {
    private BoardPanel boardPanel;
    private ClientGUI clientGUI;
    private JTextArea log;
    private JScrollPane scrollPane;
    private Dimension d1,d2;

    public SantoriniMainFrame (ClientGUI clientGUI){
        super();
        this.clientGUI=clientGUI;
        d1 = new Dimension(350,200);
        this.setPreferredSize(d1);
        boardPanel = new BoardPanel(5,5,clientGUI);
        this.getContentPane().add(BorderLayout.CENTER,boardPanel);
        log = new JTextArea();
        scrollPane = new JScrollPane(log);
        d2 = new Dimension(150,200);
        scrollPane.setPreferredSize(d2);
        scrollPane.createVerticalScrollBar();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setWheelScrollingEnabled(true);
        this.getContentPane().add(BorderLayout.EAST,scrollPane);
        this.pack();
        this.setMinimumSize(new Dimension(850,850));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public JTextArea getLog() {
        return log;
    }
}
