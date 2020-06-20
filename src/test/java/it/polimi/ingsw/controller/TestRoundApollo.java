package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestRoundApollo {
    Player player1 = new Player("pippo", "RED", 1,2, God.APOLLO,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.APOLLO,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundApollo TestRoundApollo = new RoundApollo(board,player1);



    @Test
    public void TestDoBuild() {
        Coordinates coordinates = new Coordinates(2,1);
        int i;
        boolean isDome;
        TestRoundApollo.doBuild(coordinates);
        i = board.getLevel(coordinates);
        assertEquals(1,i);
        TestRoundApollo.doBuild(coordinates);
        TestRoundApollo.doBuild(coordinates);
        TestRoundApollo.doBuild(coordinates);
        isDome = board.isDome(coordinates);
        assertTrue(isDome);
    }

    @Test
    public void TestIsDome(){
        Coordinates coordinates = new Coordinates(2,1);
        boolean isDome;
        isDome = board.isDome(coordinates);
        assertFalse(isDome);
    }

    @Test
    public void TestDoMove(){
        Coordinates coordinates11 = new Coordinates(1,1);
        board.moveWorker(coordinates11,worker1);
        boolean Gameover=false;
        Coordinates coordinates21 = new Coordinates(2,1);
        assertTrue(board.isOccupied(coordinates11));
        assertEquals(board.getWorker(coordinates11),worker1);
        TestRoundApollo.doMove(coordinates21,Gameover,worker1);
        assertFalse(board.isOccupied(coordinates11));
        assertTrue(board.isOccupied(coordinates21));
        Coordinates coordinatesWorker = worker1.getCoordinates();
        int x=coordinatesWorker.getX();
        int y=coordinatesWorker.getY();
        assertEquals(2,x);
        assertEquals(1,y);
        assertEquals(worker1,board.getWorker(coordinates21));
    }

    @Test
    public void TestDoMoveWithHeraPower(){
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates31 = new Coordinates(3,1);
        board.setHeraPlayer(2);
        TestRoundApollo.doBuild(coordinates21);
        TestRoundApollo.doBuild(coordinates21);
        TestRoundApollo.doBuild(coordinates31);
        TestRoundApollo.doBuild(coordinates31);
        TestRoundApollo.doBuild(coordinates31);
        TestRoundApollo.doMove(coordinates21,false,worker1);
        boolean gameover = TestRoundApollo.doMove(coordinates31,false,worker1);
        assertTrue(gameover);
    }

    @Test
    public void TestChangeWorker(){
        Coordinates coordinates11 = new Coordinates(1,1);
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates32 = new Coordinates(3,2);
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
        boolean tag = false;
        boolean tag2 = false;
        boolean gameover = false;
        board.moveWorker(coordinates21,worker1);
        board.moveWorker(coordinates32,worker2);
        board.moveWorker(coordinates11,worker3);
        possiblesMovesCoordinates = TestRoundApollo.canMove(worker1);
        for(Coordinates c:possiblesMovesCoordinates){
            if(c.getX() == coordinates32.getX() && c.getY()==coordinates32.getY()){
                tag = true;
            }
            if(c.getX() == coordinates11.getX() && c.getY()==coordinates11.getY()) {
                tag2 = true;
            }
        }
        assertFalse(tag);
        assertTrue(tag2);
        TestRoundApollo.doMove(coordinates11,gameover,worker1);
        assertTrue(board.isOccupied(coordinates11));
        assertTrue(board.isOccupied(coordinates21));
        assertEquals(worker1,board.getWorker(coordinates11));
        assertEquals(worker3,board.getWorker(coordinates21));
        Coordinates coordinatesWorker1 = worker1.getCoordinates();
        int x=coordinatesWorker1.getX();
        int y=coordinatesWorker1.getY();
        assertEquals(1,x);
        assertEquals(1,y);
        Coordinates coordinatesWorker3 = worker3.getCoordinates();
        int a=coordinatesWorker3.getX();
        int b=coordinatesWorker3.getY();
        assertEquals(2,a);
        assertEquals(1,b);
    }

    @Test
    public void TestWin(){
        boolean gameover = false;
        Coordinates coordinates11 = new Coordinates(1,1);
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates31 = new Coordinates(3,1);
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
        boolean tag = false;
        TestRoundApollo.doBuild(coordinates11);
        TestRoundApollo.doBuild(coordinates11);
        TestRoundApollo.doBuild(coordinates11);
        TestRoundApollo.doBuild(coordinates21);
        TestRoundApollo.doBuild(coordinates21);
        TestRoundApollo.doMove(coordinates31,gameover,worker1);
        possiblesMovesCoordinates = TestRoundApollo.canMove(worker1);
        for(Coordinates c:possiblesMovesCoordinates) {
            if (c.getX() == coordinates21.getX() && c.getY() == coordinates21.getY()) {
                tag = true;
            }
        }
        assertFalse(tag);
        TestRoundApollo.doMove(coordinates21,gameover,worker1);
        gameover=TestRoundApollo.doMove(coordinates11,gameover,worker1);
        assertTrue(gameover);
    }

    @Test
    public void TestAthenaPowerActive(){
        board.setNround(2);
        boolean gameover = false;
        Coordinates coordinates11 = new Coordinates(1,1);
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates12 = new Coordinates(1,2);
        TestRoundApollo.doMove(coordinates11,gameover,worker1);
        TestRoundApollo.doBuild(coordinates21);
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
        boolean tag = false;
        boolean tag2 = false;
        possiblesMovesCoordinates = TestRoundApollo.canMove(worker1);
        for(Coordinates c:possiblesMovesCoordinates) {
            if (c.getX() == coordinates21.getX() && c.getY() == coordinates21.getY()) {
                tag = true;
            }
            if (c.getX() == coordinates12.getX() && c.getY() == coordinates12.getY()) {
                tag2 = true;
            }
        }
        assertFalse(tag);
        assertTrue(tag2);
    }

}
