package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;

public class SantoriniMainFrame extends JFrame {
    private BoardPanel boardPanel;
    private ClientGUI clientGUI;
    private JTextArea log;
    private JScrollPane scrollPane;
    private Dimension d;

    public SantoriniMainFrame (ClientGUI clientGUI){
        super();
        this.clientGUI=clientGUI;
        this.setSize(650,500);
        boardPanel = new BoardPanel(5,5,clientGUI);
        this.getContentPane().add(BorderLayout.CENTER,boardPanel);
        log = new JTextArea();
        scrollPane = new JScrollPane(log);
        d = new Dimension(150,500);
        scrollPane.setPreferredSize(d);
        scrollPane.createVerticalScrollBar();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setWheelScrollingEnabled(true);
        this.getContentPane().add(BorderLayout.EAST,scrollPane);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(650,500));
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
