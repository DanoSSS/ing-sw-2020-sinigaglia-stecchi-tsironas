package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;

public class GodChoice {
    private final String god1;
    private final String god2;
    private final Player player;
    private final View view;

    public GodChoice(Player player,String god1,String god2,View view) {
        this.god1 = god1;
        this.god2 = god2;
        this.view = view;
        this.player = player;
    }

    public View getView() {
        return view;
    }

    public Player getPlayer() {
        return player;
    }

    public String getGod2() {
        return god2;
    }

    public String getGod1() {
        return god1;
    }
}
