package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class StartingFrame extends JFrame {
        private SantoriniMainFrame santoriniMainFrame;


        public StartingFrame(SantoriniMainFrame santoriniMainFrame, ClientGUI clientGUI) throws IOException {
                this.santoriniMainFrame = santoriniMainFrame;

                JPanel InitPanel = new JPanel();
                JLabel ImageLabel = new JLabel();
                ImageLabel.setSize(new Dimension(500,490));
                JButton StartGame = new JButton("Start game");
                ImageIcon SantoriniImage = new ImageIcon(getClass().getResource("/santorini.png"));

                int w = SantoriniImage.getIconWidth();
                int h = SantoriniImage.getIconHeight();
                int targetWidth = ImageLabel.getWidth();
                int targetHeight = ImageLabel.getHeight();
                do {
                if (w > targetWidth) {
                    w =w-60;
                    if (w < targetWidth) {
                        w = targetWidth;
                    }
                }
                if (h > targetHeight) {
                    h =h-60;
                    if (h < targetHeight) {
                        h = targetHeight;
                    }
                }
                Image img = SantoriniImage.getImage().getScaledInstance(w,h,Image.SCALE_SMOOTH);
                SantoriniImage = new ImageIcon(img);
            } while (w != targetWidth || h != targetHeight);
                ImageLabel.setIcon(SantoriniImage);

                InitPanel.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent mouseEvent) {
                                StartingFrame startingFrame = returnFrame();
                                startingFrame.dispose();
                                santoriniMainFrame.setVisible(true);
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
                this.getContentPane().add(InitPanel);

                this.pack();
                this.setLocationRelativeTo(null);
                this.setMinimumSize(new Dimension(390,380));
                //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    System.out.println("closing SetupSantorini...");
                    try {
                        clientGUI.getSocket().close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    dispose();
                    System.out.println("closed");
                }
            });
                this.setVisible(true);

        }

        public StartingFrame returnFrame (){
                return this;
        }

}
