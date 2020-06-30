package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;

public class RoundAres extends Round {
    public RoundAres(Board board, Player player) {
        super(board, player);
    }

    /**
     * methiod that make a sample build with no end turn
     * @param c
     */
    public void buildInCoordinateAres(Coordinates c){
        int i;
        doBuild(c);
        board.buildAres(c);
    }

    /**
     * method that analyzes input and reduce level of coordinate's cell given with the input or not then end turn
     * @param input
     */
    public void aresPowerThenEndTurn(String input){
        Coordinates coordinates=null;
        boolean correctInput=false;
        int i;
        if(!input.equals("NO")) {
            String[] c = input.split(",");
            try {
                coordinates = new Coordinates(Integer.parseInt(c[0]), Integer.parseInt(c[1]));
                correctInput = true;
            } catch (NullPointerException | NumberFormatException e) {
                correctInput = false;
            }
            if (correctInput) {
                board.reduceLevel(coordinates);
            }
        }
        if (board.getNround() != 0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        board.aresEndTurn(coordinates);
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
            case MOVE_AND_COORDINATE_BUILD:
                Coordinates buildC = ((Message) message).getCoordinates();
                buildInCoordinateAres(buildC);
                break;
            case ARES_POWER:
                String input = ((Message) message).getSentence();
                aresPowerThenEndTurn(input);
        }
    }
}
