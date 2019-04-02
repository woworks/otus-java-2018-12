package ru.otus.java.hw08.atm;

import java.util.Map;

public interface Memento {

    Map<Banknote, Integer> getState();
}
