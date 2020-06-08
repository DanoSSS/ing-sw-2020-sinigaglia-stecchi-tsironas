package it.polimi.ingsw.client.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MoveListener implements MouseListener {
    protected int x;
    protected int y;
    protected BoardPanel board;

    public MoveListener(int x, int y,BoardPanel board){
        this.x=x;
        this.y=y;
        this.board=board;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        try {
            board.handleClick(x, y);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
