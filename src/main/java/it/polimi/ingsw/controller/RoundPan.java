package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;

public class RoundPan implements Round {
    private Board board;
    private Player player;
    private Game game;
//    private Cell  cell;

    public RoundPan(Board board, Player player){
        this.board = board;
        this.player = player;
    }

    @Override
    public boolean ExecuteRound(boolean Gameover) {
        //    boolean GameStatus;
        //   GameStatus=domove(/*cell*/,Gameover);                           //da rivedere paramatri interazione view
        //   dobuild(/*cell*/);                                    //bisogna passare sia vecchia cella sia nuova cella
        //   return GameStatus;
        return false;
    }
    public boolean domove(Cell cell, boolean Gameover){
        Cell OldCell;
        OldCell = player.getWorker1().getCell();
        if (board.getNround() == 0) {
            /*try {
                if (cell.isDome() || cell.isOccupied() || (cell.getLevel()- oldCell.getLevel())>1);
            }catch(impossibleMoveException e){};*/
            player.getWorker1().setCell(cell);
            if ((cell.getLevel() == 3 && OldCell.getLevel() == 2) || (cell.getLevel() == 0 && OldCell.getLevel() == 2) || (cell.getLevel() == 1 && OldCell.getLevel() == 3) || (cell.getLevel() == 0 && OldCell.getLevel() == 3)) {
                Gameover = true;
            }
        }
        else {
            /*try {
                if (cell.isDome() || cell.isOccupied() || (cell.getLevel()- oldCell.getLevel())>0);
            }catch(impossibleMoveException e){};*/
            player.getWorker1().setCell(cell);
            if ((cell.getLevel() == 3 && OldCell.getLevel() == 2) || (cell.getLevel() == 0 && OldCell.getLevel() == 2) || (cell.getLevel() == 1 && OldCell.getLevel() == 3) || (cell.getLevel() == 0 && OldCell.getLevel() == 3)) {
                Gameover = true;
            }
        }
        return Gameover;
    }

    public void dobuild(Cell cell) {
        int level;
        /*try{
            if(cell.isOccupied() || cell.isDome());
        }catch (impossiblebuildexception e){};*/
        level = cell.getLevel();
        level++;
        cell.setLevel(level);
        if (level == 4) {
            cell.setDome(true);
        }
    }
}
