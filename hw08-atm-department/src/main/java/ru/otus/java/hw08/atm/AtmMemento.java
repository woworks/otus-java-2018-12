package ru.otus.java.hw08.atm;

import java.util.Map;

public class AtmMemento {
    private final Map<Long, Map<Banknote, Integer>> state;

    public AtmMemento(Map<Long, Map<Banknote, Integer>> state) {
        this.state = state;
    }

    Map<Long, Map<Banknote, Integer>> getState() {
        return state;
    }
}
