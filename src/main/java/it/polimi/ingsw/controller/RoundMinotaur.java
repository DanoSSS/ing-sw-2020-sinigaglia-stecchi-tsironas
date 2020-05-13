package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

public class RoundMinotaur extends Round {

    public RoundMinotaur(Board board, Player player) {
        super(board, player);
    }


    @Override
    public void update(Object message) {

    }




    public ArrayList<Coordinates> canMove(Worker worker) {
        Coordinates coordinates, newCoordinates;
        int x, y;
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
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
                            }else if(board.getWorker(newCoordinates).getPlayer() != player && minotaurForcing(coordinates,newCoordinates)){
                                possiblesMovesCoordinates.add(newCoordinates);
                            }
                        }
                    } else {
                        if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) == 0 ) {
                            if(!board.isOccupied(newCoordinates)) {
                                possiblesMovesCoordinates.add(newCoordinates);
                            }else if(board.getWorker(newCoordinates).getPlayer() != player && minotaurForcing(coordinates,newCoordinates)){
                                possiblesMovesCoordinates.add(newCoordinates);
                            }
                        }
                    }
                }
            }
        }
        return possiblesMovesCoordinates;
    }

    public boolean doMove(Coordinates moveCoordinates,boolean GameOver,Worker activeWorker){
        Coordinates oldCoordinates;
        int x=activeWorker.getCoordinates().getX();
        int y=activeWorker.getCoordinates().getY();
        oldCoordinates = new Coordinates(x,y);
        if(board.isOccupied(moveCoordinates)) {
            board.moveWorker(newOpponentCoordinate(oldCoordinates, moveCoordinates), board.getWorker(moveCoordinates));
        }
        board.freeCellFromWorker(oldCoordinates);
        board.moveWorker(moveCoordinates, activeWorker);
        if (board.getLevel(moveCoordinates) == 3 && board.getLevel(oldCoordinates) == 2) {
            GameOver = true;
        }

        return GameOver;
    }


    public boolean minotaurForcing (Coordinates workerCoordinates, Coordinates opponentCoordinates){
        int x,y;
        Coordinates newCoordinates;
        x=(opponentCoordinates.getX()-workerCoordinates.getX())+opponentCoordinates.getX();
        y=(opponentCoordinates.getY()-workerCoordinates.getY())+opponentCoordinates.getY();
        if(x>=0 && x<=4 && y>=0 && y<=4){
            newCoordinates = new Coordinates(x,y);
            if(!board.isOccupied(newCoordinates) && !board.isDome(newCoordinates)){
                return true;
            }else return false;
        }else return false;
    }

    public Coordinates newOpponentCoordinate (Coordinates workerCoordinates, Coordinates opponentCoordinates) {
        int x, y;
        Coordinates newCoordinates;
        x = (opponentCoordinates.getX() - workerCoordinates.getX()) + opponentCoordinates.getX();
        y = (opponentCoordinates.getY() - workerCoordinates.getY()) + opponentCoordinates.getY();
        newCoordinates = new Coordinates(x, y);
        return newCoordinates;
    }


}
