package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

public class RoundEphaestus implements Round {
    private Board board;
    private Player player;

    public RoundEphaestus(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    @Override
    public boolean ExecuteRound(boolean Gameover) {
        boolean gamestatus,buildSecondSameSpace;
        int i;
        Worker activeWorker,newActiveWorker;
        ArrayList<Coordinates> possibleMoves,possibleBuilds;
        Coordinates moveCoordinates,buildCoordinates;
        activeWorker = askActiveWorker();
        possibleMoves=canMove(activeWorker);        //arraylist of possible coordinates where worker can move(da passare alla view)
        if(possibleMoves.size()==0){
            do{
                newActiveWorker=askOtherWorker();
            }while(newActiveWorker!=activeWorker);
            activeWorker=newActiveWorker;
            possibleMoves=canMove(activeWorker);
            if(possibleMoves.size()==0){
                return false;       // se ci sono due giocatori vince l'altro, se sono in tre eliminare il giocatore
            }
        }
        moveCoordinates=askCoordinatesToMove(possibleMoves);
        gamestatus=doMove(moveCoordinates,Gameover,activeWorker);
        possibleBuilds = canBuild(activeWorker);        //arraylist of possible coordinates where worker can build(da passare alla view)
        if(possibleBuilds.size()==0){
            return false;       // se ci sono due giocatori vince l'altro, se sono in tre eliminare il giocatore
        }
        buildCoordinates = askCoordinatesToBuild(possibleBuilds);
        doBuild(buildCoordinates);
        if(board.getLevel(buildCoordinates)<3){
            buildSecondSameSpace=askIfBUildSecond();
            if(buildSecondSameSpace){
                board.setLevel(buildCoordinates);
            }
        }
        if(board.getNround()!=0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        return gamestatus;
    }

    public Worker askActiveWorker() {
        //chiede alla view di selezionare un worker
        return worker;
    }

    public Worker askOtherWorker() {
        //chiede alla view di selezionare l'altro worker
        return worker;
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
                        if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) <= 1 && !board.isOccupied(newCoordinates)) {
                            possiblesMovesCoordinates.add(newCoordinates);
                        }
                    } else {
                        if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) == 0 && !board.isOccupied(newCoordinates)) {
                            possiblesMovesCoordinates.add(newCoordinates);
                        }
                    }
                }
            }
        }
        return possiblesMovesCoordinates;
    }

    public Coordinates askCoordinatesToMove(ArrayList<Coordinates> possibleCoordinates){
        //dare alla view arraylist e chiedere al player dove vuole andare se non si può passare da controller si fa
        //un currentlypossiblemove in board
        return coordinates;
    }

    public boolean doMove(Coordinates moveCoordinates,boolean GameOver,Worker activeWorker){
        Coordinates oldCoordinates;
        oldCoordinates=activeWorker.getCoordinates();
        board.freeCellFromWorker(oldCoordinates);
        board.moveWorker(moveCoordinates,activeWorker);
        if (board.getLevel(moveCoordinates) == 3 && board.getLevel(oldCoordinates) == 2) {
            GameOver = true;
        }
        return GameOver;
    }

    public ArrayList<Coordinates> canBuild(Worker worker) {
        Coordinates coordinates, newCoordinates;
        int x, y;
        ArrayList<Coordinates> possiblesBuildsCoordinates = new ArrayList<Coordinates>();
        coordinates = worker.getCoordinates();
        x = coordinates.getX();
        y = coordinates.getY();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i <= 4 && j >= 0 && j <= 4) {
                    newCoordinates = new Coordinates(i, j);
                    if (!board.isOccupied(newCoordinates) && !board.isDome(newCoordinates)) {
                        possiblesBuildsCoordinates.add(newCoordinates);
                    }
                }
            }
        }
        return possiblesBuildsCoordinates;
    }

    public Coordinates askCoordinatesToBuild(ArrayList<Coordinates> possibleCoordinates){
        //dare alla view arraylist e chiedere al player dove vuole costruire se non si può passare da controller si fa
        //un currentlypossiblebuild in board
        return coordinates;
    }

    public void doBuild(Coordinates buildCoordinate){
        board.setLevel(buildCoordinate);
        if(board.getLevel(buildCoordinate)==4) {
            board.setDome(buildCoordinate);
        }
    }

    public boolean askIfBUildSecond(){
        //chiedere se si vuole costruire una seconda volta nello stesso posto a patto che non sia una cupola come da potere
        return buildSecond;
    }

}

