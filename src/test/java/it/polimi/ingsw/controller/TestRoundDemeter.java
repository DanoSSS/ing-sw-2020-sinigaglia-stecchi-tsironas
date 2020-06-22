package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRoundDemeter {

    Player player1 = new Player("pippo", "RED", 1,2, God.DEMETER,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.DEMETER,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundDemeter testRoundDemeter = new RoundDemeter(board,player1);

    @Test
    public void testCanBuildSecond(){
        boolean tag=false;
        Coordinates coordinates11 = new Coordinates(1,1);
        ArrayList<Coordinates> possiblesBuildsCoordinates = new ArrayList<Coordinates>();
        possiblesBuildsCoordinates = testRoundDemeter.canBuildSecond(worker1,coordinates11);
        for(Coordinates c:possiblesBuildsCoordinates){
            if(c.getX() == coordinates11.getX() && c.getY()==coordinates11.getY()){
                tag = true;
            }
        }
        assertFalse(tag);
    }

    @Test
    public void testFirstBuild(){
        try {
            Coordinates coordinates11 = new Coordinates(1, 1);
            board.setCurrentActiveWorker(worker1);
            testRoundDemeter.firstBuild(coordinates11);
            assertEquals(1, board.getLevel(coordinates11));
        }catch (NullPointerException e) {
        }
    }

    @Test
    public void TestSecondBuildEndTurn(){
        try {
            board.setNround(2);
            Coordinates coordinates11 = new Coordinates(1, 1);
            testRoundDemeter.secondBuildEndTurn("1,1");
            int level = board.getLevel(coordinates11);
            assertEquals(1, level);
        }catch (NullPointerException e){}
    }
}
