package ru.otus.java.hw13.sort;

import java.util.concurrent.ThreadLocalRandom;

class SortUtil {

    private SortUtil() {
    }

    private static void swap(int[] A, int index1, int index2) {
        int temp = A[index1];
        A[index1] = A[index2];
        A[index2] = temp;
    }

    static int partition(int[] A, int low, int high) {
        SortUtil.swap(A, low, getPivot(low, high));
        int border = low + 1;
        for (int i = border; i <= high; i++) {
            if (A[i] < A[low]) {
                SortUtil.swap(A, i, border++);
            }
        }
        SortUtil.swap(A, low, border - 1);

        return border - 1;
    }

    private static int getPivot(int low, int high) {
        return ThreadLocalRandom.current().nextInt((high - low) + 1) + low;
    }
}
