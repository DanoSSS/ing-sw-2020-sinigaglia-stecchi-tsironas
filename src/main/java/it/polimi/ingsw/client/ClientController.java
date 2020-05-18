package it.polimi.ingsw.client;

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
    //private final ViewClient view;


    public ClientController(String nickname, int idPlayer, int NPlayers, Integer[] idPlayers, String[] nicknames) {
        this.idPlayer = idPlayer;
        this.nickname = nickname;
        this.NPlayers = NPlayers;
        this.idPlayers = idPlayers.clone();
        this.otherNickname = nicknames;
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
        return new ClientController(nickname,idPlayer,NPlayers,idPlayers,otherNickname,currentRoundIdPlayer,turnInfo);
    }

}
