package ru.otus.java.hw02.runtime;

public class RuntimeMemoryCounter {
    public static long getObjectSize(Object object) throws Exception {
        runGC();
        //usedMemory();
        // Array to keep strong references to allocated objects
        final int count = 100000;
        Object[] objects = new Object[count];

        long heap1 = 0;
        // Allocate count+1 objects, discard the first one
        for (int i = 0; i < count; i++) {
                objects[i] = object;
        }
        runGC();

        long heap2 = usedMemory(); // Take an after heap snapshot:
        final int size = Math.round(((float) (heap2 - heap1)) / count);

        return size;
    }

    private static void runGC() throws Exception {
        // It helps to call Runtime.gc()
        // using several method calls:
        for (int r = 0; r < 10; ++r) _runGC();
    }

    private static void _runGC() throws Exception {
        long usedMem1 = usedMemory();
        long usedMem2 = Long.MAX_VALUE;
        for (int i = 0; (usedMem1 < usedMem2) && (i < 500); ++i) {
            s_runtime.runFinalization();
            s_runtime.gc();
            Thread.currentThread().yield();

            usedMem2 = usedMem1;
            usedMem1 = usedMemory();
        }
    }

    private static long usedMemory() {
        return s_runtime.totalMemory() - s_runtime.freeMemory();
    }

    private static final Runtime s_runtime = Runtime.getRuntime();
}
