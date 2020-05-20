package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestRoundAtlas {
    Player player1 = new Player("pippo", "RED", 1,2, God.APOLLO,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.APOLLO,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player players[] = {player1,player2};
    Board board = new Board(players,worker1,worker2,worker3,worker4,2);
    RoundAtlas testRoundAtlas = new RoundAtlas(board,player1);

    @Test
    public void TestDobuild(){
        boolean dome1 = true;
        Coordinates c1 = new Coordinates(1,1);
        testRoundAtlas.doBuild(c1,dome1);
        assertTrue(board.isDome(c1));
        boolean dome2 = false;
        Coordinates c2 = new Coordinates(2,2);
        testRoundAtlas.doBuild(c2,dome2);
        assertFalse(board.isDome(c2));
    }

}
