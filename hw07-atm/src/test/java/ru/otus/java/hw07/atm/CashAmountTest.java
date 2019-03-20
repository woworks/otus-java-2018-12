package ru.otus.java.hw07.atm;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CashAmountTest {

    @Test
    void getBanknotes() {

        Map<AtmStorage.Banknote, Integer> banknotes = AtmStorage.calculateBanknotes(253);

        assertEquals(3, banknotes.keySet().size());

        assertEquals(2, banknotes.get(AtmStorage.Banknote.ONE_HUNDRED).intValue());
        assertEquals(1, banknotes.get(AtmStorage.Banknote.FIFTY).intValue());
        assertEquals(3, banknotes.get(AtmStorage.Banknote.ONE).intValue());

        banknotes = AtmStorage.calculateBanknotes(388);

        assertEquals(3, banknotes.get(AtmStorage.Banknote.ONE_HUNDRED).intValue());
        assertEquals(1, banknotes.get(AtmStorage.Banknote.FIFTY).intValue());
        assertEquals(1, banknotes.get(AtmStorage.Banknote.TWENTY).intValue());
        assertEquals(1, banknotes.get(AtmStorage.Banknote.TEN).intValue());
        assertEquals(1, banknotes.get(AtmStorage.Banknote.FIVE).intValue());
        assertEquals(3, banknotes.get(AtmStorage.Banknote.ONE).intValue());


    }
}