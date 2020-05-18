package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

public class RoundPrometheus extends Round {

    public RoundPrometheus(Board board, Player player){
        super(board, player);
    }

    public boolean ExecuteRound(boolean Gameover) {
        boolean gamestatus,buildBefore;
        int i;
        Worker activeWorker,newActiveWorker;
        ArrayList<Coordinates> possibleMoves,possibleBuilds;
        Coordinates moveCoordinates,buildCoordinates;
        activeWorker = askActiveWorker();
        buildBefore = askIfWantBuildBefore();
        if(buildBefore){
            possibleBuilds =  canBuild(activeWorker);        //arraylist of possible coordinates where worker can build(da passare alla view)
            if(possibleBuilds.size()==0){
                do {
                    newActiveWorker = askOtherWorker();
                } while (newActiveWorker != activeWorker);
                activeWorker = newActiveWorker;
                possibleBuilds = canBuild(activeWorker);        //arraylist of possible coordinates where worker can build(da passare alla view)
                if(possibleBuilds.size()==0){
                    return false;        //bisogna vedere se il giocatore perde in questo caso, se non perde mettere i controlli prima dell if(buildBefore))
                }
            }
            buildCoordinates = askCoordinatesToBuild(possibleBuilds);
            doBuild(buildCoordinates);
            possibleMoves=canMoveAfterBuild(activeWorker,buildBefore);
            if(possibleMoves.size()==0){
                return false;       // se non ci sono mosse possibili dopo costruzione speciale il giocatore perde??
            }
        }
        else {
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
        }
        moveCoordinates=askCoordinatesToMove(possibleMoves);
        gamestatus=doMove(moveCoordinates,Gameover,activeWorker);
        possibleBuilds = canBuild(activeWorker);        //arraylist of possible coordinates where worker can build(da passare alla view)
        if(possibleBuilds.size()==0){
            return false;       // se ci sono due giocatori vince l'altro, se sono in tre eliminare il giocatore
        }
        buildCoordinates = askCoordinatesToBuild(possibleBuilds);
        doBuild(buildCoordinates);
        if(board.getNround()!=0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        return gamestatus;
    }

    public boolean askIfWantBuildBefore(){
        boolean buildBefore=true;
        //chiede se vuole costruire prima di muovere sapendo di non poter salire
        return buildBefore;
    }

    public ArrayList<Coordinates> canMoveAfterBuild(Worker worker,boolean buildBefore) {
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
                    if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) == 0 && !board.isOccupied(newCoordinates)) {
                        possiblesMovesCoordinates.add(newCoordinates);
                    }
                }
            }
        }
        return possiblesMovesCoordinates;
    }











}
