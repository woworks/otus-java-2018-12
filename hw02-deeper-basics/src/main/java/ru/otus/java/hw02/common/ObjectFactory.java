package ru.otus.java.hw02.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class ObjectFactory {

    public static List<Object> getObjects() {

        List<Object> objects = new ArrayList<>();

        //objects.add(new Object());
        objects.add(new A(0, "Josh", "Wink", 22));
        objects.add(1);
        objects.add(1L);
        objects.add(1.1);
        objects.add(false);
        objects.add("String");
        objects.add('C');
        objects.add(Calendar.getInstance());
        objects.add(new BigDecimal("999999999999999.999"));
        objects.add(new StringBuilder(1000).append("Hello"));
        objects.add(new Exception());
        objects.add(A.Colors.BLUE);
        objects.add(new Integer[100]);
        objects.add(Arrays.asList("one", "two", "three"));
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");map.put(2, "Two");map.put(3, "Three");
        objects.add(map);
        Set<String> set = new HashSet<>();
        set.add("String1");set.add("String2");set.add("String3");
        objects.add(set);


        return objects;

    }
}

class A implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private Integer age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

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

