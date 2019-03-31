package ru.otus.java.hw08.atm;

import java.util.Map;

public class AtmStates {
    Map<Long, AtmMemento> states;

    public AtmStates(Map<Long, AtmMemento> states) {
        this.states = states;
    }

    public AtmMemento getStateByAtmId(Long atmId) {
        return states.get(atmId);
    }
}
