package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.util.ArrayList;

public abstract class Round implements Observer<Object>{
     protected Board board;
     protected Player player;
     protected Worker firstActiveWorker, otherActiveWorker;
     protected ArrayList<Coordinates> possibleMoves,possibleBuilds;
     int times=0;

    public Round(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public void doBuild(Coordinates buildCoordinate){
        board.setLevel(buildCoordinate);
        if(board.getLevel(buildCoordinate)==4) {
            board.setDome(buildCoordinate);
            if(board.getChronusPlayer()>0){
                chronusWin();
            }
        }
    }

    public void chronusWin(){
        int d=board.getNumberOfDome()+1;
        board.setNumberOfDome(d);
        if(d==5){
            board.getObservableModel().notify(new ReturnMessage(18,board.getChronusPlayer()));
        }
    }

    public boolean heraPower(Coordinates c){
        if((c.getX()==0 || c.getX()==4 || c.getY()==0 || c.getY()==4)&&board.getCurrentRound()!=board.getHeraPlayer()){
            return true;
        }else return false;
    }

    public boolean doMove(Coordinates moveCoordinates,boolean GameOver,Worker activeWorker){
        Coordinates oldCoordinates;
        int x=activeWorker.getCoordinates().getX();
        int y=activeWorker.getCoordinates().getY();
        oldCoordinates = new Coordinates(x,y);
        board.freeCellFromWorker(oldCoordinates);
        board.moveWorker(moveCoordinates,activeWorker);
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
        if(worker.getPlayer().getGod()== God.ZEUS && board.getLevel(worker.getCoordinates())<3){
            possiblesBuildsCoordinates.add(worker.getCoordinates());
        }
        return possiblesBuildsCoordinates;
    }

    public void activeWorkerSelection(int id) {
        firstActiveWorker = board.getWorkerById(id);
        possibleMoves = canMove(firstActiveWorker);
        if (possibleMoves.size() == 0) {
            if(player.getWorker1().getIdWorker()==id) {
                otherActiveWorker = player.getWorker2();
                possibleMoves = canMove(otherActiveWorker);
            }else {
                otherActiveWorker = player.getWorker1();
                possibleMoves =canMove(otherActiveWorker);
            }
            if (possibleMoves.size() == 0) {
                board.loseGame();
            }else{
                board.setCurrentActiveWorkerAndPossibleMoves(otherActiveWorker, possibleMoves);
            }
        } else board.setCurrentActiveWorkerAndPossibleMoves(firstActiveWorker, possibleMoves);
    }

    public void moveInCoordinate(Coordinates newC){
        boolean win= false;
        Worker oppositeWorker=null;
        Coordinates oldC;
        int x=board.getCurrentActiveWorker().getCoordinates().getX();
        int y=board.getCurrentActiveWorker().getCoordinates().getY();
        oldC = new Coordinates(x,y);
        if(board.isOccupied(newC)){
            oppositeWorker=board.getWorker(newC);
        }
        win=doMove(newC,win,board.getCurrentActiveWorker());
        if(win){
            board.winGame();
        }
        else {
            possibleBuilds = canBuild(board.getCurrentActiveWorker());
            if (possibleBuilds.size() == 0) {
                board.loseGame();
            } else board.moveWorkerAndPossibleBuilds(oldC, newC, possibleBuilds, oppositeWorker);
        }
    }

    public void buildInCoordinate(Coordinates c){
        int i;
        doBuild(c);
        if(board.getNround()!=0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        board.buildEndTurn(c);
    }

    public void  secondBuildEndTurn(String input){
        int i;
        Coordinates coordinates=null;
        if (!input.equals("NO")) {
            String[] c = input.split(",");
            coordinates = new Coordinates(Integer.parseInt(c[0]), Integer.parseInt(c[1]));
            doBuild(coordinates);
        }
        if(board.getNround()!=0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        board.buildEndTurn(coordinates);
    }

    @Override
    public void update(Object message) {
        Action a = ((Message) message).getAction();
        switch (a){
            case SELECT_ACTIVE_WORKER:                //deve poter scegliere solo i suoi active worker
                int i =  ((Message) message).getIdWorker();
                activeWorkerSelection(i);
                break;
            case SELECT_COORDINATE_MOVE:
                Coordinates moveC = ((Message) message).getCoordinates();
                moveInCoordinate(moveC);
                break;
            case MOVE_AND_COORDINATE_BUILD:
                Coordinates buildC = ((Message) message).getCoordinates();
                buildInCoordinate(buildC);
                break;
        }
    }

}
