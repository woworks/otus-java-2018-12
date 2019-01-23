package ru.otus.java.hw02.agent;

import java.lang.instrument.Instrumentation;

public class MemoryCounterAgent {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation instrumentation) {
        MemoryCounterAgent.instrumentation = instrumentation;
    }

    public static long getSize(Object obj) {
        if (instrumentation == null) {
            throw new IllegalStateException("Agent not initialised");
        }
        return instrumentation.getObjectSize(obj);
    }
}