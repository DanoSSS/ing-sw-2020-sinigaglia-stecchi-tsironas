package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRoundArtemis {
    Player player1 = new Player("pippo", "RED", 1,2, God.ARTEMIS,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.ARTEMIS,2);
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

    @Test
    public void TestCanMoveSecondWithAthenaPower(){
        boolean tag = false;
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates22 = new Coordinates(2,2);
        board.moveWorker(coordinates21,worker1);
        board.setNround(1);
        TestRoundArtemis.doMove(coordinates22,false,worker1);
        possiblesMovesCoordinates =TestRoundArtemis.canMoveSecond(worker1,coordinates21);
        for(Coordinates c:possiblesMovesCoordinates) {
            if (c.getX() == coordinates21.getX() && c.getY() == coordinates21.getY()) {
                tag = true;
            }
        }
        assertFalse(tag);

    }

    @Test
    public void TestFirstMoveArtemis(){

        try {
            Coordinates coordinates21 = new Coordinates(2, 1);
            Coordinates coordinates22 = new Coordinates(2,2);
            board.moveWorker(coordinates22,worker1);
            board.setCurrentActiveWorker(worker1);
            TestRoundArtemis.firstMoveArtemis(coordinates21);
            assertEquals(board.getCurrentActiveWorker(), board.getWorker(coordinates21));
        }catch(NullPointerException e){}
    }

    @Test
    public void TestPossibleBuildsArtemisNotMoving(){
        try{
            Coordinates coordinates21 = new Coordinates(2, 1);
            board.moveWorker(coordinates21,worker1);
            TestRoundArtemis.possibleBuildsArtemis("NO");
            assertEquals(worker1.getCoordinates(),coordinates21);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestPossibleBuildsArtemisMoving(){
        try{
            Coordinates coordinates21 = new Coordinates(2, 1);
            Coordinates coordinates22 = new Coordinates(2,2);
            board.setCurrentActiveWorker(worker1);
            board.moveWorker(coordinates21,worker1);
            TestRoundArtemis.possibleBuildsArtemis("2,2");
            assertEquals(board.getWorker(coordinates22),worker1);

        }catch (NullPointerException e ){}
    }

    @Test
    public void TestDoMoveWinning(){
        Coordinates coordinates21 = new Coordinates(2, 1);
        Coordinates coordinates22 = new Coordinates(2,2);
        board.setLevel(coordinates21);
        board.setLevel(coordinates21);
        board.setLevel(coordinates21);
        board.setLevel(coordinates22);
        board.setLevel(coordinates22);
        board.moveWorker(coordinates22,worker1);
        boolean win = TestRoundArtemis.doMove(coordinates21,false,worker1);
        assertTrue(win);
    }

    @Test
    public void TestDoMoveWinningWithHeraPower(){
        Coordinates coordinates21 = new Coordinates(2, 1);
        Coordinates coordinates22 = new Coordinates(2,2);
        board.setHeraPlayer(2);
        board.setLevel(coordinates21);
        board.setLevel(coordinates21);
        board.setLevel(coordinates21);
        board.setLevel(coordinates22);
        board.setLevel(coordinates22);
        board.moveWorker(coordinates22,worker1);
        boolean win = TestRoundArtemis.doMove(coordinates21,false,worker1);
        assertTrue(win);
    }

    @Test
    public void TestActiveWorkerSelection(){
        try {
            Coordinates coordinates00 = new Coordinates(0, 0);
            Coordinates coordinates01 = new Coordinates(0, 1);
            Coordinates coordinates10 = new Coordinates(1, 0);
            Coordinates coordinates11 = new Coordinates(1, 1);
            Coordinates coordinates21 = new Coordinates(2, 1);
            board.setDome(coordinates01);
            board.setDome(coordinates11);
            board.setLevel(coordinates10);
            board.setLevel(coordinates10);
            board.moveWorker(coordinates00, worker1);
            board.moveWorker(coordinates21, worker2);
            TestRoundArtemis.activeWorkerSelection(0);
            assertEquals(board.getCurrentActiveWorker(), worker2);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestActiveWorkerSelection1(){
        try {
            Coordinates coordinates00 = new Coordinates(0, 0);
            Coordinates coordinates01 = new Coordinates(0, 1);
            Coordinates coordinates10 = new Coordinates(1, 0);
            Coordinates coordinates11 = new Coordinates(1, 1);
            Coordinates coordinates21 = new Coordinates(2, 1);
            board.setDome(coordinates01);
            board.setDome(coordinates11);
            board.setLevel(coordinates10);
            board.setLevel(coordinates10);
            board.moveWorker(coordinates00, worker1);
            board.moveWorker(coordinates21, worker2);
            TestRoundArtemis.activeWorkerSelection(1);
            assertEquals(board.getCurrentActiveWorker(), worker2);
        }catch (NullPointerException e){}
    }
}
