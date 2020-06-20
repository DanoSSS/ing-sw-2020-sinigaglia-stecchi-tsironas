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

    public void buildInCoordinateAres(Coordinates c){
        int i;
        doBuild(c);
        board.buildAres(c);
    }

    public void aresPowerThenEndTurn(String input){
        Coordinates coordinates=null;
        int i;
        if(!input.equals("NO")){
            String[] c = input.split(",");
            coordinates = new Coordinates(Integer.parseInt(c[0]),Integer.parseInt(c[1]));
            board.reduceLevel(coordinates);
        }
        if(board.getNround()!=0) {
            i = board.getNround();
            i--;
            board.setNround(i);
        }
        board.aresEndTurn(coordinates); }


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
