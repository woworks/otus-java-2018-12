package ru.otus.java.hw08;

import ru.otus.java.hw08.aop.instrumentation.MyClassImpl;

/*
    java -javaagent:instrumentationDemo.jar -jar instrumentationDemo.jar
*/

public class Main {
    public static void main(String[] args) {
        MyClassImpl myClass = new MyClassImpl();
        myClass.secureAccess("Security Param");
        myClass.secureLoggedAccess("Security Param");
    }

}
