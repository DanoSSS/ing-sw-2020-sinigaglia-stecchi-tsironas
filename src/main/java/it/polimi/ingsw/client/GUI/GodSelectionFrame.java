package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.utils.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class GodSelectionFrame extends JFrame {
    ClientGUI client;
    private ArrayList<String> selectedGods = new ArrayList<>();
    private int count=0;


    public GodSelectionFrame(int nButtons, String sentence, ArrayList<String> gods,int np,ClientGUI client){
        this.client=client;
        JTextField jTextField = new JTextField(sentence);
        this.add(BorderLayout.NORTH,jTextField);
        JPanel jPanel = new JPanel();
        for(int i = 0; i<nButtons; i++){
            JButton jButton = new JButton(gods.get(i));
            final int j=i;
            jButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(!selectedGods.contains(gods.get(j))){
                        selectedGods.add(gods.get(j));
                        jButton.setBackground(Color.GREEN);
                        count++;
                        if(count==2 && nButtons==14 && np==2){
                            client.asyncWriteToSocket(new Message(22,getSelectedGods().get(0)+","+getSelectedGods().get(1)));
                            closeFrame();
                        }
                        if(count==3 && nButtons==14 && np==3){
                            client.asyncWriteToSocket(new Message(22,getSelectedGods().get(0)+","+getSelectedGods().get(1)+","+getSelectedGods().get(2)));
                            closeFrame();
                        }
                        if(count==1 && (nButtons==3||nButtons==2)){
                            client.asyncWriteToSocket(new Message(24,getSelectedGods().get(0)));
                            closeFrame();
                        }
                    }
                }
            });
            jPanel.add(jButton);
        }
        this.add(BorderLayout.CENTER,jPanel);
        this.validate();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    public ArrayList<String> getSelectedGods() {
        return selectedGods;
    }

    public void closeFrame(){
        this.dispose();
    }

}