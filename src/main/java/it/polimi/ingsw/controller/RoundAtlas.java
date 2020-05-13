package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

public class RoundAtlas extends Round {


    public RoundAtlas(Board board, Player player){
        super(board, player);
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

    @Override
    public void update(Object message) {

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

