package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestGame {
    Player player1 = new Player("pippo", "RED", 1,2, God.APOLLO,1);
    Worker worker1 = new Worker(player1,"RED",1);
    Worker worker2 = new Worker(player1,"RED",2);
    Player player2 = new Player("pluto", "BLUE", 3,4, God.ARES,2);
    Worker worker3 = new Worker(player2,"BLUE",3);
    Worker worker4 = new Worker(player2,"BLUE",4);
    Player player3 = new Player("paperino", "GREEN", 5,6, God.ARTEMIS,3);
    Worker worker5 = new Worker(player2,"GREEN",5);
    Worker worker6 = new Worker(player2,"GREEN",6);
    Player players2p[] = {player1,player2};
    Player players3p[] = {player1,player2,player3};
    Board board2p = new Board(players2p,worker1,worker2,worker3,worker4,2);
    Board board3p = new Board(players3p,worker1,worker2,worker3,worker4,worker5,worker6,3);
    Game game2p = new Game(board2p,2,player1,player2);
    Game game3p = new Game(board3p,3,player1,player2,player3);

    @Test
    public void TestRoundCreation(){
        Round r=null;
        game2p.RoundCreation(player1,r);
         r = game2p.getRound(player1);
        assertEquals(r.player,player1);
        game2p.RoundCreation(player2,r);
        r = game2p.getRound(player2);
        assertEquals(r.player,player2);
        game2p.RoundCreation(player3,r);
        r = game2p.getRound(player3);
        assertEquals(r.player,player3);
        Player player4 = new Player("pippo", "RED", 1,2, God.ATHENA,1);
        game2p.RoundCreation(player4,r);
        r = game2p.getRound(player4);
        assertEquals(r.player,player4);
        Player player5 = new Player("pippo", "RED", 1,2, God.ATLAS,1);
        game2p.RoundCreation(player5,r);
        r = game2p.getRound(player5);
        assertEquals(r.player,player5);
        Player player6 = new Player("pippo", "RED", 1,2, God.CHRONUS,1);
        game2p.RoundCreation(player6,r);
        r = game2p.getRound(player6);
        assertEquals(r.player,player6);
        Player player7 = new Player("pippo", "RED", 1,2, God.DEMETER,1);
        game2p.RoundCreation(player7,r);
        r = game2p.getRound(player7);
        assertEquals(r.player,player7);
        Player player8 = new Player("pippo", "RED", 1,2, God.EPHAESTUS,1);
        game2p.RoundCreation(player8,r);
        r = game2p.getRound(player8);
        assertEquals(r.player,player8);
        Player player9 = new Player("pippo", "RED", 1,2, God.HERA,1);
        game2p.RoundCreation(player9,r);
        r = game2p.getRound(player9);
        assertEquals(r.player,player9);
        Player player10 = new Player("pippo", "RED", 1,2, God.HESTIA,1);
        game2p.RoundCreation(player10,r);
        r = game2p.getRound(player10);
        assertEquals(r.player,player10);
        Player player11 = new Player("pippo", "RED", 1,2, God.MINOTAUR,1);
        game2p.RoundCreation(player11,r);
        r = game2p.getRound(player11);
        assertEquals(r.player,player11);
        Player player12 = new Player("pippo", "RED", 1,2, God.PAN,1);
        game2p.RoundCreation(player12,r);
        r = game2p.getRound(player12);
        assertEquals(r.player,player12);
        Player player13 = new Player("pippo", "RED", 1,2, God.PROMETHEUS,1);
        game2p.RoundCreation(player13,r);
        r = game2p.getRound(player13);
        assertEquals(r.player,player13);
        Player player14 = new Player("pippo", "RED", 1,2, God.ZEUS,1);
        game2p.RoundCreation(player14,r);
        r = game2p.getRound(player14);
        assertEquals(r.player,player14);
    }

}
