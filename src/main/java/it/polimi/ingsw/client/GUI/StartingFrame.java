package it.polimi.ingsw.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class StartingFrame extends JFrame {
        private boolean startBool = false;
        private SantoriniMainFrame santoriniMainFrame;
        private ImageIcon gameImage;
        private JPanel glass;

        public StartingFrame(SantoriniMainFrame santoriniMainFrame) throws IOException {
                this.santoriniMainFrame = santoriniMainFrame;
                URL image = StartingFrame.class.getResource("/santorini.png");
                System.out.println(image);
                //JPanel InitPanel = new JPanel();

                gameImage = new ImageIcon(image);
                JLabel ImageLabel = new JLabel();
                Image image1 = gameImage.getImage().getScaledInstance(485, 485,  java.awt.Image.SCALE_SMOOTH);
                gameImage = new ImageIcon(image1);
                ImageLabel.setIcon(gameImage);

                ImageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                glass = (JPanel) getGlassPane();
                JButton glassButton = new JButton("Start");
                glassButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        glass.setVisible(false);
                        setVisible(false);
                        santoriniMainFrame.showBoard();
                        santoriniMainFrame.setVisible(true);
                        startBool = true;
                        dispose();
                    }
                });
                glass.setLayout(new GridBagLayout());
                glass.add(glassButton);

                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                getContentPane().add(ImageLabel);

        }

        /*public StartingFrame returnFrame (){
                return this;
        }*/
    public void showFirstFrame(){
        setPreferredSize(new Dimension(gameImage.getIconWidth(),gameImage.getIconHeight()));
        pack();
        setVisible(true);
        glass.setVisible(true);
    }
        public boolean isStartBool() {
                return startBool;
        }

}
