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
    private Integer [] idWorkers;

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

    public ClientController(String nickname, int idPlayer, int nPlayers, Integer[] idPlayers, String[] otherNickname, int currentRoundIdPlayer, Action turnInfo) {
        this.idPlayer = idPlayer;  //constructor for clone
        this.nickname = nickname;
        this.NPlayers = nPlayers;
        this.idPlayers = idPlayers;
        this.otherNickname=otherNickname;
        this.currentRoundIdPlayer=currentRoundIdPlayer;
        this.turnInfo=turnInfo;
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
        return new ClientController(nickname,idPlayer,NPlayers,idPlayers,otherNickname,currentRoundIdPlayer,turnInfo/*,board*/);
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

    public boolean checkPresenceWorker(int idWorker) {
        if (idWorker>0){
            return false;
        } else{
            return true;
        }
    }
}
