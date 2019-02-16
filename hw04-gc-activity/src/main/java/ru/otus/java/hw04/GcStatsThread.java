package ru.otus.java.hw04;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GcStatsThread extends Thread {

    @Override
    public void run() {

        while (!Main.isCrashed()) {
            displayStats();
        }
    }

    public static void displayStats() {
        List<GarbageCollectorMXBean> gcmxb = ManagementFactory.getGarbageCollectorMXBeans();

        System.out.println("=======================GC Stats========================");

        for (GarbageCollectorMXBean ob : gcmxb) {
            System.out.println("name of memory manager:" + ob.getName());
            System.out.println("Collection Time:" + ob.getCollectionTime());
            System.out.println("Collection Count:" + ob.getCollectionCount());
            String[] str = ob.getMemoryPoolNames();
            for (int i = 0; i < str.length; i++) {
                System.out.println(str[0].intern());
            }
        }

        System.out.println("====================Memory Pools Stats=====================");

        System.out.println(":: Pool name ::                     .:Usage:.");
        ManagementFactory.getMemoryPoolMXBeans().forEach(b ->
                System.out.println(String.format("%-25s %15s Kb", b.getName(), (b.getUsage().getUsed() >> 10)))

        );

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
