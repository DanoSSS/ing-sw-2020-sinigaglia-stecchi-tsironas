package it.polimi.ingsw.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TilePanel extends JPanel {
    private BoardPanel boardGUI; //riferimento al contenitore di celle
    private int x;
    private int y;
    private int level=0;
    private JLabel workerLabel;

    public TilePanel(int x, int y, BoardPanel boardGUI) {
        super();
        this.x = x;
        this.y = y;
        this.boardGUI = boardGUI;
        addMouseListener((MouseListener) new MoveListener(x, y, boardGUI));
        this.setBackground(new Color(3,192,60));
        this.setBorder(BorderFactory.createLineBorder(Color.white,3));
        JLabel levelLabel = new JLabel(Integer.toString(level));
        this.add(BorderLayout.NORTH,levelLabel);
    }

    public void addWorker(int colorWorker) throws IOException {   //da vedere e sistemare insieme
        workerLabel= new JLabel();
        workerLabel.setSize(this.getSize());
        this.add(BorderLayout.CENTER,workerLabel);
        if(colorWorker==1){
            BufferedImage img = null;
            img = ImageIO.read(new File("arts/wkblue.png"));
            Image dimg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            workerLabel.setIcon(imageIcon);
        }
        workerLabel.setVisible(true);
    }

    public void removeWorker(){      //da vedere e sistemare insieme
        this.remove(workerLabel);
    }
}
