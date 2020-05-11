package it.polimi.ingsw.utils;

public enum Color {
    RED(1),GREEN(2),BLUE(3);
    String color;
    Color(int i){
        this.color=Color.values()[i].toString();
    }
}
