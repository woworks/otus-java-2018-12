package ru.otus.java.hw02.app;

import ru.otus.java.hw02.agent.MemoryCounterAgent;

import java.math.BigDecimal;
import java.util.*;

public class AgentTester {
    public static void main(String[] args) {
        System.out.println("Single Objects");
        printObjectSize(new Object());
        printObjectSize(new A(0, "Josh", "Wink", 22));
        printObjectSize(1);
        printObjectSize(1L);
        printObjectSize(1.1);
        printObjectSize(false);
        printObjectSize("String");
        printObjectSize('C');
        printObjectSize(Calendar.getInstance());
        printObjectSize(new BigDecimal("999999999999999.999"));
        printObjectSize(new StringBuilder(1000));
        printObjectSize(new Exception());
        printObjectSize(A.Colors.BLUE);

        System.out.println("\nComplex objects");
        printObjectSize(new Integer[100]);
        printObjectSize(Arrays.asList("one", "two", "three"));
        printObjectSize(new HashMap<String, String>());
        printObjectSize(new HashSet<Long>());
    }

    public static void printObjectSize(Object obj) {
        System.out.println(String.format("\t%s, size=%s", obj.getClass()
                .getSimpleName(), MemoryCounterAgent.getSize(obj)));
    }
}

class A {
    private int id;
    private String name;
    private String lastName;
    private Integer age;

    A(Integer id, String name, String lastName, Integer age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    A() {
        this(2, "Jack", "Daniels", 44);
    }

    enum Colors {RED, GREEN, BLUE}
}
