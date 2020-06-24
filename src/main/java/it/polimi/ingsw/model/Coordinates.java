package it.polimi.ingsw.model;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private int x;
    private int y;

    public Coordinates(int x,int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return x
     */
    public int getX(){
        return x;
    }

    /**
     *
     * @return y
     */
    public int getY(){
        return y;
    }

    /**
     *
     * @param x
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * method that check if two coordinates have the same x and y values
     * @param c
     * @return
     */
    public boolean equals (Coordinates c){
        return (c.getX() == this.x && c.getY() == this.y);
    }
}
