package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.util.ArrayList;

public class RoundArtemis extends Round {
    private Coordinates oldCoordinate;

    public RoundArtemis(Board board, Player player) {
        super(board, player);
    }

    public ArrayList<Coordinates> canMoveSecond(Worker worker,Coordinates oldCoordinates) {
        Coordinates coordinates, newCoordinates;
        int x, y;
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<>();
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

    public void firstMoveArtemis(Coordinates newC) {
        boolean win = false;
        int x=board.getCurrentActiveWorker().getCoordinates().getX();
        int y=board.getCurrentActiveWorker().getCoordinates().getY();
        oldCoordinate = new Coordinates(x,y);
        win = doMove(newC, win, board.getCurrentActiveWorker());
        if (win) {
            board.winGame();
        } else {
            ArrayList<Coordinates> secondPossibleMove = canMoveSecond(board.getCurrentActiveWorker(), oldCoordinate);
            board.notify(new ReturnMessage(9,oldCoordinate, newC, board.getCurrentActiveWorker().getIdWorker(), secondPossibleMove));
        }
    }

    public void possibleBuildsArtemis(String answer){
        boolean win = false;
        Coordinates coordinates=null;
        ArrayList<Coordinates> possibleBuilds=null;
        if(answer.equals("NO")){
            possibleBuilds = canBuild(board.getCurrentActiveWorker());
            if(possibleBuilds.size()==0){
                board.loseGame();
            }
        }else{
            String[] input = answer.split(",");
            coordinates = new Coordinates(Integer.parseInt(input[0]),Integer.parseInt(input[1]));
            int x=board.getCurrentActiveWorker().getCoordinates().getX();
            int y=board.getCurrentActiveWorker().getCoordinates().getY();
            oldCoordinate = new Coordinates(x,y);
            win=doMove(coordinates,win,board.getCurrentActiveWorker());
            if(win){
                board.winGame();
            }
            else{
                possibleBuilds = canBuild(board.getCurrentActiveWorker());
                if(possibleBuilds.size()==0){
                    board.loseGame();
                }
            }
        }
        board.notify(new ReturnMessage(10,board.getCurrentActiveWorker().getIdWorker(),oldCoordinate,coordinates,possibleBuilds,board.getCurrentRound(),null));
    }

    @Override
    public void update(Object message) {
        Action a = ((Message) message).getAction();
        switch (a) {
            case SELECT_ACTIVE_WORKER:                //deve poter scegliere solo i suoi active worker
                int i = ((Message) message).getIdWorker();
                activeWorkerSelection(i);
                break;
            case SELECT_COORDINATE_MOVE:
                Coordinates moveC = ((Message) message).getCoordinates();
                firstMoveArtemis(moveC);
                break;
            case ARTEMIS_FIRST_MOVE:
                String answer = ((Message) message).getSentence();
                possibleBuildsArtemis(answer);
                break;
            case MOVE_AND_COORDINATE_BUILD:
                Coordinates buildC = ((Message) message).getCoordinates();
                buildInCoordinate(buildC);
                break;
        }
    }

}
