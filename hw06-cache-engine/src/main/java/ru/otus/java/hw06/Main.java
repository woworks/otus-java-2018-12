package ru.otus.java.hw06;

import ru.otus.java.hw06.cache.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //new CacheMain().eternalCacheExample();
        //new CacheMain().lifeCacheExample();
        //new Main().lifeCacheExample();
    }

    public void lifeCacheExample() throws InterruptedException {
        int size = 5;

        CacheEngine<Integer, String> cache = new SoftCacheEngineImpl<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

}
