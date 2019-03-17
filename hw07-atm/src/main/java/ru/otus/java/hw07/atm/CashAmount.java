package ru.otus.java.hw07.atm;

import java.util.EnumMap;
import java.util.Map;

public class CashAmount {

    enum Banknote {
        ONE_HUNDRED(100), FIFTY(50), TWENTY(20), TEN(10), FIVE(5), ONE(1);

        private int value;

        Banknote(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }

    private int value;

    public CashAmount(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }

    Map<Banknote, Integer> getBanknotes() {
        return calculateBanknotes(this.value);
    }

    static Map<Banknote, Integer> calculateBanknotes(int value) {

        EnumMap<Banknote, Integer> banknoteMap = new EnumMap<>(Banknote.class);

        for (int i = 0; i <  Banknote.values().length; i++) {
            Banknote banknote = Banknote.values()[i];
            int integerPart = value / banknote.getValue();
            if (integerPart > 0) {
                value = value - integerPart * banknote.getValue();
                banknoteMap.put(Banknote.values()[i], integerPart);
            }
        }

        return banknoteMap;
    }

    @Override
    public String toString() {
        return "CashAmount{" +
                "value=" + value +
                '}';
    }
}
