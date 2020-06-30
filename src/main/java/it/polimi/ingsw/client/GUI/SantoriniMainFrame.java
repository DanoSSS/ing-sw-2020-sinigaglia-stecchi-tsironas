package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SantoriniMainFrame extends JFrame {
    private BoardPanel boardPanel;
    private JTextArea log;
    private JScrollPane scrollPane;
    private JPanel namesPanel= new JPanel();
    private Dimension d;

    /**
     * Constructor that creates the main frame
     * @param clientGUI
     */
    public SantoriniMainFrame (ClientGUI clientGUI){
        super();
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
        this.getContentPane().add(BorderLayout.NORTH,namesPanel);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.out.println("closing Santorini...");
                try {
                    clientGUI.getSocket().close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("Closed");
                dispose();
            }
        });
        this.setVisible(false);
    }

    /**
     * This method receives an Arraylist with the players of the game and print their nicknames and gods on the main frame
     * @param playersList
     */
    public void addNamesAndGods(ArrayList<Player> playersList){
        for(Player p: playersList){
            JLabel playerLabel = new JLabel(p.getNickname()+":"+p.getGod().toString());
            if(p.getIdPlayer()==1){
                playerLabel.setForeground(new Color(14,104,7));
            }
            if(p.getIdPlayer()==2){
                playerLabel.setForeground(Color.RED);
            }
            if(p.getIdPlayer()==3){
                playerLabel.setForeground(Color.BLUE);
            }
            this.namesPanel.add(playerLabel);
        }
    }

    /**
     *
     * @return boardPanel
     */
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     *
     * @return log
     */
    public JTextArea getLog() {
        return log;
    }
}
