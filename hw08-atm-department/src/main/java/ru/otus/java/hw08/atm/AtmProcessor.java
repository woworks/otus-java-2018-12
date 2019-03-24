package ru.otus.java.hw08.atm;

import java.util.Map;

public interface AtmProcessor {

    void withdrawCash() throws InsufficientFundsException;

    void depositCash();

    void checkBalance();

    Map<Banknote, Integer> getBalance();

}
