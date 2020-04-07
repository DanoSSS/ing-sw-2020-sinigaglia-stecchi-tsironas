package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;

public class RoundApollo implements Round{
    private Board board;
    private Player player;
    private Game game;
//    private Cell  cell;

    public RoundApollo(Board board, Player player){
        this.board = board;
        this.player = player;
    }



    @Override
    public  boolean ExecuteRound(boolean Gameover) {
    //    boolean GameStatus;
     //   GameStatus=domove(/*cell*/,Gameover);                           //da rivedere paramatri interazione view
     //   dobuild(/*cell*/);                                    //bisogna passare sia vecchia cella sia nuova cella
     //   return GameStatus;
        return false;
    }

    public boolean domove(Cell cell, boolean Gameover){  //aggiungere controllo worker senza mosse possibili
        Cell oldCell;
        if (player.getWorker1().isActiveWorker()){//per togliere gli if si può passare activeworker come parametro
            oldCell=player.getWorker1().getCell();
            if(board.getNround()==0) {
/*              try {
                    if (cell.isDome() || (cell.getLevel()- oldCell.getLevel())>1);
                }catch(impossibleMoveException e){};*/
                if (cell.isOccupied()) {
                    if (cell.getWorker().getPlayer() != player) {
                        cell.getWorker().setCell(oldCell);
                        player.getWorker1().setCell(cell);
                        if (cell.getLevel() == 3 && oldCell.getLevel() == 2) {
                            Gameover = true;
                        }
                    } else {
                        //chiedere nuova mossa
                    }
                } else {
                    player.getWorker1().setCell(cell);
                    if (cell.getLevel() == 3 && oldCell.getLevel() == 2) {
                        Gameover = true;
                    }
                }
            }else{
                /*try {
                    if (cell.isDome() || (cell.getLevel()- oldCell.getLevel())>0);
                }catch(impossibleMoveException e){};*/
                if (cell.isOccupied()) {
                    if (cell.getWorker().getPlayer() != player) {
                        cell.getWorker().setCell(oldCell);
                        player.getWorker1().setCell(cell);
                        if (cell.getLevel() == 3 && oldCell.getLevel() == 2) {
                            Gameover = true;
                        }
                    } else {
                        //chiedere nuova mossa
                    }
                } else {
                    player.getWorker1().setCell(cell);
                    if (cell.getLevel() == 3 && oldCell.getLevel() == 2) {
                        Gameover = true;
                    }
                }

            }
            }else if (player.getWorker2().isActiveWorker()){
/*            try {
                if (cell.isDome());
            }catch(impossibleMoveException e){};*/
            oldCell=player.getWorker2().getCell();
            if(cell.isOccupied()){
                if(cell.getWorker().getPlayer()!=player) {
                    cell.getWorker().setCell(oldCell);
                    player.getWorker2().setCell(cell);
                    if (cell.getLevel() == 3 && oldCell.getLevel() == 2) {
                        Gameover = true;
                    }
                }else {
                    //chiedere nuova mossa
                }
            }else {
                player.getWorker2().setCell(cell);
                if (cell.getLevel() == 3 && oldCell.getLevel() == 2) {
                    Gameover=true;
                }
            }
        }
        return Gameover;
    }

    public void dobuild(Cell cell){
        int level;
/*        if (player.getWorker1().isActiveWorker()){
            try{
                if(cell.isOccupied() || cell.isDome());
            }catch (impossiblebuildexception e){};*/
            level=cell.getLevel();
            level++;
            cell.setLevel(level);
            if(level==4){
                cell.setDome(true);
            }

/*        }else if (player.getWorker2().isActiveWorker()) {
            try {
                if (cell.isOccupied() || cell.isDome()) ;
            } catch (impossiblebuildexception e) {
            }
            ;
            level = cell.getLevel();
            level++;
            cell.setLevel(level);
            if (level == 4) {
                cell.setDome(true);
            }*/
        }


}


