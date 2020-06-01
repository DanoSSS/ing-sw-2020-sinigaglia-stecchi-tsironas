package it.polimi.ingsw.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BoardPanel extends JPanel {
    private TilePanel[][] tile;
    protected final int nRow;
    protected final int nColumn;
    private ImageIcon workerPlayer1;
    private ImageIcon workerPlayer2;
    private ImageIcon workerPlayer3;
    private ArrayList<TilePanel> workerPositions;
    private ClientGUI clientGUI;

    public BoardPanel(int nRow,int nColumn,ClientGUI clientGUI) {
        super(new GridLayout(nRow,nColumn));
        this.clientGUI=clientGUI;
        this.nRow=nRow;
        this.nColumn=nColumn;
        createBoardTile();
    }
    public void createBoardTile(){  //init empty Tile[][]
        tile= new TilePanel[5][5];
        for (int i=0; i<nRow;i++){
            for (int j=0;j<nColumn;j++){
                tile[i][j]=new TilePanel(i,j,this);
                this.add(tile[i][j]);
            }
        }
    }

    public void drawImage(int x,int y){  //TODO: in bade all id worker che riceve come parametro passera un worker diverso
        tile[x][y].removeAll();
        workerPlayer1 = new ImageIcon("src/main/resources/wkblue.png");
        tile[x][y].add(workerPlayer1);
    }
}
