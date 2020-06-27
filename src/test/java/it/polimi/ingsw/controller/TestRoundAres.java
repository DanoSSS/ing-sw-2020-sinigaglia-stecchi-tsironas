package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoundAres {
    Player player1 = new Player("pippo", "RED", 1,2, God.ARES,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.ARES,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundAres TestRoundAres = new RoundAres(board,player1);


    @Test
    public void TestAresPowerThenEndTurn1(){
        try {
            Coordinates coordinates = new Coordinates(2, 1);
            TestRoundAres.doBuild(coordinates);
            TestRoundAres.aresPowerThenEndTurn("NO");
            int i = board.getLevel(coordinates);
            assertEquals(1, i);
            TestRoundAres.aresPowerThenEndTurn("2,1");
            i = board.getLevel(coordinates);
            assertEquals(0, i);
        }catch (NullPointerException e){}
    }
    @Test
    public void TestAresPowerThenEndTurn2(){
        try{
            Coordinates coordinates = new Coordinates(2, 1);
            board.setNround(1);
            TestRoundAres.doBuild(coordinates);
            TestRoundAres.aresPowerThenEndTurn("2,1");
            int i = board.getLevel(coordinates);
            assertEquals(0, i);
            assertEquals(0,board.getNround());
        }catch(NullPointerException e){}
    }

    @Test
    public void TestBuildInCoordinateAres(){
        try {
            Coordinates coordinates21 = new Coordinates(2, 1);
            TestRoundAres.buildInCoordinateAres(coordinates21);
            assertEquals(1,board.getLevel(coordinates21));
        }catch (NullPointerException e){}

    }

    @Test
    public void TestUpdateActiveWorker(){
        try{
            Message message = new Message(1,0);
            TestRoundAres.update(message);
            assertEquals(board.getCurrentActiveWorker(),worker1);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateSelectCoordinateMove(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(6,coordinates11);
            TestRoundAres.update(message);
            assertEquals(worker1,board.getWorker(coordinates11));
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateMoveAndCoordinateBuild(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(7,coordinates11);
            TestRoundAres.update(message);
            assertEquals(1,board.getLevel(coordinates11));
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateAresPower(){
        try{
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(30,"NO");
            TestRoundAres.update(message);
            assertEquals(0,board.getLevel(coordinates11));
            board.setLevel(coordinates11);
            assertEquals(1,board.getLevel(coordinates11));
            message = new Message(30,"1,1");
            TestRoundAres.update(message);
            assertEquals(0,board.getLevel(coordinates11));
        }catch (NullPointerException e){}
    }
}
