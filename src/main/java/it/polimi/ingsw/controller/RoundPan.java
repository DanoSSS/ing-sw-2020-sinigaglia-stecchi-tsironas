package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;


public class RoundPan extends Round {

    public RoundPan(Board board, Player player) {
        super(board, player);
    }

    /**
     * method that move worker in moveCoordinates and check if player wins according pan win conditions
     * @param moveCoordinates
     * @param GameOver
     * @param activeWorker
     * @return GameOver
     */
    public boolean doMove(Coordinates moveCoordinates,boolean GameOver,Worker activeWorker){
        Coordinates oldCoordinates;
        int x=activeWorker.getCoordinates().getX();
        int y=activeWorker.getCoordinates().getY();
        oldCoordinates = new Coordinates(x,y);
        board.freeCellFromWorker(oldCoordinates);
        board.moveWorker(moveCoordinates,activeWorker);
        if ((board.getLevel(moveCoordinates) == 3 && board.getLevel(oldCoordinates) == 2) || (board.getLevel(moveCoordinates) == 0 && board.getLevel(oldCoordinates) == 2) || (board.getLevel(moveCoordinates) == 1 && board.getLevel(oldCoordinates) == 3) || (board.getLevel(moveCoordinates) == 0 && board.getLevel(oldCoordinates) == 3)) {
            if(board.getHeraPlayer()>0){
                if(!heraPower(moveCoordinates)){
                    GameOver = true;
                }
            }else {
                GameOver = true;
            }
        }
        return GameOver;
    }

}