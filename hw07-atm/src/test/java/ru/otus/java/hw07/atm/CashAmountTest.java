package ru.otus.java.hw07.atm;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CashAmountTest {

    @Test
    void getBanknotes() {
        CashAmount cashAmount = new CashAmount(253);

        Map<CashAmount.Banknote, Integer> banknotes = cashAmount.getBanknotes();

        assertEquals(3, banknotes.keySet().size());

        assertEquals(2, banknotes.get(CashAmount.Banknote.ONE_HUNDRED).intValue());
        assertEquals(1, banknotes.get(CashAmount.Banknote.FIFTY).intValue());
        assertEquals(3, banknotes.get(CashAmount.Banknote.ONE).intValue());

        cashAmount.setValue(388);
        banknotes = cashAmount.getBanknotes();


        assertEquals(3, banknotes.get(CashAmount.Banknote.ONE_HUNDRED).intValue());
        assertEquals(1, banknotes.get(CashAmount.Banknote.FIFTY).intValue());
        assertEquals(1, banknotes.get(CashAmount.Banknote.TWENTY).intValue());
        assertEquals(1, banknotes.get(CashAmount.Banknote.TEN).intValue());
        assertEquals(1, banknotes.get(CashAmount.Banknote.FIVE).intValue());
        assertEquals(3, banknotes.get(CashAmount.Banknote.ONE).intValue());


    }
}