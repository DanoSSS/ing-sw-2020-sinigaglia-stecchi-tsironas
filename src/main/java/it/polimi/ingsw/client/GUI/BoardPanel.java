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

    public void drawFirstWorker(int x, int y, int idWorker) throws IOException {
        tile[x][y].addWorker(idWorker);
        //repaint in clientGUI
    }

    public void drawWorker(int x,int y,int idWorker) throws IOException {  //da vedere e sistemare insieme
        Coordinates oldC;
        int occupiedByIdWorker = tile[x][y].getIdWorker();

        if(occupiedByIdWorker>0 && occupiedByIdWorker==idWorker){return;}  //cannot move on the same position, return

        if(occupiedByIdWorker>0){ //if cell has worker, swap TODO:controllare prima di aver finito di implementare tutti i round prima di controllare errori
            oldC= locateIdWorker(idWorker);
            tile[x][y].removeWorker();
            tile[x][y].addWorker(idWorker);
            tile[oldC.getX()][oldC.getY()].addWorker(occupiedByIdWorker);
        } else {
            tile[x][y].addWorker(idWorker);
        }
        revalidate();
    }

    public void removeWorker(int x, int y){    //da vedere e sistemare insieme
        tile[x][y].removeWorker();
        clientGUI.getSantoriniMainFrame().repaint();
    }

    public Coordinates locateIdWorker(int idWorker){
        Coordinates c = null;
        for (int i=0;i<nRow;i++){
            for (int j=0;j<nColumn;j++){
                if (tile[i][j].getIdWorker()>0 && tile[i][j].getIdWorker()==idWorker){
                    c = new Coordinates(i,j);
                }
            }
        }
        return c;
    }

    public void handleClick(int x, int y) {
        Action a = clientGUI.getClientAction();
        boolean flag = clientGUI.getClientController().checkPresenceWorker(tile[x][y].getIdWorker()); // for normal BUILD & MOVE
        switch (a){
            case SELECT_ACTIVE_WORKER:
                if(!clientGUI.getClientController().checkIdWorker(tile[x][y].getIdWorker())){ //return true if there is a worker; false if empty
                    clientGUI.getSantoriniMainFrame().getLog().append( "\n----\nThis is not your worker!\nPick yours!");
                }else {
                    clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), tile[x][y].getIdWorker()));
                }
                break;
            case NOT_YOUR_TURN:
                clientGUI.getSantoriniMainFrame().getLog().append("\n----\nWAIT! It's not your turn!");
                break;
            case SELECT_COORDINATE_MOVE:
            case MOVE_AND_COORDINATE_BUILD:
                Coordinates newC = new Coordinates(x,y);
                        //for normal build NO! you cannot create levels on opposite workers
                            clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(),newC));
                         //TODO: capire come gestire gli errori dopo aver implementato tuti i diversi round ed eliminare questa parte sopra (4 righe circa)
                        break;
        }
    }

    public void drawPossibleBorder(ArrayList<Coordinates> possibleMove){
        for(int i=0;i<nRow;i++){
            for(int j=0;j<nColumn;j++){
                Coordinates c1 = new Coordinates(i,j);
                for(Coordinates c:possibleMove){
                    if(c.equals(c1) && possibleMove.size()!=0){
                        tile[i][j].setBorder(BorderFactory.createLineBorder(Color.ORANGE,3));
                    }else{
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
    }


}
