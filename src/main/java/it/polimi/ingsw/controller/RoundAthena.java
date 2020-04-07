package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;

public class RoundAthena implements Round {
    private Board board;
    private Player player;
//    private Cell  cell;

    public RoundAthena(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    @Override
    public boolean ExecuteRound(boolean Gameover) {
        //   boolean GameStatus;
        //   GameStatus=domove(/*cell*/,Gameover);                           //da rivedere paramatri interazione view
        //   dobuild(/*cell*/);                                     //bisogna passare sia vecchia cella sia nuova cella
        //   return GameStatus;                                     //oppure si ricava da player.worker.getcell
        return false;
    }


    //fa una move standard se non si sposta in altezza segna un flag pari a (n player-1)
    // che verrà passato a tutti i round e che se diverso da zero impedira di salire
    public boolean domove(Cell cell, boolean Gameover) {  //aggiungere controllo worker senza mosse possibili
        Cell oldCell;
        int count;
        oldCell = player.getWorker1().getCell();
        /*try {
            if (cell.isDome() || cell.isOccupied() || (cell.getLevel()- oldCell.getLevel())>1);
        }catch(impossibleMoveException e){};*/
        if ((cell.getLevel() - oldCell.getLevel()) == 1) {
            count = board.getNumberOfPlayers();
            board.setNround(count - 1);
            player.getWorker1().setCell(cell);
        }
        if (cell.getLevel() == 3 && oldCell.getLevel() == 2) {
            Gameover = true;
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

