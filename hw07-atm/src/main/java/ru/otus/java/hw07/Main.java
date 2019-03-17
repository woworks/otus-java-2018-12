package ru.otus.java.hw07;


import ru.otus.java.hw07.atm.*;

import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {

        User user = new User();
        user.setFirstName("Jack");
        user.setFirstName("Doe");
        user.setCardNo("1234 5678 1234 5678");
        user.setPin("1111");
        user.setBalance(new CashAmount(100));

        AtmProcessor processor = new AtmProcessorImpl(user);
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
