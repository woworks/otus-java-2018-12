package ru.otus.java.hw08.atm;

import java.util.HashMap;

public class AtmStateSaver {

    private AtmStates memento = new AtmStates(new HashMap<>());

    AtmStates getMemento() {
        return memento;
    }

    public void setStates(AtmStates memento) {
        this.memento = memento;
    }
}
