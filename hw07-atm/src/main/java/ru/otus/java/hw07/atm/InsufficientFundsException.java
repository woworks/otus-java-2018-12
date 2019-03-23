package ru.otus.java.hw07.atm;

public class InsufficientFundsException extends Exception{
    InsufficientFundsException(String message) {
        super(message);
    }
}
