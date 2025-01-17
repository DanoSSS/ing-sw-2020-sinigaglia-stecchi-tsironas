package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class RoundApollo extends Round {

    public RoundApollo(Board board, Player player) {
        super(board, player);
    }

    /**
     * method that calculate possible cell in wich current worker can move according Apollo rules
     * (worker can move also in cell occupied by other players' workers)
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
                    if (board.getNround() == 0) {
                        if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) <= 1) {
                            if(!board.isOccupied(newCoordinates)) {
                                possiblesMovesCoordinates.add(newCoordinates);
                            }else if(board.getWorker(newCoordinates).getPlayer() != player){
                                possiblesMovesCoordinates.add(newCoordinates);
                            }
                        }
                    } else {
                        if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) <= 0) {
                            if(!board.isOccupied(newCoordinates)) {
                                possiblesMovesCoordinates.add(newCoordinates);
                            }else if(board.getWorker(newCoordinates).getPlayer() != player){
                                possiblesMovesCoordinates.add(newCoordinates);
                            }
                        }
                    }
                }
            }
        }
        return possiblesMovesCoordinates;
    }

    /**
     * method that move worker in moveCoordinates(one from possibleMovesCoordinate found in RoundApollo's canMove ) and check if player wins
     * @param moveCoordinates
     * @param GameOver
     * @param activeWorker
     * @return GameOver
     */
    public boolean doMove(Coordinates moveCoordinates,boolean GameOver,Worker activeWorker) {
        Coordinates oldCoordinates;
        int x=activeWorker.getCoordinates().getX();
        int y=activeWorker.getCoordinates().getY();
        oldCoordinates = new Coordinates(x,y);
        if (board.isOccupied(moveCoordinates)) {
            board.moveWorker(oldCoordinates, board.getWorker(moveCoordinates));
            board.moveWorker(moveCoordinates, activeWorker);
        } else {
            board.moveWorker(moveCoordinates, activeWorker);
            board.freeCellFromWorker(oldCoordinates);
        }
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


