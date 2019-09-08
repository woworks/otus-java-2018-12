package ru.otus.java.hw08.aop.instrumentation;

import ru.otus.java.hw08.annotation.annotations.Log;

public class MyClassImpl {

    @Log
    public void secureLoggedAccess(String param) {
        System.out.println("secureLoggedAccess, param:" + param);
    }

    public void secureAccess(String param) {
        System.out.println("secureAccess, param:" + param);
    }

    @Override
    public String toString() {
        return "MyClassImpl{}";
    }
}
