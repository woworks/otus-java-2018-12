package ru.otus.java.hw08.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtmDepartment {

    public AtmDepartment(List<AtmProcessor> atmProcessors) {
        this.atmProcessors = atmProcessors;
    }

    private List<AtmProcessor> atmProcessors = new ArrayList<>();

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
    }

    public void generateUserAtmActivity() {
    }

    public void reinitAtms() {
    }
}
