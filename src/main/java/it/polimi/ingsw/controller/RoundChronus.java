package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

public class RoundChronus extends Round{

    public RoundChronus(Board board, Player player) {
        super(board, player);
        board.setChronusPlayer(player.getIdPlayer());
    }

}
