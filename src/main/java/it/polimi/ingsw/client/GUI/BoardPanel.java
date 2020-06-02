package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Worker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;

import java.util.Map;


public class BoardPanel extends JPanel {
    private TilePanel[][] tile;
    protected final int nRow;
    protected final int nColumn;
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

    public void drawWorker(int x,int y,int idWorker) throws IOException {  //da vedere e sistemare insieme
        tile[x][y].addWorker(idWorker);
    }

    public void removeWorker(int x, int y){    //da vedere e sistemare insieme
        tile[x][y].removeWorker();
        repaint();
    }

    public void handleClick(int x, int y) {
        Action a = clientGUI.getClientAction();
        switch (a){
            case SELECT_ACTIVE_WORKER:
                if(!clientGUI.getClientController().checkIdWorker(tile[x][y].getIdWorker())){
                    clientGUI.getSantoriniMainFrame().getLog().append( "\nThis is not your worker!\nPick yours!");
                }else {
                    clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), tile[x][y].getIdWorker()));
                }
                break;
            case NOT_YOUR_TURN:
                clientGUI.getSantoriniMainFrame().getLog().append("\nWAIT! It's not your turn!");
                break;
            case SELECT_COORDINATE_MOVE:
            case MOVE_AND_COORDINATE_BUILD:
                Coordinates newC = new Coordinates(x,y);
                clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(),newC));
                break;
        }
    }

    public void drawPossibleBorder(ArrayList<Coordinates> possibleMove){
        for(int i=0;i<nRow;i++){
            for(int j=0;j<nColumn;j++){
                Coordinates c1 = new Coordinates(i,j);
                for(Coordinates c:possibleMove){
                    if(c.equals(c1) && possibleMove.size()!=0){
                        tile[i][j].setBorder(BorderFactory.createLineBorder(Color.orange,3));
                    }else if(!c.equals(c1)){
                        tile[i][j].setEnabled(false);
                    }
                }
            }
        }
    }

    public void setDefaultBorder(){
        for(int i=0;i<nRow;i++) {
            for (int j = 0; j < nColumn; j++) {
                tile[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 3));
                if(!tile[i][j].getDome()){
                    tile[i][j].setEnabled(true);
                }
            }
        }
    }

    public void drawLevel(int x,int y, int level, boolean dome){
        tile[x][y].build(level,dome);
        repaint();
    }

}
