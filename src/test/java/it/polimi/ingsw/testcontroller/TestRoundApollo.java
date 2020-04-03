package it.polimi.ingsw.testcontroller;

import it.polimi.ingsw.controller.RoundApollo;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class TestRoundApollo extends TestCase {
    private Board board;
    private Player player;
    Cell cell1 = new Cell();


    @Test
    public void TestDoBuild() {
        int i;
        cell1.setLevel(2);
        RoundApollo TestDoBuild = new RoundApollo(board,player);
        TestDoBuild.dobuild(cell1);
        i=cell1.getLevel();
        assertEquals(3,i);
    }


}
