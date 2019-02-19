package ru.otus.java.hw04;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        List<LocalDateTime> list = new ArrayList<>();
        Thread statsThread = new GcStatsThread();
        statsThread.setDaemon(true);
        statsThread.start();
        int j = 0;
        for (j = 0; j < Integer.MAX_VALUE; j++) {
            list.add(LocalDateTime.now());
            if (j % 100_000 == 0) {
                System.out.println("-------------------=List elements size: " + j + "=-------------------");
                Thread.sleep(4_000);
            }

        }
    }
}