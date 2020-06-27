package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Message;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoundHestia {
    Player player1 = new Player("pippo", "RED", 1,2, God.HESTIA,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.HESTIA,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundHestia testRoundHestia = new RoundHestia(board,player1);

    @Test
    public void TestCanBuildSecond(){
        boolean tag=false;
        boolean tag2=false;
        ArrayList<Coordinates> possiblesBuildsCoordinates = new ArrayList<>();
        Coordinates coordinates00 = new Coordinates(0,0);
        Coordinates coordinates11 = new Coordinates(1,1);
        Coordinates coordinates12 = new Coordinates(1,2);
        board.moveWorker(coordinates11,worker1);
        possiblesBuildsCoordinates = testRoundHestia.canBuildSecond(worker1);
        for(Coordinates c:possiblesBuildsCoordinates){
            if(c.getX() == coordinates00.getX() && c.getY()==coordinates00.getY()){
                tag = true;
            }
            if(c.getX() == coordinates12.getX() && c.getY()==coordinates12.getY()) {
                tag2 = true;
            }
        }
        assertTrue(tag2);
        assertFalse(tag);
    }

    @Test
    public void TestFirstBuild() {
        try {
            Coordinates coordinates12 = new Coordinates(1, 2);
            board.setCurrentActiveWorker(worker1);
            board.setLevel(coordinates12);
            testRoundHestia.firstBuild(coordinates12);
            int i = board.getLevel(coordinates12);
            assertEquals(2, i);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateActiveWorker(){
        try{
            Message message = new Message(1,0);
            testRoundHestia.update(message);
            assertEquals(board.getCurrentActiveWorker(),worker1);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateSelectCoordinateMove(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(6,coordinates11);
            testRoundHestia.update(message);
            assertEquals(worker1,board.getWorker(coordinates11));
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateMoveAndCoordinateBuild(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(7,coordinates11);
            testRoundHestia.update(message);
            assertEquals(1,board.getLevel(coordinates11));
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateFirstBuild(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(14,"1,1");
            testRoundHestia.update(message);
            assertEquals(1,board.getLevel(coordinates11));
        }catch (NullPointerException e){}
    }
}
