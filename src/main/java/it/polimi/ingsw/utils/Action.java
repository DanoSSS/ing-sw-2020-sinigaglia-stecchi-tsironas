package it.polimi.ingsw.utils;

 public enum Action {

    INITWORKERS(0), SELECTACTIVEWORKER(1), CURRENTPLAYERNUMBER(2), WORKERSET(3), STRING(4);
     private int value;

    Action(int value){
        this.value=value;
    }

     public int getValue() {
         return value;
     }
 }
