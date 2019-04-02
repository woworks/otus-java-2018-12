package ru.otus.java.hw08;


import ru.otus.java.hw08.atm.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<AtmProcessor> atms = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            AtmStorage atmStorage = new AtmStorage();
            atmStorage.initBalance();
            AtmProcessor processor = new AtmProcessorImpl((long)i, atmStorage);
            atms.add(processor);
        }

        AtmDepartment atmDepartment = new AtmDepartment(atms);
        atmDepartment.saveAtmsState();

        int choice = 100;
        while (choice != 0) {

            try {
                choice = displayMenu();

                switch (choice) {
                    case 1:
                        atmDepartment.displayBalance();
                        break;
                    case 2:
                        atmDepartment.generateUserAtmActivity();
                        break;
                    case 3:
                        atmDepartment.restoreAtmsState();
                        break;
                    case 0:
                        System.out.println("Exiting..");
                        break;
                    default:
                        System.out.println("Incorrect input!");
                }

             } catch (InputMismatchException e) {
                System.out.println("Incorrect input!");
            }
        }
    }

    private static int displayMenu() {
        System.out.println("ATM Department Menu:");
        System.out.println("1) Display All ATM's Balance:");
        System.out.println("2) Generate user activity on different ATMs:");
        System.out.println("3) Restore ATMs state to initial:");
        System.out.println("0) Exit:");
        System.out.print("Your choice: ");

        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

}
