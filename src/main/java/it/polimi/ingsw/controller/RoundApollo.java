package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class RoundApollo implements Round{
    private Board board;
    private Player player;

    public RoundApollo(Board board, Player player){
        this.board = board;
        this.player = player;
    }

    @Override
    public  boolean ExecuteRound(boolean Gameover) {
        boolean GameStatus;
        int x,y;
        x = askX();
        y = askY();
        GameStatus = domove(x,y,Gameover);
        x = askX();
        y = askY();
        dobuild(x,y);
        return GameStatus;
    }

    public int askX(){
        //chiede alla view la coordinata x
        return x;
    }

    public int askY(){
        //chiede alla view la coordinata y
        return y;
    }

    public boolean domove(int x, int y, boolean Gameover){  //aggiungere controllo worker senza mosse possibili
        int oldX,oldY;
        Worker activeWorker;
        activeWorker = askActiveWorker();
        oldX = activeWorker.getXcoordinate();
        oldY = activeWorker.getYcoordinate();
        if(board.getNround()==0) {
            while (board.isDome(x,y) || (board.getLevel(x,y)- board.getLevel(oldX,oldY))>1 || board.getWorker(x,y).getPlayer() == player){
                x=askX();
                y=askY();
            }
            if (board.isOccupied(x,y)) {
                board.moveWorker(oldX, oldY, board.getWorker(x, y));
                board.moveWorker(x, y, activeWorker);
                if (board.getLevel(x,y) == 3 && board.getLevel(oldX, oldY) == 2) {
                    Gameover = true;
                }
            } else {
                board.moveWorker(x,y,activeWorker);
                if (board.getLevel(x,y) == 3 && board.getLevel(oldX, oldY) == 2) {
                    Gameover = true;
                }
            }
        }else{
            while (board.isDome(x,y) || (board.getLevel(x,y)- board.getLevel(oldX,oldY))>0 || board.getWorker(x,y).getPlayer() == player){
                x=askX();
                y=askY();
            }
            if (board.isOccupied(x,y)) {
                board.moveWorker(oldX, oldY, board.getWorker(x, y));
                board.moveWorker(x, y, activeWorker);
                if (board.getLevel(x,y) == 3 && board.getLevel(oldX, oldY) == 2) {
                    Gameover = true;
                }
            } else {
                board.moveWorker(x,y,activeWorker);
                if (board.getLevel(x,y) == 3 && board.getLevel(oldX, oldY) == 2) {
                    Gameover = true;
                }
            }

        }
        return Gameover;
    }

    public void dobuild(int x,int y){
        while(board.isOccupied(x,y) || board.isDome(x,y)){
            x = askX();
            y = askY();
        }
        board.setLevel(x,y);
        if(board.getLevel(x,y)==4) {
                board.setDome(x,y);
        }
    }

    public Worker askActiveWorker(){
        Worker worker;
            //chiedere activeWorker alla view
        return worker;
    }
}


