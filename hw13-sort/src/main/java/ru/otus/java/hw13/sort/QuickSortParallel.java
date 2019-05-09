package ru.otus.java.hw13.sort;

import java.util.concurrent.atomic.AtomicInteger;

public class QuickSortParallel extends Thread {

    private static final int numThreads = 4;
    private static final AtomicInteger count = new AtomicInteger(0);
    private int[] A;
    private int low;
    private int high;

    public QuickSortParallel(int[] A) {
        this.A = A;
        this.low = 0;
        this.high = A.length - 1;
    }
    private QuickSortParallel(int[] A, int low, int high) {
        this.A = A;
        this.low = low;
        this.high = high;
    }

    public void doQuickSort() {
        try {
            quickSort(this.A, 0, A.length - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void quickSort(int[] A, int low, int high) throws InterruptedException {
        //System.out.println(Thread.currentThread().getName() + "::quickSort: low:" + low +";high="+high);
        if (low < high + 1) {
            int pivot = SortUtil.partition(A, low, high);

            Thread firstPartQuickSort = null;
            Thread secondPartQuickSort = null;

            if (count.get() < numThreads) {
                count.incrementAndGet();
                firstPartQuickSort = new QuickSortParallel(this.A, low, pivot - 1);
                firstPartQuickSort.start();
            } else {
                quickSort(this.A, low, pivot - 1);
            }

            if (count.get() < numThreads) {
                count.incrementAndGet();
                secondPartQuickSort = new QuickSortParallel(this.A,pivot + 1, high);
                secondPartQuickSort.start();
            } else {
                quickSort(this.A,pivot + 1, high);
            }


            if (firstPartQuickSort != null) {
                firstPartQuickSort.join();
                count.decrementAndGet();
            }

            if (secondPartQuickSort != null) {
                secondPartQuickSort.join();
                count.decrementAndGet();
            }
        }
    }


    @Override
    public void run() {
        try {
            this.quickSort(A, this.low, this.high);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
