package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestRoundAtlas {
    Player player1 = new Player("pippo", "RED", 1,2, God.ATLAS,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.ATLAS,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundAtlas testRoundAtlas = new RoundAtlas(board,player1);

    @Test
    public void TestDobuild(){
        Coordinates c1 = new Coordinates(1,1);
        testRoundAtlas.doBuild(c1, true);
        assertTrue(board.isDome(c1));
        Coordinates c2 = new Coordinates(2,2);
        testRoundAtlas.doBuild(c2, false);
        assertFalse(board.isDome(c2));
    }

    @Test
    public void TestDoBuildDome(){
        Coordinates c1 = new Coordinates(1,1);
        board.setChronusPlayer(2);
        board.setLevel(c1);
        board.setLevel(c1);
        board.setLevel(c1);
        testRoundAtlas.doBuild(c1,false);
        boolean isDome=board.isDome(c1);
        assertTrue(isDome);
    }

    @Test
    public void TestBuildDomeOrNotWithDome(){
        try {
            Coordinates c1 = new Coordinates(1, 1);
            testRoundAtlas.buildDomeorNot("dome 1,1");
            boolean isDome = board.isDome(c1);
            assertTrue(isDome);
        }catch(NullPointerException e){}
    }

    @Test
    public void TestBuildDomeOrNotStdLevel(){
        try {
            Coordinates c1 = new Coordinates(1, 1);
            board.setNround(1);
            testRoundAtlas.buildDomeorNot("std 1,1");
            int i = board.getLevel(c1);
            assertEquals(1,i);
        }catch(NullPointerException e){}
    }

    @Test
    public void TestMoveInCoordinate(){
        try {
            Coordinates c1 = new Coordinates(1, 1);
            board.setCurrentActiveWorker(worker1);
            testRoundAtlas.moveInCoordinate(c1);
            assertEquals(worker1, board.getWorker(c1));
        }catch (NullPointerException e){}
    }

    @Test
    public void TestBuildInCoordinateWithAthenaPower(){
        try{
            Coordinates c1 = new Coordinates(1, 1);
            board.setNround(1);
            testRoundAtlas.buildInCoordinate(c1);
            int level = board.getLevel(c1);
            assertEquals(1,level);
        }catch (NullPointerException e){}
    }
    @Test
    public void TestUpdateActiveWorker(){
        try{
            Message message = new Message(1,0);
            testRoundAtlas.update(message);
            assertEquals(board.getCurrentActiveWorker(),worker1);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateSelectCoordinateMove(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(6,coordinates11);
            testRoundAtlas.update(message);
            assertEquals(worker1,board.getWorker(coordinates11));
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateMoveAndCoordinateBuild(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(7,coordinates11);
            testRoundAtlas.update(message);
            assertEquals(1,board.getLevel(coordinates11));
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateBuildAtlas(){
        try{
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(11,"dome 1,1");
            testRoundAtlas.update(message);
            assertTrue(board.isDome(coordinates11));
        }catch (NullPointerException e){}
    }
}
