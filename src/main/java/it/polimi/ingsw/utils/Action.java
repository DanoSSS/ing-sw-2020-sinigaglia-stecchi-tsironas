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
    FIRST_BUILD_DEMETER(14),
    PROMETHEUS_CHOOSE(15),
    FIRST_BUILD_PROMETHEUS(16),
    LOSE3P(17),
    WIN(18),
    FIRST_MESSAGE(19),
    NICKNAME_ALREADY_USED(20),
    NUMBER_OF_PLAYERS(21),
    SELECT_GODS_CHALLENGER(22),
    WRONG_GODS(23),
    CHOOSE_GOD(24),
    SET_WORKER_POSITION(25),
    ERROR_SET_WORKER_POSITION(26),
    GAME_OVER(27),
    END_GAME(28),
    PLAYER_DISCONNECTED(29);

    private int value;

    Action(int value){
        this.value=value;
    }

    public int getValue() {
         return value;
     }
 }
