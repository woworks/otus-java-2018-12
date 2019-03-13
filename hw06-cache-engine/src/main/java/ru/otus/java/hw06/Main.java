package ru.otus.java.hw06;

import ru.otus.java.hw06.cache.*;
import ru.otus.java.hw06.gc.GcStatsThread;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread statsThread = new GcStatsThread();
        statsThread.setDaemon(true);
        statsThread.start();
        new Main().lifeCacheExample();
    }

    private void lifeCacheExample() throws InterruptedException {
        int size = 9_000;

        CacheEngine<Integer, String> cache = new SoftCacheEngineImpl<>(size, 100_000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            //System.out.println("                                                 String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("                                                      Cache hits: " + cache.getHitCount());
        System.out.println("                                                      Cache misses: " + cache.getMissCount());

        Thread.sleep(1000);

        List<String> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            Thread.sleep(10);
            // eating memory by creating strings
            String s = new String(String.valueOf(i));
            list.add(s);
            if (i % 1000 == 0) {
                System.out.println("                                                      String for " + i + ": " + (element != null ? element.getValue() : "null"));
            }
        }

        System.out.println("                                                      Cache hits: " + cache.getHitCount());
        System.out.println("                                                      Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

}
