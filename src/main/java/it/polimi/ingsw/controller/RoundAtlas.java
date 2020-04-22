package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

public class RoundAtlas implements Round {
    public Board board;
    public Player player;

    public RoundAtlas(Board board, Player player){
        this.board = board;
        this.player = player;
    }

    @Override
    public boolean ExecuteRound(boolean Gameover) {
        boolean gamestatus,domePower;
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
        domePower=askDomeOrNot();
        doBuild(buildCoordinates,domePower);
        if(board.getNround()!=0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        return gamestatus;
    }

    public Worker askActiveWorker() {
        Worker worker=null;
        //chiede alla view di selezionare un worker
        return worker;
    }

    public Worker askOtherWorker() {
        Worker worker=null;
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
        Coordinates coordinates=null;
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
        Coordinates coordinates=null;
        //dare alla view arraylist e chiedere al player dove vuole costruire se non si può passare da controller si fa
        //un currentlypossiblebuild in board
        return coordinates;
    }

    public boolean askDomeOrNot(){
        boolean dome=true;
        //chiedere alla view se si vuole costruire un dome usando il potere a prescindere dal livello
        return dome;
    }

    public void doBuild(Coordinates buildCoordinate,boolean domePower) {
        if (domePower) {
            board.setDome(buildCoordinate);                               //serve anche settare il livelloa 4 o basta così??
        } else {
            board.setLevel(buildCoordinate);
            if (board.getLevel(buildCoordinate) == 4) {
                board.setDome(buildCoordinate);
            }
        }
    }

}

