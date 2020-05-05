package it.polimi.ingsw.testcontroller;

import it.polimi.ingsw.controller.RoundMinotaur;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoundMinotaur {
    Player player1 = new Player("pippo", "RED", 1,2, God.MINOTAUR);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.MINOTAUR);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Board board = new Board(worker1,worker2,worker3,worker4,2);
    RoundMinotaur TestRoundMinotaur = new RoundMinotaur(board,player1);

    @Test
    public void TestMinotaurPower(){
        Coordinates coordinates13 = new Coordinates(1,3);
        Coordinates coordinates03 = new Coordinates(0,3);
        Coordinates coordinates22 = new Coordinates(2,2);
        Coordinates coordinates31 = new Coordinates(3,1);
        board.moveWorker(coordinates13,worker1);
        board.moveWorker(coordinates03,worker3);
        board.moveWorker(coordinates22,worker4);
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
        boolean tag = false;
        boolean tag2 = false;
        boolean gameover = false;
        possiblesMovesCoordinates = TestRoundMinotaur.canMove(worker1);
        for(Coordinates c:possiblesMovesCoordinates){
            if(c.getX() == coordinates03.getX() && c.getY()==coordinates03.getY()){
                tag = true;
            }
            if(c.getX() == coordinates22.getX() && c.getY()==coordinates22.getY()) {
                tag2 = true;
            }
        }
        assertFalse(tag);    //test if player can move a worker in a cell where there is other player's worker
        assertTrue(tag2);    //test if player can move a worker in a cell where there is an opponent's worker
        TestRoundMinotaur.doMove(coordinates22,gameover,worker1);
        assertTrue(board.isOccupied(coordinates22));
        assertTrue(board.isOccupied(coordinates31));    //test forcing worker in his back cell
        assertEquals(worker1,board.getWorker(coordinates22));
        assertEquals(worker4,board.getWorker(coordinates31));
        Coordinates coordinatesWorker1 = worker1.getCoordinates();
        int x=coordinatesWorker1.getX();
        int y=coordinatesWorker1.getY();
        assertEquals(2,x);
        assertEquals(2,y);
        Coordinates coordinatesWorker4 = worker4.getCoordinates();
        int a=coordinatesWorker4.getX();
        int b=coordinatesWorker4.getY();
        assertEquals(3,a);
        assertEquals(1,b);
        assertFalse(board.isOccupied(coordinates13));   //test initial cell free
        assertNull(board.getWorker(coordinates13));
    }

}
