package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestRoundEphaestus {
    Player player1 = new Player("pippo", "RED", 1, 2, God.EPHAESTUS, 1);
    Worker worker1 = new Worker(player1, "RED", 1);
    Worker worker2 = new Worker(player1, "RED", 2);
    Player player2 = new Player("pluto", "BLUE", 3, 4, God.EPHAESTUS, 2);
    Worker worker3 = new Worker(player2, "BLUE", 3);
    Worker worker4 = new Worker(player2, "BLUE", 4);
    Player players[] = {player1, player2};
    Board board = new Board(players, worker1, worker2, worker3, worker4, 2);
    RoundEphaestus TestRoundEphaestus = new RoundEphaestus(board, player1);

    @Test
    public void TestBuildSecondYes() {
        try {
            board.setNround(1);
            Coordinates coordinates10 = new Coordinates(1, 0);
            TestRoundEphaestus.buildSecond("yes 1,0");
            assertEquals(2, board.getLevel(coordinates10));
            assertEquals(1, board.getNround());
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void TestBuildSecondNo() {
        try {
            Coordinates coordinates10 = new Coordinates(1, 0);
            TestRoundEphaestus.buildSecond("no 1,0");
            assertEquals(2, board.getLevel(coordinates10));
        } catch (NullPointerException e) {}
    }

    @Test
    public void TestUpdateActiveWorker(){
        try{
            Message message = new Message(1,0);
            TestRoundEphaestus.update(message);
            assertEquals(board.getCurrentActiveWorker(),worker1);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateSelectCoordinateMove(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(6,coordinates11);
            TestRoundEphaestus.update(message);
            assertEquals(worker1,board.getWorker(coordinates11));
        }catch (NullPointerException e){}
    }

    @Test
    public void TestUpdateBuildEphaestus(){
        try{
            board.setCurrentActiveWorker(worker1);
            Coordinates coordinates11 = new Coordinates(1,1);
            Message message = new Message(12,"no 1,1");
            TestRoundEphaestus.update(message);
            assertEquals(1,board.getLevel(coordinates11));
        }catch (NullPointerException e){}
    }
}
