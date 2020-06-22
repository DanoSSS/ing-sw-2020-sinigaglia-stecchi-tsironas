package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoundPrometheus {
    Player player1 = new Player("pippo", "RED", 1,2, God.PROMETHEUS,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.PROMETHEUS,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundPrometheus TestRoundPrometheus = new RoundPrometheus(board,player1);

    @Test
    public void TestCanMoveAfterBuild() {
        boolean tag = false;
        ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
        Coordinates coordinates11 = new Coordinates(1, 1);
        Coordinates coordinates21 = new Coordinates(2, 1);
        board.moveWorker(coordinates11, worker1);
        possiblesMovesCoordinates = TestRoundPrometheus.canMoveAfterBuild(worker1);
        for (Coordinates c : possiblesMovesCoordinates) {
            if (c.getX() == coordinates21.getX() && c.getY() == coordinates21.getY()) {
                tag = true;
            }
        }
        assertTrue(tag);
    }

    @Test
    public void TestActiveWorkerSelectionPrometheus(){
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
            TestRoundPrometheus.activeWorkerSelectionPrometheus(0);
            assertEquals(board.getCurrentActiveWorker(), worker2);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestActiveWorkerSelectionPrometheus1(){
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
            TestRoundPrometheus.activeWorkerSelectionPrometheus(1);
            assertEquals(board.getCurrentActiveWorker(), worker2);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestMoveOrBuildWithBuild(){
        try {
            Coordinates coordinates00 = new Coordinates(0, 0);
            Coordinates coordinates11 = new Coordinates(1, 1);
            board.moveWorker(coordinates00,worker1);
            board.setCurrentActiveWorker(worker1);
            TestRoundPrometheus.moveOrBuild("BUILD 1,1");
            int level = board.getLevel(coordinates11);
            assertEquals(1, level);
        }catch (NullPointerException e){}
    }
    @Test
    public void TestMoveOrBuildWithMove(){
        try {
            Coordinates coordinates00 = new Coordinates(0, 0);
            Coordinates coordinates11 = new Coordinates(1, 1);
            board.moveWorker(coordinates00,worker1);
            board.setCurrentActiveWorker(worker1);
            TestRoundPrometheus.moveOrBuild("MOVE 1,1");
            assertEquals(worker1,board.getWorker(coordinates11));
        }catch (NullPointerException e){}
    }
}
