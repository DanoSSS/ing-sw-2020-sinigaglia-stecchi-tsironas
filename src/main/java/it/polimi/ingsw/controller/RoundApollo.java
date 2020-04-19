package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

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
        Coordinates coordinates;
        coordinates = askCoordinatesMove;
        GameStatus = domove(coordinates,Gameover);
        coordinates = askCoordinatesBuild;
        dobuild(x,y);
        return GameStatus;
    }

    public ArrayList<Coordinates> possiblesApolloMoves(Coordinates coordinates){
        ArrayList<Coordinates> possibleCoordinates = new ArrayList<Coordinates>();
        Coordinates newCoordinates;
        int x;
        int y;
        x = coordinates.getX();
        y = coordinates.getY();
        for (int i=x-1;i<=x+1;x++){
            for(int j=y-1;j<=y+1;y++){
                if((i>=0 && i<=4)&&(j>=0 && j<=4){
                    newCoordinates = new Coordinates(i,j);
                }
                if(board.getNround()==0) {
                    if (board.isDome(newCoordinates) || (board.getLevel(newCoordinates) - board.getLevel(coordinates)) > 1 || board.getWorker(newCoordinates).getPlayer() == player) {
                        possibleCoordinates.add(newCoordinates);
                    }
                }
                else{
                    if (board.isDome(newCoordinates) || (board.getLevel(newCoordinates) - board.getLevel(coordinates)) > 0 || board.getWorker(newCoordinates).getPlayer() == player){
                    possibleCoordinates.add(newCoordinates);
                    }
                }
            }
        }
    }



    public Coordinates askCoordinatesMove(){
        Coordinates oldCoordinates;
        Coordinates coordinates;
        ArrayList<Coordinates> possibleCoordinates;
        Worker activeWorker;
        activeWorker = askActiveWorker();
        oldCoordinates = activeWorker.getCoordinates();
        possibleCoordinates=possiblesApolloMoves(oldCoordinates);

        //chiede alla view una coordinata tra quelle possibili

        return coordinates;
    }

    public int askCoordinatesBuild(){

        return ;
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
                board.moveWorker(oldX,oldY, board.getWorker(x,y));
                board.moveWorker(x, y,activeWorker);
                if (board.getLevel(x,y) == 3 && board.getLevel(oldX, oldY) == 2) {
                    Gameover = true;
                }
            } else {
                board.moveWorker(x,y,activeWorker);
                board.freeCellFromWorker(oldX,oldY);
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
                board.freeCellFromWorker(oldX,oldY);
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


