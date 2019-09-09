package ru.otus.java.hw08;

import ru.otus.java.hw08.aop.instrumentation.MyClassImpl;

/*
    java -javaagent:hw08-auto-log-shaded.jar -jar hw08-auto-log-shaded.jar
*/

public class Main {
    public static void main(String[] args) {
        MyClassImpl myClass = new MyClassImpl();
        myClass.secureAccess("secureAccess Param Value");
        myClass.secureLoggedAccess("secureLoggedAccess Param Value");
        myClass.secureLoggedAccessInt(42);
    }

}