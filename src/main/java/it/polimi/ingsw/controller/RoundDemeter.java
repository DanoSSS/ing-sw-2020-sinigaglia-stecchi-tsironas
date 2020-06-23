package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;

import java.util.ArrayList;

public class RoundDemeter extends Round {
    ArrayList<Coordinates> possibleBuilds;

    public RoundDemeter(Board board, Player player) {
        super(board, player);
    }

    /**
     * method that calculate possible cell in wich current worker can build a second time according Demeter power
     * @param worker
     * @param previousCoordinate
     * @return possibleBuildsCoordinates
     */
    public ArrayList<Coordinates> canBuildSecond(Worker worker,Coordinates previousCoordinate) {
        Coordinates coordinates, newCoordinates;
        int x, y;
        ArrayList<Coordinates> possiblesBuildsCoordinates = new ArrayList<>();
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

    /**
     * method that calls doBuild and calculate possibleBuilds for a possible second build
     * @param coordinates
     */
    public void firstBuild(Coordinates coordinates) {
        doBuild(coordinates);
        possibleBuilds = canBuildSecond(board.getCurrentActiveWorker(), coordinates);
        if (possibleBuilds.size() == 0) {
            board.loseGame();
        } else {
            board.firstBuildDemeterAndHestia(coordinates, possibleBuilds);
        }
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
                firstBuild(buildC);
                break;
            case FIRST_BUILD_DEMETER:
                String input = ((Message) message).getSentence();
                secondBuildEndTurn(input);
        }
    }
}
