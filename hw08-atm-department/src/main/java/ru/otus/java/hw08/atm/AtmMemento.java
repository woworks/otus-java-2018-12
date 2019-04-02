package ru.otus.java.hw08.atm;

import java.util.Map;

public class AtmMemento implements Memento {
    private final Map<Banknote, Integer> state;

    AtmMemento(Map<Banknote, Integer> state) {
        this.state = state;
    }

    public Map<Banknote, Integer> getState() {
        return this.state;
    }

    @Override
    public String toString() {
        return "AtmMemento{" +
                "state=" + state +
                '}';
    }
}
