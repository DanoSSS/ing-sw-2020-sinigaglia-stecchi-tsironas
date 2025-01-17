package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;


public class RoundEphaestus extends Round {

    public RoundEphaestus(Board board, Player player) {
        super(board, player);
    }

    /**
     * method that according the input build one or two time in the same cell
     * @param input
     */
    public void buildSecond(String input){
        int i;
        String[] inputAnswer = input.split(" ");
        String[] c = inputAnswer[1].split(",");
        Coordinates coordinates = new Coordinates(Integer.parseInt(c[0]),Integer.parseInt(c[1]));
        if(inputAnswer[0].equals("yes")){
            doBuild(coordinates);
            if(board.getLevel(coordinates)<3){
                    board.setLevel(coordinates);
            }else{
                board.loseGame();
            }
        }else if(inputAnswer[0].equals("no")){
            doBuild(coordinates);
        }
        if(board.getNround()!=0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        board.buildEndTurn(coordinates);
    }

    /**
     * update from remote view
     * @param message
     */
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
            case BUILD_EPHAESTUS:
                String input = ((Message) message).getSentence();
                buildSecond(input);
                break;
        }
    }

}

