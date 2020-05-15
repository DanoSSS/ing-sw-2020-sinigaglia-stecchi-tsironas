package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoundPan {
    Player player1 = new Player("pippo", "RED", 1,2, God.PAN,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.PAN,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundPan TestRoundPan = new RoundPan(board,player1);

    @Test
    public void TestWinPan(){
        Coordinates coordinates00 = new Coordinates(0,0);
        Coordinates coordinates10 = new Coordinates(1,0);
        boolean gameover = false;
        TestRoundPan.doBuild(coordinates00);
        TestRoundPan.doBuild(coordinates00);
        board.moveWorker(coordinates00,worker1);
        gameover = TestRoundPan.doMove(coordinates10,gameover,worker1);
        assertTrue(gameover);     //test player if win game when he moves from a level 2 to level 0
        TestRoundPan.doBuild(coordinates00);
        board.moveWorker(coordinates00,worker1);
        gameover = TestRoundPan.doMove(coordinates10,gameover,worker1);
        assertTrue(gameover);     //test if player win game when he moves from a level 3 to level 0
        board.moveWorker(coordinates00,worker1);
        TestRoundPan.doBuild(coordinates10);
        gameover = TestRoundPan.doMove(coordinates10,gameover,worker1);
        assertTrue(gameover);      //test if player win game when he moves from a level 3 to level 1
        TestRoundPan.doBuild(coordinates10);
        gameover = TestRoundPan.doMove(coordinates00,gameover,worker1);
        assertTrue(gameover);        //test classical win move from level 2 to level 3
    }
}
