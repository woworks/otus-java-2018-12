package ru.otus.java.hw08.atm;

import java.util.Map;

public class AtmMemento {
    private final Map<Banknote, Integer> state;

    public AtmMemento(Map<Banknote, Integer> state) {
        this.state = state;
    }

    public Map<Banknote, Integer> getState() {
        return state;
    }
}
