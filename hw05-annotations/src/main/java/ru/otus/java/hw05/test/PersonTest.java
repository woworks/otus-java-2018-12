package ru.otus.java.hw05.test;

import ru.otus.java.hw05.annotations.After;
import ru.otus.java.hw05.annotations.Before;
import ru.otus.java.hw05.annotations.Test;
import ru.otus.java.hw05.tester.Assert;

import java.util.Random;

public class PersonTest {

    private Person person;

    @Before
    public void setup() {
        this.person = PersonFactory.getPerson(new Random().nextInt());
        System.out.println("@Before:: setup: person id = " + person.getId());
    }

    @After
    public void shutdown() {
        System.out.println("@After:: shutdown");
        this.person = null;
    }

    @Test
    public void notNegativePersonIdTest() {
        System.out.println("@Test:: notNegativePersonIdTest: person.getId = " + person.getId());
        Assert.isNotNegative(person.getId());
    }

    @Test
    public void notNullPersonTest() {
        System.out.println("@Test:: NotNullPersonTest");
        Assert.isNotNull(person);
    }

    @Test
    public void goodEmailTest() {
        System.out.println("@Test:: goodEmailTest");
        String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Assert.matchPattern(emailRegex, person.getEmail());
    }

    @Test
    public void correctAgeTest () {
        System.out.println("@Test:: correctAgeTest");
        Assert.isNotNegative(person.getAge());
    }
}
