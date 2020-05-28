package it.polimi.ingsw.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartingFrame extends JFrame {
     /*   public static void main(String[] args) throws IOException {
                StartingFrame startingFrame = new StartingFrame();
        }*/

        public StartingFrame() throws IOException {
                JPanel InitPanel = new JPanel();
                JLabel ImageLabel = new JLabel();
                ImageLabel.setSize(new Dimension(600,600));
                JButton StartGame = new JButton("Start game");
                ImageIcon SantoriniImage = new ImageIcon("src/main/resources/santorini.png");
                ImageLabel.setIcon(SantoriniImage);

        /*        BufferedImage img = null;
                img = ImageIO.read(new File("resources/santorini.png"));
                Image dimg = img.getScaledInstance(ImageLabel.getWidth(), ImageLabel.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                ImageLabel.setIcon(imageIcon); */
                InitPanel.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent mouseEvent) {
                                StartingFrame startingFrame = returnFrame();
                                startingFrame.dispose();
                        }

                        @Override
                        public void mousePressed(MouseEvent mouseEvent) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent mouseEvent) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent mouseEvent) {

                        }

                        @Override
                        public void mouseExited(MouseEvent mouseEvent) {

                        }
                });
                InitPanel.add(ImageLabel);
                this.add(InitPanel);

                this.pack();
                this.setMinimumSize(new Dimension(600,600));
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                this.setVisible(true);

        }

        public StartingFrame returnFrame (){
                return this;
        }
}
