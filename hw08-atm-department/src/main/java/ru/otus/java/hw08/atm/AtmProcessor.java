package ru.otus.java.hw08.atm;

import java.util.Map;

public interface AtmProcessor {

    void withdrawCash(int amount) throws InsufficientFundsException;

    void depositCash(Map<Banknote, Integer> banknotesToDeposit);

    void checkBalance();

    Map<Banknote, Integer> getBalance();

}
