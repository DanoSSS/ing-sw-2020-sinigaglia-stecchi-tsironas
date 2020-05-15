package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRoundArtemis {
    Player player1 = new Player("pippo", "RED", 1,2, God.APOLLO,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.APOLLO,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundArtemis TestRoundArtemis = new RoundArtemis(board,player1);

    @Test
    public void testSecondMovePower(){
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates22 = new Coordinates(2,2);
        Coordinates coordinates23 = new Coordinates(2,3);
        board.moveWorker(coordinates21,worker1);
        int x=worker1.getCoordinates().getX();
        int y=worker1.getCoordinates().getY();
        Coordinates oldCoordinates = new Coordinates(x,y);
        boolean gameover = false;
        boolean tag = false;
        boolean tag1 = false;
        TestRoundArtemis.doMove(coordinates22,gameover,worker1);
        int x1=worker1.getCoordinates().getX();
        int y1=worker1.getCoordinates().getY();
        assertEquals(2,x1);
        assertEquals(2,y1);
        possiblesMovesCoordinates = TestRoundArtemis.canMoveSecond(worker1,coordinates21);
        for(Coordinates c:possiblesMovesCoordinates){
            System.out.println(c.getX()+" "+c.getY());
            if(c.getX() == coordinates21.getX() && c.getY()==coordinates21.getY()){
                tag = true;
            }
            if(c.getX() == coordinates23.getX() && c.getY()==coordinates23.getY()) {
                tag1 = true;
            }
        }
        assertFalse(tag);
        assertTrue(tag1);
    }
}
