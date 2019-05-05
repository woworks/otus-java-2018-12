package ru.otus.java.hw13.sort;

public class QuickSort {
    public void quickSort(int[] A) {
        quickSort(A, 0, A.length - 1);
    }

    private void quickSort(int[] A, int low, int high) {
        if (low < high + 1) {
            int pivot = SortUtil.partition(A, low, high);
            quickSort(A, low, pivot - 1);
            quickSort(A, pivot + 1, high);
        }
    }

}
