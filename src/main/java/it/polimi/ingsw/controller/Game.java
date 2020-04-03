package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.Random;

public class Game {
    private int NumberOfPlayers;
    private Round CurrentRound;
    private Player player1,player2,player3;
    private Worker worker1,worker2,worker3,worker4,worker5,worker6;
    private Board board;
    private Round roundP1,roundP2,roundP3;
    private boolean GameOver;

    public boolean isGameOver() {
        return GameOver;
    }

    public void setGameOver(boolean gameOver) {
        GameOver = gameOver;
    }

    public void SelectChallenger (){

    }

    public void RoundCreationP1(Player player){
        if (player.getGod()== God.APOLLO) {
            roundP1 = new RoundApollo(board,player);
        }
        if (player.getGod()== God.ARTEMIS){
            roundP1 = new RoundArtemis();
        }
        if (player.getGod()== God.ATHENA){
            roundP1 = new RoundAthena();
        }
        if (player.getGod()== God.ATLAS){
            roundP1 = new RoundAtlas();
        }
        if (player.getGod()== God.DEMETER){
            roundP1 = new RoundDemeter();
        }
        if (player.getGod()== God.EPHEASTUS){
            roundP1 = new RoundEphaestus();
        }
        if (player.getGod()== God.MINOTAUR){
            roundP1 = new RoundMinotaur();
        }
        if (player.getGod()== God.PAN){
            roundP1 = new RoundPan();
        }
        if (player.getGod()== God.PROMETHEUS){
            roundP1 = new RoundPrometheus();
        }

    }

    public void RoundCreationP2(Player player){
        if (player.getGod()== God.APOLLO) {
            roundP2 = new RoundApollo(board, player);
        }
        if (player.getGod()== God.ARTEMIS){
            roundP2 = new RoundArtemis();
        }
        if (player.getGod()== God.ATHENA){
            roundP2 = new RoundAthena();
        }
        if (player.getGod()== God.ATLAS){
            roundP2 = new RoundAtlas();
        }
        if (player.getGod()== God.DEMETER){
            roundP2 = new RoundDemeter();
        }
        if (player.getGod()== God.EPHEASTUS){
            roundP2 = new RoundEphaestus();
        }
        if (player.getGod()== God.MINOTAUR){
            roundP2 = new RoundMinotaur();
        }
        if (player.getGod()== God.PAN){
            roundP2 = new RoundPan();
        }
        if (player.getGod()== God.PROMETHEUS){
            roundP2 = new RoundPrometheus();
        }

    }

    public void RoundCreationP3(Player player){
        if (player.getGod()== God.APOLLO) {
            roundP3 = new RoundApollo(board,player);
        }
        if (player.getGod()== God.ARTEMIS){
            roundP3 = new RoundArtemis();
        }
        if (player.getGod()== God.ATHENA){
            roundP3 = new RoundAthena();
        }
        if (player.getGod()== God.ATLAS){
            roundP3 = new RoundAtlas();
        }
        if (player.getGod()== God.DEMETER){
            roundP3 = new RoundDemeter();
        }
        if (player.getGod()== God.EPHEASTUS){
            roundP3 = new RoundEphaestus();
        }
        if (player.getGod()== God.MINOTAUR){
            roundP3 = new RoundMinotaur();
        }
        if (player.getGod()== God.PAN){
            roundP3 = new RoundPan();
        }
        if (player.getGod()== God.PROMETHEUS){
            roundP3 = new RoundPrometheus();
        }

    }

    public void StartGameOperation () {
        RoundCreationP1(player1);
        RoundCreationP2(player2);
        RoundCreationP3(player3);
        do{
            CurrentRound = SelectRound(CurrentRound);
            GameOver=CurrentRound.ExecuteRound(GameOver);
        }while(!GameOver);
    }

    public Round SelectRound(Round currentRound){
        Random random = new Random();
        int RandomVar;
        if(currentRound==null){
            RandomVar = random.nextInt(2);
            if (RandomVar==0) return roundP1;
            if (RandomVar==1) return roundP2;
            if (RandomVar==2) return roundP3;
        }
        else {
            if (currentRound == roundP1) return roundP2;
            if (currentRound == roundP2) return roundP3;
            if (currentRound == roundP3) return roundP1;
        }
        return null;
    }
    public void GodsSetup(){
        //deve chiedere al challenger di scegliere 3 gods e assegnarle all' attributo gods dei player
    }
}
