package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoundAres {
    Player player1 = new Player("pippo", "RED", 1,2, God.APOLLO,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.APOLLO,2);
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
}
