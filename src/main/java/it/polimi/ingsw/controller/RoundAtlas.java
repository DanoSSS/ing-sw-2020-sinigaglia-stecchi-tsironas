package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;


public class RoundAtlas extends Round {

    public RoundAtlas(Board board, Player player){
        super(board, player);
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

    public void buildDomeorNot(String input){
        boolean domePower=false;
        int i;
        String[] inputAnswer = input.split(" ");
        String[] sC = inputAnswer[1].split(",");
        Coordinates coordinates = new Coordinates(Integer.parseInt(sC[0]),Integer.parseInt(sC[1]));
        if(inputAnswer[0].equals("dome")){
            domePower = true;
            doBuild(coordinates,domePower);
        }else if(inputAnswer[0].equals("std")){
            domePower = false;
            doBuild(coordinates,domePower);
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
            case BUILD_ATLAS:
                String input = ((Message) message).getSentence();
                buildDomeorNot(input);
                break;
        }
    }

}

