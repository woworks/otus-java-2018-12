package ru.otus.java.hw10.reflection;

import java.lang.reflect.Field;
import java.util.*;

public class Reflector {


    public Map<String, Object> getFields(Object object) {
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

    public void populateFields(Object object, Map<String, Object> valuesMap) {

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
