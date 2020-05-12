package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;

import java.util.*;

public class Game extends Observable<Object> implements Observer<Object> {
    private int currentRound = 2;
    private Board board;
    private List<Player> players= new ArrayList<>();
    private Map<Player,Round> round = new HashMap<Player,Round>();
    private boolean GameOver;
    private int NumberOfPlayers;
    private boolean isChallengerTurn=true;

    public boolean isChallengerTurn() {
        return isChallengerTurn;
    }

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



    public boolean isGameOver() { return GameOver; }

    public void setGameOver(boolean gameOver) {
        GameOver = gameOver;
    }

    public void SelectChallenger (){

    }

    public void RoundCreationP1(Player player){
        Round roundP1;
        if (player.getGod()== God.APOLLO) {
            roundP1 = new RoundApollo(board,player);
            round.put(player,roundP1);
        }
        if (player.getGod()== God.ARTEMIS){
            roundP1 = new RoundArtemis(board,player);
            round.put(player,roundP1);
        }
        if (player.getGod()== God.ATHENA){
            roundP1 = new RoundAthena(board,player);
            round.put(player,roundP1);
        }
        if (player.getGod()== God.ATLAS){
            roundP1 = new RoundAtlas(board,player);
            round.put(player,roundP1);
        }
        if (player.getGod()== God.DEMETER){
            roundP1 = new RoundDemeter(board,player);
            round.put(player,roundP1);
        }
        if (player.getGod()== God.EPHAESTUS){
            roundP1 = new RoundEphaestus(board,player);
            round.put(player,roundP1);
        }
        if (player.getGod()== God.MINOTAUR){
            roundP1 = new RoundMinotaur(board,player);
            round.put(player,roundP1);
        }
        if (player.getGod()== God.PAN){
            roundP1 = new RoundPan(board,player);
            round.put(player,roundP1);
        }
        if (player.getGod()== God.PROMETHEUS){
            roundP1 = new RoundPrometheus(board,player);
            round.put(player,roundP1);
        }

    }

    public void RoundCreationP2(Player player){
        Round roundP2;
        if (player.getGod()== God.APOLLO) {
            roundP2 = new RoundApollo(board, player);
            round.put(player,roundP2);
        }
        if (player.getGod()== God.ARTEMIS){
            roundP2 = new RoundArtemis(board,player);
            round.put(player,roundP2);
        }
        if (player.getGod()== God.ATHENA){
            roundP2 = new RoundAthena(board,player);
            round.put(player,roundP2);
        }
        if (player.getGod()== God.ATLAS){
            roundP2 = new RoundAtlas(board,player);
            round.put(player,roundP2);
        }
        if (player.getGod()== God.DEMETER){
            roundP2 = new RoundDemeter(board,player);
            round.put(player,roundP2);
        }
        if (player.getGod()== God.EPHAESTUS){
            roundP2 = new RoundEphaestus(board,player);
            round.put(player,roundP2);
        }
        if (player.getGod()== God.MINOTAUR){
            roundP2 = new RoundMinotaur(board,player);
            round.put(player,roundP2);
        }
        if (player.getGod()== God.PAN){
            roundP2 = new RoundPan(board,player);
            round.put(player,roundP2);
        }
        if (player.getGod()== God.PROMETHEUS){
            roundP2 = new RoundPrometheus(board,player);
            round.put(player,roundP2);
        }

    }

    public void RoundCreationP3(Player player){
        Round roundP3;
        if (player.getGod()== God.APOLLO) {
            roundP3 = new RoundApollo(board,player);
            round.put(player,roundP3);
        }
        if (player.getGod()== God.ARTEMIS){
            roundP3 = new RoundArtemis(board,player);
            round.put(player,roundP3);

        }
        if (player.getGod()== God.ATHENA){
            roundP3 = new RoundAthena(board,player);
            round.put(player,roundP3);

        }
        if (player.getGod()== God.ATLAS){
            roundP3 = new RoundAtlas(board,player);
            round.put(player,roundP3);

        }
        if (player.getGod()== God.DEMETER){
            roundP3 = new RoundDemeter(board,player);
            round.put(player,roundP3);

        }
        if (player.getGod()== God.EPHAESTUS){
            roundP3 = new RoundEphaestus(board,player);
            round.put(player,roundP3);

        }
        if (player.getGod()== God.MINOTAUR){
            roundP3 = new RoundMinotaur(board,player);
            round.put(player,roundP3);

        }
        if (player.getGod()== God.PAN){
            roundP3 = new RoundPan(board,player);
            round.put(player,roundP3);

        }
        if (player.getGod()== God.PROMETHEUS){
            roundP3 = new RoundPrometheus(board,player);
            round.put(player,roundP3);

        }

    }

    public Round getRoundP1(Player player){
        return round.get(player);
    }

    public Round getRoundP2(Player player){
        return round.get(player);
    }

    public Round getRoundP3(Player player){
        return round.get(player);
    }

    //function that select
    public void SelectRound(Player currentPlayer) {
        round.get(currentPlayer).ExecuteRound(GameOver);
        UpdateRound();
    }

    public void UpdateRound(){
        currentRound++;
        if(NumberOfPlayers==2 && currentRound==3){
            currentRound=1;
            board.initRound(currentRound);
        }
        else if(NumberOfPlayers==3 && currentRound==4){
            currentRound=1;
            board.initRound(currentRound);
        }
        else{board.initRound(currentRound);}
    }

    @Override
    public void update(Object message) {
        Action a=((Message) message).getAction(); //0
        int flag=0,id=1,player=1; //0

        switch (a){
            case INITWORKERS:
                //imposta a board le posizioni degli worker
                //Iterator it = (((Message)message).getInitWorkerList()).iterator();
                Map<Coordinates,Player> workerPosition = new HashMap<>();/*
                while (it.hasNext() && ((NumberOfPlayers==3 && flag<=6) || (NumberOfPlayers==2 && flag<=4))  ){
                    Coordinates c = (Coordinates)it.next();
                             //1,2,3,4,5,6
                    id++;    //2,3,4,5,0,1
                    if(NumberOfPlayers==3){
                        if(flag==3){id=0;player++;}      //when list of coordinates is at 4th position -> idworker0 -> player1
                    }else if(NumberOfPlayers==2){
                        if(flag==3) {id=0;player--;}     //when list of coordinates is at 2th position -> idworker0 -> player1
                    }else if(flag==5){player=0;}
                    workerPosition.put(c,players.get(player));
                    board.setWorker(c,id);
                    flag++;
                }*/
                for (Iterator<Coordinates> it = (((Message)message).getInitWorkerList()).iterator(); it.hasNext();) {
                    Coordinates c = (Coordinates)it.next();
                             //0,1,2,3,4,5
                    id++;    //2,3,4,5,0,1
                    if(NumberOfPlayers==3){
                        if(flag==2){id=0;player++;}      //when list of coordinates is at 4th position -> idworker0 -> player1
                    }else if(NumberOfPlayers==2){
                        if(flag==2) {id=0;player--;}     //when list of coordinates is at 2th position -> idworker0 -> player1
                    }else if(flag==5){player=0;}
                    workerPosition.put(c,players.get(player));
                    board.setWorker(c,id);
                    flag++;
                }
                board.getObservableModel().notify(new ReturnMessage(4,workerPosition));
                board.initRound(currentRound);

            case INITEXECUTEROUND:
                SelectRound(((Message) message).getPlayer());
        }
    }


}

