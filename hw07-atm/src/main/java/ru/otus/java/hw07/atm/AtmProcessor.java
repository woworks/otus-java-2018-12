package ru.otus.java.hw07.atm;

public interface AtmProcessor {

    int displayMenu();

    void withdrawCash() throws InsufficientFundsException;

    void depositCash();

    void checkBalance();

}
