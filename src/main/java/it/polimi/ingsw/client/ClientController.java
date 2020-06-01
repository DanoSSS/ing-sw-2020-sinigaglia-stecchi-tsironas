package it.polimi.ingsw.client;

import it.polimi.ingsw.client.CLI.CellMessage;
import it.polimi.ingsw.utils.Action;

import java.io.Serializable;

public class ClientController implements Serializable {
    private final Integer[] idPlayers;
    private final int NPlayers;
    private final int idPlayer;
    private final String nickname;
    private String[] otherNickname;
    private int currentRoundIdPlayer;
    private Action turnInfo;
    private CellMessage[][] board;
    private Integer [] idWorkers;

    public void start() {
        board = new CellMessage[10][5];
        for (int i = 0; i < 10; i=i+2) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = CellMessage.LV0;
            }
        }
        for(int l=1; l<10; l=l+2){
            for (int j = 0; j < 5; j++) {
                board[l][j] = CellMessage.free;
            }
        }
    }

    public CellMessage[][] getBoard(){
        return board;
    }

    public void setWorkerCellMessage(int id,int x,int y){
        switch (id){
            case 0:
                board[x][y] = CellMessage.W0;
                break;
            case 1:
                board[x][y] = CellMessage.W1;
                break;
            case 2:
                board[x][y] = CellMessage.W2;
                break;
            case 3:
                board[x][y] = CellMessage.W3;
                break;
            case 4:
                board[x][y] = CellMessage.W4;
                break;
            case 5:
                board[x][y] = CellMessage.W5;
                break;
        }
    }

    public void freeWorkerCellMessage(int x,int y){
        board[x][y] = CellMessage.free;
    }

    public void buildCellMessage(int x,int y,int level,boolean dome) {
        if (dome) {
            board[x][y] = CellMessage.DOME;
        } else {
            switch (level) {
                case 1:
                    board[x][y] = CellMessage.LV1;
                    break;
                case 2:
                    board[x][y] = CellMessage.LV2;
                    break;
                case 3:
                    board[x][y] = CellMessage.LV3;
                    break;
                case 4:
                    board[x][y] = CellMessage.DOME;
                    break;
            }
        }
    }

    public ClientController(String nickname, int idPlayer, int NPlayers, Integer[] idPlayers, String[] nicknames,int currentPlayer) {
        this.idPlayer = idPlayer;
        idWorkers = new Integer[2];
        if(idPlayer==1){
            idWorkers[0]= 0;
            idWorkers[1]= 1;
        }
        else if(idPlayer==2){
            idWorkers[0]= 2;
            idWorkers[1]= 3;
        }
        else if(idPlayer==3){
            idWorkers[0]= 4;
            idWorkers[1]= 5;
        }
        this.nickname = nickname;
        this.NPlayers = NPlayers;
        this.idPlayers = idPlayers.clone();
        this.otherNickname = nicknames;
        this.currentRoundIdPlayer=currentPlayer;
        }

    public ClientController(String nickname, int idPlayer, int nPlayers, Integer[] idPlayers, String[] otherNickname, int currentRoundIdPlayer, Action turnInfo,CellMessage[][] board) {
        this.idPlayer = idPlayer;  //constructor for clone
        this.nickname = nickname;
        this.NPlayers = nPlayers;
        this.idPlayers = idPlayers;
        this.otherNickname=otherNickname;
        this.currentRoundIdPlayer=currentRoundIdPlayer;
        this.turnInfo=turnInfo;
        this.board = board;
    }

    public int getIdPlayer(int i) {
        return idPlayers[i];
    }
    public int getNPlayers() {
        return NPlayers;
    }
    public Integer[] getIdPlayers() {
        return idPlayers;
    }
    public String getNickname() {
        return nickname;
    }
    public int getCurrentRoundIdPlayer() {
        return currentRoundIdPlayer;
    }
    public String[] getOtherNickname(){
        return otherNickname;
    }
    public void setCurrentRoundIdPlayer(int currentRoundIdPlayer) {
        this.currentRoundIdPlayer = currentRoundIdPlayer;
    }
    public Action getTurnInfo() {
        return turnInfo;
    }
    public void setTurnInfo(Action turnInfo) {
        this.turnInfo = turnInfo;  //to do
    }
    public int getIdPlayer(){
        return idPlayer;
    }

    public ClientController clone(){
            Integer[] idPlayers = getIdPlayers();
            int NPlayers=getNPlayers();
            int idPlayer = getIdPlayer();
            String nickname=getNickname();
            String[] otherNickname = getOtherNickname();
            int currentRoundIdPlayer=getCurrentRoundIdPlayer();
            Action turnInfo=getTurnInfo();
            CellMessage[][] board = getBoard();
        return new ClientController(nickname,idPlayer,NPlayers,idPlayers,otherNickname,currentRoundIdPlayer,turnInfo,board);
    }

    public boolean checkIdWorker(int idWorker){
        if(idPlayer==1){
            if(idWorker==0 || idWorker==1)
                return true;
        }
        else if(idPlayer==2){
            if(idWorker==2 || idWorker==3)
                return true;
        }
        else if(idPlayer==3){
            if(idWorker==4 || idWorker==5)
                return true;
        }
        return false;
    }
}
