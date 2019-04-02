package ru.otus.java.hw08.atm;

public class InsufficientFundsException extends Exception{
    InsufficientFundsException(String message) {
        super(message);
    }
}
