package ru.otus.java.hw08.atm;

import java.util.Map;

public class AtmStates {
    private Map<Long, Memento> states;

    AtmStates(Map<Long, Memento> states) {
        this.states = states;
    }

    Memento getStateByAtmId(Long atmId) {
        return states.get(atmId);
    }

    void setStateByAtmId(Long atmId, Memento memento) {
        states.put(atmId, memento);
    }
}
