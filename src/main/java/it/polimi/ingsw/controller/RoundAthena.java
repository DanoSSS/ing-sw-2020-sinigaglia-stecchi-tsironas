package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class RoundAthena extends Round {

    public RoundAthena(Board board, Player player) {
        super(board, player);
    }

    /**
     * method that calculate possible cell in wich current worker can move according Athena rules
     * @param worker
     * @return possibleMovesCoordinate
     */
    public ArrayList<Coordinates> canMove(Worker worker) {
        Coordinates coordinates, newCoordinates;
        int x, y;
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<>();
        coordinates = worker.getCoordinates();
        x = coordinates.getX();
        y = coordinates.getY();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i <= 4 && j >= 0 && j <= 4) {
                    newCoordinates = new Coordinates(i, j);
                    if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) <= 1 && !board.isOccupied(newCoordinates)) {
                        possiblesMovesCoordinates.add(newCoordinates);
                    }
                }
            }
        }
        return possiblesMovesCoordinates;
    }

    /**
     * method that move worker in moveCoordinates(one from possibleMovesCoordinate found in RoundApollo's canMove ),
     * check if player wins and set board attribute Nround equal to the number of players less 1 if active worker levels up
     * @param moveCoordinates
     * @param GameOver
     * @param activeWorker
     * @return GameOver
     */
    public boolean doMove(Coordinates moveCoordinates,boolean GameOver,Worker activeWorker) {
        int count;
        Coordinates oldCoordinates;
        int x=activeWorker.getCoordinates().getX();
        int y=activeWorker.getCoordinates().getY();
        oldCoordinates = new Coordinates(x,y);
        if ((board.getLevel(moveCoordinates) - board.getLevel(oldCoordinates)) == 1) {
            count = board.getNumberOfPlayers();
            board.setNround(count);
        }
        board.freeCellFromWorker(oldCoordinates);
        board.moveWorker(moveCoordinates, activeWorker);
        if (board.getLevel(moveCoordinates) == 3 && board.getLevel(oldCoordinates) == 2) {
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