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
    private int idWorker=-1;
    private Dimension d;

    public TilePanel(int x, int y, BoardPanel boardGUI) {
        super();
        this.x = x;
        this.y = y;
        this.boardGUI = boardGUI;
        addMouseListener((MouseListener) new MoveListener(x, y, boardGUI));
        d = new Dimension(115,115);
        this.setPreferredSize(d);
        this.setBackground(new Color(210,210,210));
        this.setBorder(BorderFactory.createLineBorder(Color.cyan,3));
        JLabel levelLabel = new JLabel(Integer.toString(level));
        this.add(BorderLayout.NORTH,levelLabel);
    }

    public void addWorker(int idWorker) throws IOException {   //da vedere e sistemare insieme
        workerLabel= new JLabel();
        workerLabel.setSize(this.getSize());
        this.add(BorderLayout.SOUTH,workerLabel);
        BufferedImage img = null;
        ImageIcon image1=null;
        if(idWorker==1 || idWorker==0) {
             image1 = new ImageIcon(getClass().getResource("/wkred.png"));
        }
        else if(idWorker==2 || idWorker==3){
            image1 = new ImageIcon(getClass().getResource("/wkgreen.png"));
        }
        else if(idWorker==4 || idWorker==5){
            image1 = new ImageIcon(getClass().getResource("/wkblue.png"));
        }
        Image dimg = image1.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(dimg);
        workerLabel.setIcon(image1);
        this.idWorker=idWorker;
        workerLabel.setVisible(true);
    }

    public void removeWorker(){      //da vedere e sistemare insieme
        this.remove(workerLabel);
    }

    public int getIdWorker() {
        return idWorker;
    }

}
