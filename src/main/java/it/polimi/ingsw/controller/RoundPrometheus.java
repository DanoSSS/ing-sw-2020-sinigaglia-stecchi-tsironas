package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.util.ArrayList;

public class RoundPrometheus extends Round {
    ArrayList<Coordinates> possibleBuilds,possibleMoves;

    public RoundPrometheus(Board board, Player player){
        super(board, player);
    }

    public ArrayList<Coordinates> canMoveAfterBuild(Worker worker) {
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

    public void activeWorkerSelectionPrometheus(int id){
        Worker firstActiveWorker = board.getWorkerById(id);
        possibleBuilds = canBuild(firstActiveWorker);
        possibleMoves = canMove(firstActiveWorker);
        if (possibleMoves.size() == 0) {
            if(player.getWorker1().getIdWorker()==id) {
                otherActiveWorker = player.getWorker2();
            }else {
                otherActiveWorker = player.getWorker1();
            }
            possibleBuilds = canBuild(otherActiveWorker);
            possibleMoves = canMove(otherActiveWorker);
            if (possibleMoves.size() == 0) {
                board.getObservableModel().notify(new ReturnMessage(5)); //da gestire la perdita
            }else{
                board.setCurrentActiveWorkerAndChoosePrometheus(otherActiveWorker, possibleMoves, possibleBuilds);
            }
        } else board.setCurrentActiveWorkerAndChoosePrometheus(firstActiveWorker, possibleMoves, possibleBuilds);
    }

    public void moveOrBuild(String input){
        boolean win= false;
        String[] inputAnswer = input.split(" ");
        String[] c = inputAnswer[1].split(",");
        Coordinates coordinates = new Coordinates(Integer.parseInt(c[0]),Integer.parseInt(c[1]));
        if(inputAnswer[0].equals("BUILD")){
            doBuild(coordinates);
            possibleMoves = canMoveAfterBuild(board.getCurrentActiveWorker());
            if(possibleMoves.size()==0){

            }
            board.buildBeforePrometheus(coordinates,possibleMoves);
        }else if(inputAnswer[0].equals("MOVE")){
            Coordinates oldC;
            int x=board.getCurrentActiveWorker().getCoordinates().getX();
            int y=board.getCurrentActiveWorker().getCoordinates().getY();
            oldC = new Coordinates(x,y);
            doMove(coordinates,win, board.getCurrentActiveWorker());
            possibleBuilds = canBuild(board.getCurrentActiveWorker());
            if(possibleBuilds.size()==0){

            }
            board.moveWorkerAndPossibleBuilds(oldC,coordinates,possibleBuilds,null);
        }
    }

    @Override
    public void update(Object message) {
        Action a = ((Message) message).getAction();
        switch (a){
            case SELECT_ACTIVE_WORKER:                //deve poter scegliere solo i suoi active worker
                int i = ((Message) message).getIdWorker();
                activeWorkerSelectionPrometheus(i);
                break;
            case PROMETHEUS_CHOOSE:
                String input = ((Message) message).getSentence();
                moveOrBuild(input);
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
