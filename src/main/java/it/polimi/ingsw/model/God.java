package it.polimi.ingsw.model;

public enum God {
    APOLLO,ARTEMIS,ATHENA,ATLAS,DEMETER,EPHEASTUS,MINOTAUR,PAN,PROMETHEUS;
    private boolean IsSelectedGod;
    public boolean isSelectedGod() {
        return IsSelectedGod;
    }

    public void setSelectedGod(boolean selectedGod) {
        IsSelectedGod = selectedGod;
    }
}
