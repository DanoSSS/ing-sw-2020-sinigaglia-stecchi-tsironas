package it.polimi.ingsw.client;

public enum CellMessage{
    LV0("lv0"),
    LV1("lv1"),
    LV2("lv2"),
    LV3("lv3"),
    DOME("Dom"),
    W0("Wk0"),
    W1("Wk1"),
    W2("Wk2"),
    W3("Wk3"),
    W4("Wk4"),
    W5("Wk5"),
    free("---");


    private final String message;

    public String getMessage() {
        return message;
    }

    CellMessage(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
