package ru.otus.java.hw13;


import ru.otus.java.hw13.sort.QuickSort;
import ru.otus.java.hw13.sort.QuickSortParallel;

import java.util.Random;

public class Main {

    private static final Random random = new Random();
    private static final int ARRAY_SIZE = 10_000_000;

    public static void main(String[] args) throws InterruptedException {
        runSimpleQuickSort();
        Thread.sleep(1000);
        runParallelQuickSort();
    }

    private static void runSimpleQuickSort() {
        int[] A = new int[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            A[i] = random.nextInt();
        }

        long startParallel = System.currentTimeMillis();
        new QuickSort().quickSort(A);
        long endParallel = System.currentTimeMillis();

        System.out.println("\n\nSimple Sort time = " + (endParallel - startParallel) + " millis");
        //System.out.println(Arrays.toString(A));
    }

    private static void runParallelQuickSort() {
        int[] A = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            A[i] = random.nextInt();
        }

        long startParallel = System.currentTimeMillis();
        new QuickSortParallel(A).doQuickSort();
        long endParallel = System.currentTimeMillis();

        System.out.println("\n\nParalllel Sort time = " + (endParallel - startParallel) + " millis");

        //System.out.println(Arrays.toString(A));
    }
}
