package it.polimi.ingsw.testcontroller;

import it.polimi.ingsw.controller.RoundApollo;
import it.polimi.ingsw.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestRoundApollo {
    private Board board=new Board();
    private Player player;
    Coordinates coordinates = new Coordinates(2,1);

    @Test
    public void TestDoBuild() {
        int i;
        boolean isDome;
        RoundApollo TestDoBuild = new RoundApollo(board,player);
        TestDoBuild.doBuild(coordinates);
        i = board.getLevel(coordinates);
        assertEquals(1,i);
        TestDoBuild.doBuild(coordinates);
        TestDoBuild.doBuild(coordinates);
        TestDoBuild.doBuild(coordinates);
        isDome = board.isDome(coordinates);
        assertTrue(isDome);
    }

}
