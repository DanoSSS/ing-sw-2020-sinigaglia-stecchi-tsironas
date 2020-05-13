package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class RoundArtemis extends Round {

    public RoundArtemis(Board board, Player player) {
        super(board, player);
    }


    public boolean ExecuteRound(boolean Gameover) {
        boolean gamestatus,moveSecond;
        int i;
        Worker activeWorker,newActiveWorker;
        ArrayList<Coordinates> possibleMoves,possibleBuilds;
        Coordinates moveCoordinates,buildCoordinates,oldCoordinates;
        activeWorker = askActiveWorker();
        int x=activeWorker.getCoordinates().getX();
        int y=activeWorker.getCoordinates().getY();
        oldCoordinates = new Coordinates(x,y);
        possibleMoves=canMove(activeWorker);        //arraylist of possible coordinates where worker can move(da passare alla view)
        if(possibleMoves.size()==0){
            do{
                newActiveWorker=askOtherWorker();
            }while(newActiveWorker!=activeWorker);
            activeWorker=newActiveWorker;
            x=activeWorker.getCoordinates().getX();
            y=activeWorker.getCoordinates().getY();
            oldCoordinates = new Coordinates(x,y);
            possibleMoves=canMove(activeWorker);
            if(possibleMoves.size()==0){
                return false;       // se ci sono due giocatori vince l'altro, se sono in tre eliminare il giocatore
            }
        }
        moveCoordinates=askCoordinatesToMove(possibleMoves);
        gamestatus=doMove(moveCoordinates,Gameover,activeWorker);
        moveSecond = askIfMoveSecond();
        if (moveSecond){
            possibleMoves=canMoveSecond(activeWorker,oldCoordinates);
            if(possibleMoves.size()==0){
                return false;       // se non si può muvoere far perdere o impedire di attivare il potere
            }
            moveCoordinates=askCoordinatesToMove(possibleMoves);
            gamestatus=doMove(moveCoordinates,Gameover,activeWorker);
        }
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





    public boolean askIfMoveSecond(){
        boolean moveSecond=true;
        //chiedere se si vuole muovere una seconda volta ma non nella posizione iniziale
        return moveSecond;
    }

    public ArrayList<Coordinates> canMoveSecond(Worker worker,Coordinates oldCoordinates) {
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
                        if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) <= 1 && !board.isOccupied(newCoordinates) && (newCoordinates.getX()!=oldCoordinates.getX() || newCoordinates.getY()!=oldCoordinates.getY())) {
                            possiblesMovesCoordinates.add(newCoordinates);
                        }
                    } else {
                        if (!board.isDome(newCoordinates) && (board.getLevel(newCoordinates) - board.getLevel(coordinates)) == 0 && !board.isOccupied(newCoordinates) && (newCoordinates.getX()!=oldCoordinates.getX() || newCoordinates.getY()!=oldCoordinates.getY())) {
                            possiblesMovesCoordinates.add(newCoordinates);
                        }
                    }
                }
            }
        }
        return possiblesMovesCoordinates;
    }


    @Override
    public void update(Object message) {

    }
}
