package ru.otus.java.hw08.atm;

import java.util.HashMap;
import java.util.Map;

public class AtmProcessorImpl implements AtmProcessor {

    private Long id;
    private final AtmStorage atmStorage;

    public AtmProcessorImpl(Long id, AtmStorage atmStorage) {
        this.id = id;
        this.atmStorage = atmStorage;
    }

    @Override
    public void withdrawCash(int amount) throws InsufficientFundsException {
        Map<Banknote, Integer> banknotes = this.atmStorage.withdrawCash(amount);

        System.out.println("You'll get the following Banknotes: ");
        for (Map.Entry<Banknote, Integer> entry: banknotes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " Banknote(s)");
        }
    }

    @Override
    public void clearCash() {
        this.atmStorage.clearBalance();
    }

    @Override
    public void depositCash(Map<Banknote, Integer> banknotesToDeposit) {

        System.out.println("Will input cash amount to deposit: " + banknotesToDeposit);

        for (int i = 0; i < Banknote.values().length; i++) {
            Banknote banknote = Banknote.values()[i];
            banknotesToDeposit.put(banknote, banknotesToDeposit.get(banknote));
        }

        this.atmStorage.depositCash(banknotesToDeposit);

    }

    @Override
    public void depositCash(AtmMemento atmMemento) {
        depositCash(atmMemento.getState());
    }

    @Override
    public void checkBalance() {
        System.out.println("ATM #" + this.id + " balance: ");
        for (int i = 0; i < Banknote.values().length; i++) {
            Banknote banknote = Banknote.values()[i];
            System.out.print(banknote + ": [" + atmStorage.getStorageBanknotes().get(banknote) + "]; ");
        }
        System.out.println("\n");

    }

    @Override
    public Map<Banknote, Integer> getBalance() {
        Map<Banknote, Integer> balance = new HashMap<>();
        for (int i = 0; i < Banknote.values().length; i++) {
            Banknote banknote = Banknote.values()[i];
            balance.put(banknote, atmStorage.getStorageBanknotes().get(banknote));
        }

        return balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtmStorage getAtmStorage() {
        return atmStorage;
    }
}
