package it.polimi.ingsw.testcontroller;

import it.polimi.ingsw.controller.RoundApollo;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestRoundApollo {
    private Board board;
    private Player player;
    Cell cell1 = new Cell(0,0);


    @Test
    public void TestDoBuild() {
        int i;
        cell1.setLevel(2);
        RoundApollo TestDoBuild = new RoundApollo(board,player);
        TestDoBuild.dobuild(cell1);
        i=cell1.getLevel();
        assertEquals(3,i);
    }

    @Test
    public void TestIsDome(){
        cell1.setLevel(3);
        RoundApollo TestDoBuild = new RoundApollo(board,player);
        TestDoBuild.dobuild(cell1);
        assertTrue(cell1.isDome());

    }

}
