package ru.otus.java.hw05;

import ru.otus.java.hw05.test.CalculationTest;
import ru.otus.java.hw05.test.PersonTest;
import ru.otus.java.hw05.tester.TesterRunner;

public class Main {
    public static void main(String[] args) {
        TesterRunner.runTest(PersonTest.class);
        TesterRunner.runTest(CalculationTest.class);
    }

}
