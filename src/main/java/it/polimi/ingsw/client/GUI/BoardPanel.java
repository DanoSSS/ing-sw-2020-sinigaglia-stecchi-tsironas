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
import it.polimi.ingsw.utils.ReturnMessage;

import java.util.Map;


public class BoardPanel extends JPanel {
    private TilePanel[][] tile;
    protected final int nRow;
    protected final int nColumn;
    private ClientGUI clientGUI;
    private ArrayList<Coordinates> possibleTile = null;
    private int activeWorker;

    /**
     * constructor
     * @param nRow
     * @param nColumn
     * @param clientGUI
     */
    public BoardPanel(int nRow, int nColumn, ClientGUI clientGUI) {
        super(new GridLayout(nRow, nColumn));
        this.clientGUI = clientGUI;
        this.nRow = nRow;
        this.nColumn = nColumn;
        createBoardTile();
    }

    /**
     * This method creates 25 tiles and adds them to the board panel
     */
    public void createBoardTile() {  //init empty Tile[][]
        tile = new TilePanel[5][5];
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nColumn; j++) {
                tile[i][j] = new TilePanel(i, j, this);
                this.add(tile[i][j]);
            }
        }
    }

    /**
     * This method draws the worker it receives in the tile x,y
     * @param x
     * @param y
     * @param idWorker
     * @throws IOException
     */
    public void drawWorker(int x, int y, int idWorker) throws IOException {
        tile[x][y].addWorker(idWorker);
    }

    /**
     * This method removes the worker in the tile x,y
     * @param x
     * @param y
     */
    public void removeWorker(int x, int y) {
        tile[x][y].removeWorker();
    }

    /**
     * Handles the clicks on every tile of the board, it does differnt things in function of the Action setted in the Client Controller
     * @param x
     * @param y
     * @throws IOException
     * @throws InterruptedException
     */
    public void handleClick(int x, int y) throws IOException, InterruptedException {
        Action a = clientGUI.getClientAction();
        boolean flag = false; //clientGUI.getClientController().checkPresenceWorker(tile[x][y].getIdWorker()); // for normal BUILD & MOVE
        try {
            switch (a) {
                case SELECT_ACTIVE_WORKER:
                    if (!clientGUI.getClientController().checkIdWorker(tile[x][y].getIdWorker())) { //return true if there is a worker; false if empty
                        clientGUI.getSantoriniMainFrame().getLog().append("\n----\nThis is not your worker!\nPick yours!");
                    } else {
                        clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), tile[x][y].getIdWorker()));
                    }
                    this.activeWorker = tile[x][y].getIdWorker();
                    break;
                case NOT_YOUR_TURN:
                    clientGUI.getSantoriniMainFrame().getLog().append("\n----\nWAIT! It's not your turn!");
                    break;
                case SELECT_COORDINATE_MOVE:
                case MOVE_AND_COORDINATE_BUILD:
                    Coordinates newC = new Coordinates(x, y);
                    //for normal build NO! you cannot create levels on opposite workers
                    for (Coordinates c : possibleTile) {
                        if (newC.equals(c)) {
                            flag = true;
                            clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), newC));
                        }
                    }
                    if (!flag) {
                        clientGUI.getSantoriniMainFrame().getLog().append("\n----\nError:\nYou cannot move there!\nChoose another tile");
                    }
                    break;
                case ARTEMIS_FIRST_MOVE:
                case FIRST_BUILD_DEMETER:
                case ARES_POWER:
                    newC = new Coordinates(x, y);
                    for (Coordinates c : possibleTile) {
                        if (newC.equals(c)) {
                            flag = true;
                            clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), x + "," + y));
                        }
                    }
                    if (!flag) {
                        clientGUI.getSantoriniMainFrame().getLog().append("\n----\nError:\nYou cannot move there!\nChoose another tile");
                    }
                    break;
                case BUILD_ATLAS:
                    newC = new Coordinates(x, y);
                    for (Coordinates c : possibleTile) {
                        if (newC.equals(c)) {
                            flag = true;
                            Object[] options = {"DOME",
                                "STD LV"};
                            int i = JOptionPane.showOptionDialog(clientGUI.getSantoriniMainFrame(),
                                "Which element do you want to build?",
                                "ATLAS POWER",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,     //do not use a custom Icon
                                options,  //the titles of buttons
                                options[0]); //default button title
                            if (i == 0) {
                                clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), "dome " + x + "," + y));
                            }
                            if (i == 1) {
                                clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), "std " + x + "," + y));
                            }
                        }
                    }
                    if (!flag) {
                        clientGUI.getSantoriniMainFrame().getLog().append("\n----\nError:\nYou cannot move there!\nChoose another tile");
                    }
                    break;
                case BUILD_EPHAESTUS:
                    newC = new Coordinates(x, y);
                    for (Coordinates c : possibleTile) {
                        if (newC.equals(c)) {
                            flag = true;
                            if (tile[x][y].getLevel() >= 2) {
                                clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), "no " + x + "," + y));
                            } else {
                                Object[] options = {"YES",
                                    "NO"};
                                int i = JOptionPane.showOptionDialog(clientGUI.getSantoriniMainFrame(),
                                    "Do you want Build a second time in the same space",
                                    "EPHAESTUS POWER",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,     //do not use a custom Icon
                                    options,  //the titles of buttons
                                    options[0]); //default button title
                                if (i == 0) {
                                    clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), "yes " + x + "," + y));
                                }
                                if (i == 1) {
                                    clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), "no " + x + "," + y));
                                }
                            }
                        }
                    }
                    if (!flag) {
                        clientGUI.getSantoriniMainFrame().getLog().append("\n----\nError:\nYou cannot move there!\nChoose another tile");
                    }
                    break;
                case PROMETHEUS_CHOOSE:
                    newC = new Coordinates(x, y);
                    for (Coordinates c : possibleTile) {
                        if (newC.equals(c)) {
                            flag = true;
                            int choose = clientGUI.getChoosePrometheus();
                            if (choose == 0) {
                                clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), "MOVE " + x + "," + y));
                            }
                            if (choose == 1) {
                                clientGUI.asyncWriteToSocket(new Message(clientGUI.getClientAction().getValue(), "BUILD " + x + "," + y));
                            }
                        }
                    }
                    if (!flag) {
                        clientGUI.getSantoriniMainFrame().getLog().append("\n----\nError:\nYou cannot move there!\nChoose another tile");
                    }
            }
        }catch (NullPointerException e){
            clientGUI.messageHandler(new ReturnMessage(29,clientGUI.getClientController().getIdPlayer()));
        }
    }

    /**
     * This method colors the borders of the tiles contained in the possibleMove Arraylist
     * @param possibleMove
     */
    public void drawPossibleBorder(ArrayList<Coordinates> possibleMove) {
        possibleTile = possibleMove;  //register the tile where you can move
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nColumn; j++) {
                Coordinates c1 = new Coordinates(i, j);
                for (Coordinates c : possibleMove) {
                    if (c.equals(c1) && possibleMove.size() != 0) {
                        tile[i][j].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
                    }
                }
            }
        }
    }

    /**
     * This method resets the borders of all the tiles
     */
    public void setDefaultBorder() {
        if (possibleTile != null && possibleTile.size() > 0) {
            for (Coordinates c : possibleTile) {
                tile[c.getX()][c.getY()].setBorder(BorderFactory.createLineBorder(Color.black, 3));
                if (tile[c.getX()][c.getY()].getDome()) {
                    tile[c.getX()][c.getY()].setEnabled(false);
                }
            }
        }
    }

    /**
     * Colors the tile in function of the level
     * @param x
     * @param y
     * @param level
     * @param dome
     */
    public void drawLevel(int x, int y, int level, boolean dome) {
        tile[x][y].build(level, dome);
    }
}
