package ru.otus.java.hw10.reflection;

import java.lang.reflect.Field;
import java.util.*;

public class Reflector {

    private Reflector() {}

    private static Map<Class, Map<String, Object>> classCache = new HashMap<>();

    public static Map<String, Object> getFields(Object object) {
        if (classCache.containsKey(object.getClass())) {
            return classCache.get(object.getClass());
        } else {
            Map<String, Object> fields = getObjectFields(object);
            classCache.put(object.getClass(), fields);
            return fields;
        }
    }

    private static Map<String, Object> getObjectFields(Object object) {
        Class objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        Map<String, Object> map = new HashMap<>(fields.length);

        Arrays.stream(fields)
                .forEach(
                        field -> {
                            field.setAccessible(true);
                            try {
                                map.put(field.getName(), field.get(object));
                            } catch (final IllegalAccessException e) {
                                System.out.println("Could not get value for " + field.getName() + " field");
                            }
                        });

        return map;
    }

    public static void populateFields(Object object, Map<String, Object> valuesMap) {

        Class objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();

        Arrays.stream(fields)
                .forEach(
                        field -> {
                            field.setAccessible(true);
                            try {
                                field.set(object, valuesMap.get(field.getName()));
                            } catch (final IllegalAccessException e) {
                                System.out.println("Could not get value for " + field.getName() + " field");
                            }
                        });

    }
}
