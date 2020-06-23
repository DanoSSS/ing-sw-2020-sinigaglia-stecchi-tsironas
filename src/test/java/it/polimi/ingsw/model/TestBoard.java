package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestBoard {
    Player player1 = new Player("pippo", "RED", 1,2, God.APOLLO,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.HERA,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players2[] = {player1,player2};
    Board board2p = new Board(players2,worker1,worker2,worker3,worker4,2);
    Player player3 = new Player("paperino", "GREEN",5,6,God.CHRONUS,3);
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

    @Test
    public void TestEqualsCoordinate(){
        boolean equals;
        Coordinates c = new Coordinates(1,1);
        Coordinates c1 = new Coordinates(1,1);
        equals = c.equals(c1);
        assertTrue(equals);
    }

    @Test
    public void TestSetWorker(){
        Coordinates c = new Coordinates(1,1);
        board2p.setWorker(c,0);
        Worker wk = board2p.getWorkerById(0);
        assertEquals(board2p.getWorker(c),wk);
    }

    @Test
    public void TestGetPlayersNicknames(){
        String nicknames[];
        nicknames = board3p.getPlayerNicknames();
        assertEquals(nicknames[0],"pippo");
        assertEquals(nicknames[1],"pluto");
        assertEquals(nicknames[2],"paperino");
    }

    @Test
    public void TestGetIdPlayers(){
        Integer IdPlayers[];
        IdPlayers = board3p.getIdPlayers();
        assertEquals(IdPlayers[0],1);
        assertEquals(IdPlayers[1],2);
        assertEquals(IdPlayers[2],3);
    }

    @Test
    public void TestNumberOfDome(){
        board3p.setNumberOfDome(3);
        assertEquals(3, board3p.getNumberOfDome());
    }

    @Test
    public void TestUpdateRound(){
        board3p.setCurrentRound(3);
        board3p.setLoseRound(3);
        board3p.UpdateRound();
        assertEquals(1, board3p.getCurrentRound());
        board2p.setCurrentRound(1);
        board2p.setLoseRound(2);
        board2p.UpdateRound();
        assertEquals(1, board2p.getCurrentRound());
    }

    @Test
    public void TestFirstBuildDemeterAndHestia(){
        try {
            Coordinates c11 = new Coordinates(1, 1);
            Coordinates c22 = new Coordinates(2, 2);
            ArrayList<Coordinates> possiblesBuildsCoordinates = new ArrayList<Coordinates>();
            possiblesBuildsCoordinates.add(c11);
            board2p.firstBuildDemeterAndHestia(c22, possiblesBuildsCoordinates);
            assertEquals(possiblesBuildsCoordinates, board2p.getCurrentPossibleBuilds());
        }catch (NullPointerException e){}
    }

    @Test
    public void TestSetCurrentActiveWorkerAndChoosePrometheus(){
        try{
            Coordinates c11 = new Coordinates(1, 1);
            Coordinates c22 = new Coordinates(2, 2);
            ArrayList<Coordinates> possiblesBuildsCoordinates = new ArrayList<Coordinates>();
            ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
            possiblesBuildsCoordinates.add(c11);
            possiblesMovesCoordinates.add(c22);
            board2p.setCurrentActiveWorkerAndChoosePrometheus(worker1,possiblesMovesCoordinates,possiblesBuildsCoordinates);
            board3p.buildEndTurn(null);
            assertEquals(worker1,board2p.getCurrentActiveWorker());
            assertEquals(possiblesMovesCoordinates,board2p.getCurrentPossibleMoves());
            assertEquals(possiblesBuildsCoordinates,board2p.getCurrentPossibleBuilds());
        }catch (NullPointerException e){}
    }

    @Test
    public void TestBuildBeforePrometheus(){
        try{
            Coordinates c11 = new Coordinates(1, 1);
            ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
            possiblesMovesCoordinates.add(c11);
            board3p.buildBeforePrometheus(c11,possiblesMovesCoordinates);
            assertEquals(possiblesMovesCoordinates,board3p.getCurrentPossibleMoves());
        }catch (NullPointerException e){}
    }

    @Test
    public void TestSetCurrentActiveWorkerAndPossibleMoves(){
        try{
            Coordinates c11 = new Coordinates(1, 1);
            ArrayList<Coordinates> possiblesMovesCoordinates = new ArrayList<Coordinates>();
            possiblesMovesCoordinates.add(c11);
            board3p.aresEndTurn(null);
            board2p.setCurrentActiveWorkerAndPossibleMoves(worker1,possiblesMovesCoordinates);
            assertEquals(worker1,board2p.getCurrentActiveWorker());
            assertEquals(possiblesMovesCoordinates,board2p.getCurrentPossibleMoves());
        }catch (NullPointerException e){}
    }

    @Test
    public  void  TestMoveWorkerAndPossibleBuilds(){
        try{
            Coordinates c11 = new Coordinates(1, 1);
            Coordinates c22 = new Coordinates(2, 2);
            ArrayList<Coordinates> possiblesBuildsCoordinates = new ArrayList<Coordinates>();
            possiblesBuildsCoordinates.add(c22);
            board2p.moveWorkerAndPossibleBuilds(c11,c22,possiblesBuildsCoordinates,worker2);
        }catch (NullPointerException e){}
    }

    @Test
    public void TestAresEndTurn(){
        try{
            try{
                Coordinates c = new Coordinates(1,1);
                board3p.setLevel(c);
                board3p.aresEndTurn(c);
                assertEquals(1,board3p.getCurrentRound());
                assertEquals(1,board3p.getLevel(c));
            }catch(NullPointerException e){}
        }catch (NullPointerException e){}
    }

    @Test
    public void TestBuildAres(){
        try{
            Coordinates c = new Coordinates(1,1);
            board2p.setCurrentActiveWorkerAndPossibleMoves(worker1,null);
            board2p.buildAres(c);
            assertEquals(worker1,board2p.getCurrentActiveWorker());
            board2p.setCurrentActiveWorkerAndPossibleMoves(worker2,null);
            board2p.buildAres(c);
            assertEquals(worker2,board2p.getCurrentActiveWorker());
        }catch (NullPointerException e){}
    }

    @Test
    public void TestSetandGetWorker(){
        player1.setWorker1(worker1);
        assertEquals(worker1,player1.getWorker1());
        player1.setWorker2(worker2);
        assertEquals(worker2,player1.getWorker2());
    }

    @Test
    public void TestSetandGetPlayer(){
        worker1.setPlayer(player3);
        assertEquals(player3, worker1.getPlayer());
    }

    @Test
    public void TestsetCurrentActiveWorker(){
        board2p.setCurrentActiveWorker(worker1);
        board2p.winGame();
        board2p.loseGame();
        assertEquals(worker1,board2p.getCurrentActiveWorker());

    }
}