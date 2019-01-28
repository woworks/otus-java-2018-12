package ru.otus.java.hw02.runtime;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class RuntimeMemoryCounter2 {
    public static <T extends Serializable>long getObjectSize(T obj) throws InterruptedException {

        int size = 1_000_000;

        long mem = getMem();
        Object[] array = new Object[size];

        long mem2 = getMem();
        for (int i = 0; i < array.length; i++) {
            Object deepCopy = SerializationUtils.clone(obj);
            array[i] = deepCopy;
        }

        long mem3 = getMem();

        long memSize = (mem3 - mem) / array.length;
        array = null;

        return memSize;
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

}