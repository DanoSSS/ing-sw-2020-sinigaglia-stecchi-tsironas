package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestBoard {
    Player player1 = new Player("pippo", "RED", 1,2, God.APOLLO,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.HERA,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players2[] = {player1,player2};
    Board board2p = new Board(players2,worker1,worker2,worker3,worker4,2);
    Player player3 = new Player("paerino", "GREEN",5,6,God.CHRONUS,3);
    Worker worker5 = new Worker(player3,"GREEN",5);
    Worker worker6 = new Worker(player3, "GREEN", 6);
    Player players3[] = {player1,player2,player3};
    Board board3p = new Board(players3,worker1,worker2,worker3,worker4,worker5,worker6,3);

    @Test
    public void TestSetDome_IsDome(){
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates11 = new Coordinates(1,1);
        board2p.setDome(coordinates21);
        assertTrue(board2p.isDome(coordinates21));
        assertFalse(board2p.isDome(coordinates11));
    }
    @Test
    public void TestMoveWorker_IsOccupied(){
        Coordinates coordinates21 = new Coordinates(2,1);
        Coordinates coordinates11 = new Coordinates(1,1);
        board2p.moveWorker(coordinates11,worker1);
        assertTrue(board2p.isOccupied(coordinates11));
        assertFalse(board2p.isOccupied(coordinates21));
    }
    @Test
    public void TestGetWorker_FreeCellFromWorker(){
        Coordinates coordinates11 = new Coordinates(1,1);
        board2p.moveWorker(coordinates11,worker1);
        assertEquals(worker1,board2p.getWorker(coordinates11));
        board2p.freeCellFromWorker(coordinates11);
        assertEquals(null,board2p.getWorker(coordinates11));
    }
    @Test
    public void TestSetLevel_GetLevel(){
        Coordinates coordinates11 = new Coordinates(1,1);
        assertEquals(0,board2p.getLevel(coordinates11));
        board2p.setLevel(coordinates11);
        assertEquals(1,board2p.getLevel(coordinates11));
        board2p.setLevel(coordinates11);
        assertEquals(2,board2p.getLevel(coordinates11));
        board2p.setLevel(coordinates11);
        assertEquals(3,board2p.getLevel(coordinates11));
        board2p.setLevel(coordinates11);
        assertEquals(4,board2p.getLevel(coordinates11));
    }
    @Test
    public void TestSetandGetNumberofPlayers(){
        board3p.setNumberOfPlayers(3);
        assertEquals(3,board3p.getNumberOfPlayers());
    }
    @Test
    public void TestSetandGetNround(){
        board3p.setNround(2);
        assertEquals(2,board3p.getNround());
    }
    @Test
    public void TestLoseGame(){
        try {
            board3p.loseGame();
            assertEquals(3, board3p.getCurrentRound());
            assertEquals(2, board3p.getLoseRound());
        }catch (NullPointerException e){}
    }
    @Test
    public void TestReduceLevel(){
        Coordinates c = new Coordinates(1,1);
        board3p.setLevel(c);
        board3p.reduceLevel(c);
        assertEquals(0,board3p.getLevel(c));
    }
    @Test
    public void TestHeraandChronusPlayer(){
        board3p.setHeraPlayer(2);
        board3p.setChronusPlayer(3);
        assertEquals(2, board3p.getHeraPlayer());
        assertEquals(3, board3p.getChronusPlayer());
    }
    @Test
    public void TestBuildandEndTurn(){
        try{
            Coordinates c = new Coordinates(1,1);
            board3p.setLevel(c);
            board3p.buildEndTurn(c);
            assertEquals(1,board3p.getCurrentRound());
            assertEquals(1,board3p.getLevel(c));
        }catch(NullPointerException e){}
    }
}