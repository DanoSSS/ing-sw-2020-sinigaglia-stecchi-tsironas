package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.model.ObservableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SantoriniMainFrame extends JFrame {
    private String nickname;
    private JPanel panel1;
    private BoardPanel boardGUI;
    private ClientController controller;

    public SantoriniMainFrame() {
        super("Santorini - PLAYSTATION 5");
        /*
        JFrame finestraSantorini = new JFrame();
        finestraSantorini.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField jTextField = new JTextField("prova");
        finestraSantorini.add(jTextField);
        this.getContentPane().add(jTextField);
        finestraSantorini.pack();
        finestraSantorini.setVisible(false);*/
    }

    public void showBoard() {
        boardGUI = new BoardPanel(5, 5); //TODO: create constants 5 e 5
        boardGUI.createBoardTile();
        JTextField jTextField = new JTextField("prova");
        getContentPane().add(jTextField);
        getContentPane().add(boardGUI);
        pack();
    }

    private void setController(ClientController controller) {
        this.controller = controller;
    }

    public String askNickname(String message) {
        final String[] nickname = {new String()};
        JLabel enterYourName = new JLabel("Enter Your Name Here:");
        JTextField textBoxToEnterName = new JTextField(21);
        JPanel panelTop = new JPanel();
        panelTop.add(enterYourName);
        panelTop.add(textBoxToEnterName);
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Component frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "You've Submitted the name " + textBoxToEnterName.getText() + " which is allowed.");
                nickname[0] = textBoxToEnterName.getText();
            }
        });
        add(panelTop);
        repaint();
        return nickname[0];
    }

    public boolean isEmpty() {
        if (nickname!=null){
            return true;
        } else {
            return false;
        }
    }

    public int askNumberOfPlayers() {
        String[] choices= {"2","3"};
        int nPlayer= Integer.parseInt((String) JOptionPane.showInputDialog(this,
                                            "Choose the number of players",
                                            "N.PLAYER",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,choices,choices[0]));
        return nPlayer;
    }
}
