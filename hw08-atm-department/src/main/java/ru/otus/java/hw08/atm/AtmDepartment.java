package ru.otus.java.hw08.atm;

import java.util.*;

public class AtmDepartment {

    private List<AtmProcessor> atmProcessors;

    private AtmStateSaver atmStateSaver = new AtmStateSaver();

    public AtmDepartment(List<AtmProcessor> atmProcessors) {
        this.atmProcessors = atmProcessors;
    }


    public Map<Long, Memento> getBalanceByAtm() {
        Map<Long, Memento> atmsBalance = new HashMap<>();
        for (AtmProcessor processor: atmProcessors) {
            atmsBalance.put(processor.getId(), new AtmMemento(processor.getBalance()));
        }
        return atmsBalance;
    }

    Map<Banknote, Integer> getBalance() {
        Map<Banknote, Integer> totalBalance = new HashMap<>();
        for (AtmProcessor processor: atmProcessors) {
            addProcessor(totalBalance, processor.getBalance());
        }
        return totalBalance;
    }

    private void addProcessor(Map<Banknote, Integer> totalBalance, Map<Banknote, Integer> processorBalance){
        for (int i = 0; i < Banknote.values().length; i++) {
            Banknote banknote = Banknote.values()[i];
            totalBalance.put(banknote, totalBalance.get(banknote) + processorBalance.get(banknote));
        }
    }

    public void displayBalance() {
        System.out.println("Total ATM Department Balance: ");
        for (AtmProcessor atm: this.atmProcessors){
            atm.checkBalance();
        }
    }

    public void generateUserAtmActivity() {
        for (AtmProcessor atm: atmProcessors){
            atm.depositCash(generateBanknotes());
            int amount = new Random().nextInt(100);
            try {
                atm.withdrawCash(amount);
            } catch (InsufficientFundsException e) {
                System.out.println("Could not withdraw amount: " + amount);
            }
        }
    }

    private static Map<Banknote, Integer> generateBanknotes() {
        Map<Banknote, Integer> banknotes = new HashMap<>();
        for (int i = 0; i < Banknote.values().length; i++) {
             banknotes.put(Banknote.values()[i], new Random().nextInt(10));
        }

        return banknotes;
    }

    public void restoreAtmsState() {
        AtmStates states = atmStateSaver.getMemento();
        for (AtmProcessor atmProcessor: atmProcessors){
            Memento memento = states.getStateByAtmId(atmProcessor.getId());
            atmProcessor.loadFromMemento(memento);
        }
    }

    public void saveAtmsState() {
        AtmStates states = atmStateSaver.getMemento();
        for (AtmProcessor atmProcessor: atmProcessors){
            states.setStateByAtmId(atmProcessor.getId(), atmProcessor.saveToMemento());
        }
    }

}
