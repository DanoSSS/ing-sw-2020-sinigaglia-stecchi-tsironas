package it.polimi.ingsw.model;

public enum God {
    APOLLO,ARTEMIS,ATHENA,ATLAS,DEMETER,EPHAESTUS,MINOTAUR,PAN,PROMETHEUS,CHRONUS,HERA,ZEUS,HESTIA,ARES;
    private boolean IsSelectedGod;
    public boolean isSelectedGod() {
        return IsSelectedGod;
    }

    public void setSelectedGod(boolean selectedGod) {
        IsSelectedGod = selectedGod;
    }

}
