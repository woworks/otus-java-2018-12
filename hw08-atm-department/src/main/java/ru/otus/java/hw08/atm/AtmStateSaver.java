package ru.otus.java.hw08.atm;

public class AtmStateSaver {

    private AtmStates memento;

    public AtmStates getMemento() {
        return memento;
    }

    public void setStates(AtmStates memento) {
        this.memento = memento;
    }
}
