package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;

import java.util.*;

public class Game extends Observable<Object> implements Observer<Object> {
    private Board board;
    private List<Player> players= new ArrayList<>();
    private Map<Player,Round> round = new HashMap<Player,Round>();
    private int NumberOfPlayers;


    public Game(Board board,int i,Player p1,Player p2){
        this.board=board;
        this.NumberOfPlayers=i;
        this.players.add(p1);
        this.players.add(p2);
    }

    public Game(Board board,int i,Player p1,Player p2,Player p3){
        this.board=board;
        this.NumberOfPlayers=i;
        this.players.add(p1);
        this.players.add(p2);
        this.players.add(p3);
    }


    public void RoundCreation(Player player,Round r) {
        if (player.getGod()== God.APOLLO) {
            r = new RoundApollo(board,player);
            round.put(player,r);
        }
        if (player.getGod()== God.ARTEMIS){
            r = new RoundArtemis(board,player);
            round.put(player,r);

        }
        if (player.getGod()== God.ATHENA){
            r = new RoundAthena(board,player);
            round.put(player,r);

        }
        if (player.getGod()== God.ATLAS){
            r = new RoundAtlas(board,player);
            round.put(player,r);

        }
        if (player.getGod()== God.DEMETER){
            r = new RoundDemeter(board,player);
            round.put(player,r);

        }
        if (player.getGod()== God.EPHAESTUS){
            r = new RoundEphaestus(board,player);
            round.put(player,r);

        }
        if (player.getGod()== God.MINOTAUR){
            r = new RoundMinotaur(board,player);
            round.put(player,r);

        }
        if (player.getGod()== God.PAN){
            r = new RoundPan(board,player);
            round.put(player,r);

        }
        if (player.getGod()== God.PROMETHEUS){
            r = new RoundPrometheus(board,player);
            round.put(player,r);

        }
    }

    public Round getRound(Player player){
        return round.get(player);
    }


    @Override
    public void update(Object message) {
        Action a=((Message) message).getAction(); //0
        int flag=0,id=1,player=1; //0

        if (a == Action.INIT_WORKERS) {//imposta a board le posizioni degli worker
            Map<Worker, Coordinates> workerPosition = new HashMap<>();
            for (Coordinates c : ((Message) message).getInitWorkerList()) {
                //0,1,2,3,4,5
                id++;    //2,3,4,5,0,1
                if (NumberOfPlayers == 3) {
                    if (flag == 2) {
                        player++;
                    }      //when list of coordinates is at 4th position -> idworker0 -> player1
                    else if (flag == 4) {
                        player = 0;
                        id = 0;
                    }
                } else if (NumberOfPlayers == 2) {
                    if (flag == 2) {
                        id = 0;
                        player--;
                    }     //when list of coordinates is at 2th position -> idworker0 -> player1
                }
                workerPosition.put(board.getWorkerById(id), c);
                board.setWorker(c, id);
                flag++;
            }
            String[] players = board.getPlayerNicknames();
            Integer[] idPlayers = board.getIdPlayers();
            int firstToStartID = board.getCurrentRound();
            //int idPlayer1= idPlayers[messageFromPlayerNumber];
            //String nicknameP1=players[messageFromPlayerNumber];
            board.getObservableModel().notify(new ReturnMessage(3, workerPosition, players, idPlayers, NumberOfPlayers, firstToStartID));
        }
        else if (a == Action.END_GAME){
            int win =((Message) message).getIdWorker();
            board.getObservableModel().notify(new ReturnMessage(28,win));

        }
    }


}

