package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class TilePanel extends JPanel {
    private BoardPanel boardGUI; //riferimento al contenitore di celle
    private int x;
    private int y;
    private int level=0;

    public TilePanel(int x, int y, BoardPanel boardGUI) {
        super();
        this.x = x;
        this.y = y;
        this.boardGUI = boardGUI;
        addMouseListener((MouseListener) new MoveListener(x, y, boardGUI));
        this.setBackground(new Color(3,192,60));
        this.setBorder(BorderFactory.createLineBorder(Color.white,3));
        JLabel levelLabel = new JLabel(Integer.toString(level));
        this.add(BorderLayout.CENTER,levelLabel);
    }
}
