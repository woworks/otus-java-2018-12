package ru.otus.java.hw11.reflection;

import java.lang.reflect.Field;
import java.util.*;

public class Reflector {

    private Reflector() {}

    private static Map<Class, Map<String, Class>> classCache = new HashMap<>();

    public static Map<String, Class> getFields(Object object) {
        if (classCache.containsKey(object.getClass())) {
            return classCache.get(object.getClass());
        } else {
            Map<String, Class> fields = getObjectFields(object);
            classCache.put(object.getClass(), fields);
            return fields;
        }
    }

    private static Map<String, Class> getObjectFields(Object object) {
        Class objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        Map<String, Class> map = new HashMap<>(fields.length);

        Arrays.stream(fields)
                .forEach(
                        field -> {
                            field.setAccessible(true);
                                map.put(field.getName(), field.getType());
                        });

        return map;
    }

    public static Collection<Object> getObjectFieldValues(Object object) {
        Class objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        Collection<Object> map = new ArrayList<>(fields.length);

        Arrays.stream(fields)
                .forEach(
                        field -> {
                            field.setAccessible(true);
                            try {
                                map.add(field.get(object));
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

    public static boolean isNumber(Class clazz) {
        return Number.class.isAssignableFrom(clazz) || isPrimitiveNumber(clazz);
    }

    private static boolean isPrimitiveNumber(Class clazz) {
        return  clazz.equals(int.class) ||
                clazz.equals(long.class) ||
                clazz.equals(double.class) ||
                clazz.equals(float.class) ||
                clazz.equals(byte.class);
    }

}
