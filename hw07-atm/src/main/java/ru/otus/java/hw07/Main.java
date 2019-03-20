package ru.otus.java.hw07;


import ru.otus.java.hw07.atm.*;

import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {

        AtmStorage atm = new AtmStorage();
        atm.initBalance();

        AtmProcessor processor = new AtmProcessorImpl(atm);
        int choice = 100;
        while (choice != 0) {

            try {
                choice = processor.displayMenu();

                switch (choice) {
                    case 1:
                        processor.checkBalance();
                        break;
                    case 2:
                        processor.depositCash();
                        break;
                    case 3:
                        processor.withdrawCash();
                        break;
                    case 0:
                        System.out.println("Exiting..");
                        break;
                    default:
                        System.out.println("Incorrect input!");
                }

            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input!");
            }
        }
    }

}
