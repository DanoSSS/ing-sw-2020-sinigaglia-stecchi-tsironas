package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

public class RoundDemeter extends Round {

    public RoundDemeter(Board board, Player player) {
        super(board, player);
    }

    @Override
    public boolean ExecuteRound(boolean Gameover) {
        boolean gamestatus,secondBuild;
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
        secondBuild=askIfBuildSecond();
        if(secondBuild){
            possibleBuilds = canBuildSecond(activeWorker,buildCoordinates);
            if(possibleBuilds.size()==0){
                return false;       // se non può costruire si fa perdere il giocatore o non si fa fare la build
            }
            buildCoordinates = askCoordinatesToBuild(possibleBuilds);
            doBuild(buildCoordinates);
        }
        if(board.getNround()!=0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        return gamestatus;
    }

    @Override
    public void update(Object message) {

    }





    public boolean askIfBuildSecond(){
        boolean buildSecond=true;
        //chiede se vuole costruire una seconda volta ma non nello stesso spazio
        return buildSecond;
    }

    public ArrayList<Coordinates> canBuildSecond(Worker worker,Coordinates previousCoordinate) {
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
                    if (!board.isOccupied(newCoordinates) && !board.isDome(newCoordinates) && (newCoordinates.getX()!=previousCoordinate.getX() || newCoordinates.getY()!=previousCoordinate.getY())) {
                        possiblesBuildsCoordinates.add(newCoordinates);
                    }
                }
            }
        }
        return possiblesBuildsCoordinates;
    }
}
