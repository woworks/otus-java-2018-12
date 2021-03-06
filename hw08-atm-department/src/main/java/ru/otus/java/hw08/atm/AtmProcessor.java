package ru.otus.java.hw08.atm;

import java.util.Map;

public interface AtmProcessor {

    Long getId();

    void withdrawCash(int amount) throws InsufficientFundsException;

    void clearCash();

    void depositCash(Map<Banknote, Integer> banknotesToDeposit);

    void checkBalance();

    Map<Banknote, Integer> getBalance();

    void depositCash(AtmMemento atmMemento);

    Memento saveToMemento();

    void loadFromMemento(Memento memento);

}
