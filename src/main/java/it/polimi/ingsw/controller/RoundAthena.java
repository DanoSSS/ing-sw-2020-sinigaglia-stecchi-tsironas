package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class RoundAthena extends Round {

    public RoundAthena(Board board, Player player) {
        super(board, player);
    }


    public boolean ExecuteRound(boolean Gameover) {
        boolean gamestatus;
        int i;
        Worker activeWorker, newActiveWorker;
        ArrayList<Coordinates> possibleMoves, possibleBuilds;
        Coordinates moveCoordinates, buildCoordinates;
        activeWorker = askActiveWorker();
        possibleMoves = canMove(activeWorker);        //arraylist of possible coordinates where worker can move(da passare alla view)
        if (possibleMoves.size() == 0) {
            do {
                newActiveWorker = askOtherWorker();
            } while (newActiveWorker != activeWorker);
            activeWorker = newActiveWorker;
            possibleMoves = canMove(activeWorker);
            if (possibleMoves.size() == 0) {
                return false;       // se ci sono due giocatori vince l'altro, se sono in tre eliminare il giocatore
            }
        }
        moveCoordinates = askCoordinatesToMove(possibleMoves);
        gamestatus = doMove(moveCoordinates, Gameover, activeWorker);
        possibleBuilds = canBuild(activeWorker);        //arraylist of possible coordinates where worker can build(da passare alla view)
        if (possibleBuilds.size() == 0) {
            return false;       // se ci sono due giocatori vince l'altro, se sono in tre eliminare il giocatore
        }
        buildCoordinates = askCoordinatesToBuild(possibleBuilds);
        doBuild(buildCoordinates);
        return gamestatus;
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
                    if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) <= 1 && !board.isOccupied(newCoordinates)) {
                        possiblesMovesCoordinates.add(newCoordinates);
                    }
                }
            }
        }
        return possiblesMovesCoordinates;
    }


    public boolean doMove(Coordinates moveCoordinates,boolean GameOver,Worker activeWorker) {
        int count;
        Coordinates oldCoordinates;
        int x=activeWorker.getCoordinates().getX();
        int y=activeWorker.getCoordinates().getY();
        oldCoordinates = new Coordinates(x,y);
        if ((board.getLevel(moveCoordinates) - board.getLevel(oldCoordinates)) == 1) {
            count = board.getNumberOfPlayers();
            board.setNround(count - 1);
            board.freeCellFromWorker(oldCoordinates);
            board.moveWorker(moveCoordinates, activeWorker);
            if (board.getLevel(moveCoordinates) == 3 && board.getLevel(oldCoordinates) == 2) {
                GameOver = true;
            }
        } else {
            board.freeCellFromWorker(oldCoordinates);
            board.moveWorker(moveCoordinates, activeWorker);
            if (board.getLevel(moveCoordinates) == 3 && board.getLevel(oldCoordinates) == 2) {
                GameOver = true;
            }
        }
        return GameOver;
    }



}