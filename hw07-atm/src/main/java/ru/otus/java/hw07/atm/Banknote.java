package ru.otus.java.hw07.atm;

public enum Banknote {
    ONE_HUNDRED(100), FIFTY(50), TWENTY(20), TEN(10), FIVE(5), ONE(1);

    private final int value;

    Banknote(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}
