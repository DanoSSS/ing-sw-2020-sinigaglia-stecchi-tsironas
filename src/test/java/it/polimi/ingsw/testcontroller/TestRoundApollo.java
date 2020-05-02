package it.polimi.ingsw.testcontroller;

import it.polimi.ingsw.controller.RoundApollo;
import it.polimi.ingsw.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

public class TestRoundApollo {


    @Test
    public void TestDoBuild() {
        Board board=new Board();
        Player player = new Player();
        Coordinates coordinates = new Coordinates(2,1);
        int i;
        boolean isDome;
        RoundApollo TestDoBuild = new RoundApollo(board,player);
        TestDoBuild.doBuild(coordinates);
        i = board.getLevel(coordinates);
        assertEquals(1,i);
        TestDoBuild.doBuild(coordinates);
        TestDoBuild.doBuild(coordinates);
        TestDoBuild.doBuild(coordinates);
        isDome = board.isDome(coordinates);
        assertTrue(isDome);
    }

    @Test
    public void TestDoMove(){
        Board board = new Board();
        Player player = new Player();
        Worker worker = new Worker();
        worker.setCell(1,1);
        board =  board.Board(worker);
        boolean Gameover=false;
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates11 = new Coordinates(1,1);
        RoundApollo TestDoMove = new RoundApollo(board,player);
        assertTrue(board.isOccupied(coordinates11));
        assertEquals(board.getWorker(coordinates11),worker);
        TestDoMove.doMove(coordinates21,Gameover,worker);
        assertTrue(!board.isOccupied(coordinates11));
        assertTrue(board.isOccupied(coordinates21));
        Coordinates coordinatesWorker = worker.getCoordinates();
        int x=coordinatesWorker.getX();
        int y=coordinatesWorker.getY();
        assertEquals(2,x);
        assertEquals(1,y);
        assertEquals(board.getWorker(coordinates21),worker);
    }

}
