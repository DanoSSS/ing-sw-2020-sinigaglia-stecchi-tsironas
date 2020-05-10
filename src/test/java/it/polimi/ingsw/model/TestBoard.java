package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class TestBoard {
    Player player1 = new Player("pippo", "RED", 1,2, God.APOLLO);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.APOLLO);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Board board = new Board(worker1,worker2,worker3,worker4,2);



    @Test
    public void TestSetDome_IsDome(){
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates11 = new Coordinates(1,1);
        board.setDome(coordinates21);
        assertTrue(board.isDome(coordinates21));
        assertFalse(board.isDome(coordinates11));
    }

    @Test
    public void TestMoveWorker_IsOccupied(){
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates11 = new Coordinates(1,1);
        board.moveWorker(coordinates11,worker1);
        assertTrue(board.isOccupied(coordinates11));
        assertFalse(board.isOccupied(coordinates21));
    }

    @Test
    public void TestGetWorker_FreeCellFromWorker(){
        Coordinates coordinates11 = new Coordinates(1,1);
        board.moveWorker(coordinates11,worker1);
        assertEquals(worker1,board.getWorker(coordinates11));
        board.freeCellFromWorker(coordinates11);
        assertEquals(null,board.getWorker(coordinates11));
    }

    @Test
    public void TestSetLevel_GetLevel(){
        Coordinates coordinates11 = new Coordinates(1,1);
        assertEquals(0,board.getLevel(coordinates11));
        board.setLevel(coordinates11);
        assertEquals(1,board.getLevel(coordinates11));
        board.setLevel(coordinates11);
        assertEquals(2,board.getLevel(coordinates11));
        board.setLevel(coordinates11);
        assertEquals(3,board.getLevel(coordinates11));
        board.setLevel(coordinates11);
        assertEquals(4,board.getLevel(coordinates11));
    }
}
