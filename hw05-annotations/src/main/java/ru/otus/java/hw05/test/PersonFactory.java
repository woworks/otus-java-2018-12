package ru.otus.java.hw05.test;

class PersonFactory {

    private PersonFactory(){}

    public static Person getPerson(int i) {
        Person person = new Person();
        person.setId(i);
        person.setFirstName("Alex" + i);
        person.setLastName("Johnson" + i);
        person.setEmail("aj" + i + "@google.com");
        person.setAge(i);
        return person;
    }
}
