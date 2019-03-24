package ru.otus.java.hw08.atm;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class AtmStorage {

    Map<Banknote, Integer> getStorageBanknotes() {
        return storageBanknotes;
    }

    private final Map<Banknote, Integer> storageBanknotes = new HashMap<>();


    public void initBalance() {
        for (int i = 0; i <  Banknote.values().length; i++) {
            Banknote banknote = Banknote.values()[i];
            storageBanknotes.put(banknote, (i + 1) + 10);
        }
    }

    Map<Banknote, Integer> withdrawCash(int value) throws InsufficientFundsException {

        // verify if atm has enough banknotes - dry run
        this.minusCash(value, true);

        //real withdraw
        return this.minusCash(value, false);
    }

    private Map<Banknote, Integer> minusCash(int value, boolean emulate) throws InsufficientFundsException {
        Map<Banknote, Integer> toMinus = calculateBanknotes(value);

        for (Map.Entry<Banknote, Integer> entry: toMinus.entrySet()) {
            Banknote banknote = entry.getKey();
            int needNumber = entry.getValue();
            int currentNumber = storageBanknotes.get(banknote);
            if (currentNumber - needNumber < 0) {
                throw new InsufficientFundsException("Not enough " + banknote + ": we have " + currentNumber + ", but we need : " + entry.getValue());
            }
            if (!emulate) {
                storageBanknotes.put(banknote, currentNumber - needNumber);
            }
        }
        return toMinus;
    }

    void depositCash(Map<Banknote, Integer> banknotes) {
        for (Map.Entry<Banknote, Integer> entry : banknotes.entrySet()) {
            Banknote banknote = entry.getKey();
            int number = entry.getValue();

            storageBanknotes.put(banknote, storageBanknotes.get(banknote) + number);
        }
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

}
