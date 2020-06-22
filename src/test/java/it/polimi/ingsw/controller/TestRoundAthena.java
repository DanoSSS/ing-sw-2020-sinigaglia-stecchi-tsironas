package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoundAthena {
    Player player1 = new Player("pippo", "RED", 1,2, God.ATHENA,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.ATHENA,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundAthena TestRoundAthena = new RoundAthena(board,player1);

    @Test
    public void TestCanMove(){
        boolean tag = false;
        boolean tag2 = false;
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates32 = new Coordinates(3,2);
        Coordinates coordinates00 = new Coordinates(0,0);
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
        board.moveWorker(coordinates21,worker1);
        possiblesMovesCoordinates = TestRoundAthena.canMove(worker1);
        for(Coordinates c:possiblesMovesCoordinates){
            if(c.getX() == coordinates32.getX() && c.getY()==coordinates32.getY()){
                tag = true;
            }
            if(c.getX() == coordinates00.getX() && c.getY()==coordinates00.getY()) {
                tag2 = true;
            }
        }
        assertTrue(tag);
        assertFalse(tag2);
    }

    @Test
    public void TestDoMove(){
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates32 = new Coordinates(3,2);
        TestRoundAthena.doBuild(coordinates21);
        TestRoundAthena.doBuild(coordinates21);
        TestRoundAthena.doBuild(coordinates32);
        TestRoundAthena.doBuild(coordinates32);
        TestRoundAthena.doBuild(coordinates32);
        board.moveWorker(coordinates21,worker1);
        TestRoundAthena.doMove(coordinates32,false,worker1);
        assertEquals(board.getWorker(coordinates32),worker1);
    }
}
