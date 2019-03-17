package ru.otus.java.hw07.atm;

import java.util.Map;
import java.util.Scanner;

public class AtmProcessorImpl implements AtmProcessor {

    private User user;

    public AtmProcessorImpl(User user) {
        this.user = user;
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

        this.user.withdrawCash(amount);

        System.out.println("You'll get the following banknotes: ");
        for (Map.Entry<CashAmount.Banknote, Integer> entry: CashAmount.calculateBanknotes(amount).entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " banknotes");
        }
    }

    @Override
    public void depositCash() {

        int amount;
        do {
            System.out.print("Please correct input cash amount to deposit: ");
            Scanner sc = new Scanner(System.in);
            amount = sc.nextInt();
        } while (amount <= 0);

        this.user.depositCash(amount);

    }

    @Override
    public void checkBalance() {
        System.out.println("Your balance: " + user.getBalance().getValue());
    }
}
