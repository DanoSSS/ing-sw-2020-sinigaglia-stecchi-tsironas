package it.polimi.ingsw.client;

import it.polimi.ingsw.client.CLI.CellMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Action;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientController implements Serializable {
    private final Integer[] idPlayers;
    private final int NPlayers;
    private final int idPlayer;
    private final String nickname;
    private String[] otherNickname;
    private int currentRoundIdPlayer;
    private Action turnInfo;
    private ArrayList<Player> playersList;
    private int[] idWorkers= new int[2];

    /**
     * Constructor
     * @param nickname
     * @param idPlayer
     * @param NPlayers
     * @param idPlayers
     * @param nicknames
     * @param currentPlayer
     * @param playersList
     */
    public ClientController(String nickname, int idPlayer, int NPlayers, Integer[] idPlayers, String[] nicknames, int currentPlayer, ArrayList<Player> playersList) {
        this.idPlayer = idPlayer;
        if(idPlayer==1){
            idWorkers[1]= 1;
            idWorkers[0]= 0;
        }
        else if(idPlayer==2){
            idWorkers[0]= 2;
            idWorkers[1]= 3;
        }
        else if(idPlayer==3 && NPlayers==3){
            idWorkers[0]= 4;
            idWorkers[1]= 5;
        }
        this.nickname = nickname;
        this.NPlayers = NPlayers;
        this.idPlayers = idPlayers.clone();
        this.otherNickname = nicknames;
        this.currentRoundIdPlayer=currentPlayer;
        this.playersList=playersList;
        }

    /**
     * Constructor for the clone
     * @param nickname
     * @param idPlayer
     * @param nPlayers
     * @param idPlayers
     * @param otherNickname
     * @param currentRoundIdPlayer
     * @param idWorkers
     * @param turnInfo
     * @param playersList
     */
    public ClientController(String nickname, int idPlayer, int nPlayers, Integer[] idPlayers, String[] otherNickname, int currentRoundIdPlayer,int[] idWorkers, Action turnInfo,ArrayList<Player> playersList) {
        this.idPlayer = idPlayer;
        this.nickname = nickname;
        this.NPlayers = nPlayers;
        this.idPlayers = idPlayers;
        this.otherNickname=otherNickname;
        this.currentRoundIdPlayer=currentRoundIdPlayer;
        this.idWorkers=idWorkers;
        this.turnInfo=turnInfo;
        this.playersList=playersList;
    }

    /**
     *
     * @return Nplayers
     */
    public int getNPlayers() {
        return NPlayers;
    }

    /**
     *
     * @return idPlayers
     */
    public Integer[] getIdPlayers() {
        return idPlayers;
    }

    /**
     *
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return currentRoundIdPlayer
     */
    public int getCurrentRoundIdPlayer() {
        return currentRoundIdPlayer;
    }

    /**
     *
     * @return otherNickname
     */
    public String[] getOtherNickname(){
        return otherNickname;
    }

    /**
     *
     * @param currentRoundIdPlayer
     */
    public void setCurrentRoundIdPlayer(int currentRoundIdPlayer) {
        this.currentRoundIdPlayer = currentRoundIdPlayer;
    }

    /**
     *
     * @return turnInfo
     */
    public Action getTurnInfo() {
        return turnInfo;
    }

    /**
     *
     * @return playerList
     */
    public ArrayList<Player> getPlayersList() {
        return playersList;
    }

    /**
     *
     * @return idPlayer
     */
    public int getIdPlayer(){
        return idPlayer;
    }

    /**
     * It clones the client controller
     * @return
     */
    public ClientController clone(){
            Integer[] idPlayers = getIdPlayers();
            int NPlayers=getNPlayers();
            int idPlayer = getIdPlayer();
            String nickname=getNickname();
            String[] otherNickname = getOtherNickname();
            int currentRoundIdPlayer=getCurrentRoundIdPlayer();
            Action turnInfo=getTurnInfo();
            ArrayList<Player> playersList=getPlayersList();
        return new ClientController(nickname,idPlayer,NPlayers,idPlayers,otherNickname,currentRoundIdPlayer,getIdWorkers(),turnInfo,playersList/*,board*/);
    }

    /**
     * Checks that the idWorker is legit
     * @param idWorker
     * @return
     */
    public boolean checkIdWorker(int idWorker){
        if(idWorker<=5) {
            if (idPlayer == 1) {
                if (idWorker == 0 || idWorker == 1)
                    return true;
            } else if (idPlayer == 2) {
                if (idWorker == 2 || idWorker == 3)
                    return true;
            } else if (NPlayers == 3 && idPlayer == 3) {
                if (idWorker == 4 || idWorker == 5)
                    return true;
            }
        }
        return false;
    }

    /**
     *
     * @return idWorker
     */
    public int[] getIdWorkers() {
        return idWorkers;
    }


}
