package ru.otus.java.hw04;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class Main {

    private static boolean crashed = false;

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.nanoTime();

        List<LocalDateTime> list = new ArrayList<>();
/*        Thread statsThread = new GcStatsThread();
        statsThread.start();*/
        int j = 0;
        try {
            for (j = 0; j < Integer.MAX_VALUE; j++) {
                list.add(LocalDateTime.now());
                if (j % 100_000 == 0){
                    //System.out.println("-------------------=List elements size: " + j + "=-------------------");
                    Thread.sleep(4_000);
                }

            }

        } catch (OutOfMemoryError e) {
            crashed = true;
            long endTime = System.nanoTime();
            System.out.println("-------------------OutOfMemoryError!-------------------");
            System.out.println("-------------------That's all folks!-------------------");
            GcStatsThread.displayStats();
            System.out.println("Number of list items that were added before the crash: " + j);
            System.out.println("Time taken: " + (endTime - startTime) / 1_000_000_000 + "sec") ;
        }
    }

    public static boolean isCrashed() {
        return crashed;
    }
}