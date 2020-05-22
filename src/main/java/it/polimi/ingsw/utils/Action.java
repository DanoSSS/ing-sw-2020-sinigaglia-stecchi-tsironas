package it.polimi.ingsw.utils;

 public enum Action {

    INIT_WORKERS(0),
    SELECT_ACTIVE_WORKER(1),
    NOT_YOUR_TURN(2),
    WORKER_SET(3),
    STRING(4),
    LOSE(5),
    SELECT_COORDINATE_MOVE(6),
    MOVE_AND_COORDINATE_BUILD(7),
    BUILD_END_TURN(8),
    ARTEMIS_FIRST_MOVE(9),
    ARTEMIS_SECOND_MOVE(10),
    BUILD_ATLAS(11),
    BUILD_EPHAESTUS(12),
    END_TURN(13),
    FIRST_BUILD_DEMETER(14);

    private int value;

    Action(int value){
        this.value=value;
    }

    public int getValue() {
         return value;
     }
 }
