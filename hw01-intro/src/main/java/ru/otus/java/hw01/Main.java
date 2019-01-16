package ru.otus.java.hw01;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map items = ImmutableMap.of("coin", 3, "glass", 4, "pencil", 1);

        items.entrySet()
                .stream()
                .forEach(System.out::println);
    }

}
