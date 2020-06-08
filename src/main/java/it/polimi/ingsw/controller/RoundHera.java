package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

public class RoundHera extends Round {
    public RoundHera(Board board, Player player) {
        super(board, player);
        board.setHeraPlayer(player.getIdPlayer());
    }
}
