package ru.otus.java.hw07.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AtmProcessorImpl implements AtmProcessor {

    private AtmStorage atmStorage;

    public AtmProcessorImpl(AtmStorage atmStorage) {
        this.atmStorage = atmStorage;
    }

    @Override
    public int displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1) Check Balance:");
        System.out.println("2) Deposit cash:");
        System.out.println("3) Withdraw cash:");
        System.out.println("0) Exit:");
        System.out.print("Your choice: ");

        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    @Override
    public void withdrawCash() throws InsufficientFundsException {
        int amount;
        do {
            System.out.print("Please correct input cash amount to withdraw: ");
            Scanner sc = new Scanner(System.in);
            amount = sc.nextInt();
        } while (amount <= 0);

        Map<AtmStorage.Banknote, Integer> banknotes = this.atmStorage.withdrawCash(amount);

        System.out.println("You'll get the following Banknotes: ");
        for (Map.Entry<AtmStorage.Banknote, Integer> entry: banknotes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " Banknote(s)");
        }
    }

    @Override
    public void depositCash() {

        Map<AtmStorage.Banknote, Integer> banknotesToDeposit = new HashMap<>();

        System.out.println("Please correct input cash amount to deposit: ");

        for (int i = 0; i < AtmStorage.Banknote.values().length; i++) {

            int amount;
            do {
                System.out.print("Put banknotes of amount " + AtmStorage.Banknote.values()[i] + ": ");
                Scanner sc = new Scanner(System.in);
                amount = sc.nextInt();
            } while (amount <= 0);

            banknotesToDeposit.put(AtmStorage.Banknote.values()[i], amount);

        }

        this.atmStorage.depositCash(banknotesToDeposit);

    }

    @Override
    public void checkBalance() {
        System.out.println("ATM balance: ");
        for (int i = 0; i < AtmStorage.Banknote.values().length; i++) {
            AtmStorage.Banknote banknote = AtmStorage.Banknote.values()[i];
            System.out.println(banknote + ": " + atmStorage.getStorageBanknotes().get(banknote));
        }

    }
}
