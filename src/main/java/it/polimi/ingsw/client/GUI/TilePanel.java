package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.CLI.CellMessage;

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
    private JLabel workerLabel,levelLabel;
    private int idWorker=-1;
    private Dimension d;
    private boolean dome=false;

    public TilePanel(int x, int y, BoardPanel boardGUI) {
        super();
        this.x = x;
        this.y = y;
        this.boardGUI = boardGUI;
        addMouseListener((MouseListener) new MoveListener(x, y, boardGUI));
        d = new Dimension(115,115);
        this.setPreferredSize(d);
        this.setBackground(new Color(3,192,60));
        this.setBorder(BorderFactory.createLineBorder(Color.black,3));
        levelLabel = new JLabel(Integer.toString(level));
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

    public void build(int level, boolean dome) {
        this.level=level;
        this.dome=dome;
        if (dome) {
            this.setBackground(new Color(0,47,167));
        } else {
            switch (level) {
                case 1:
                    this.setBackground(new Color(239,239,239));
                    break;
                case 2:
                    this.setBackground(new Color(178,178,178));
                    break;
                case 3:
                    this.setBackground(new Color(95,95,95));
                    break;
                case 4:
                    this.setBackground(new Color(0,47,167));
                    break;
            }
            this.remove(levelLabel);
            if(level!=4) {
                levelLabel = new JLabel(Integer.toString(level));
                this.add(BorderLayout.NORTH, levelLabel);
            }
        }
    }

    public boolean getDome() {
        return dome;
    }
}
