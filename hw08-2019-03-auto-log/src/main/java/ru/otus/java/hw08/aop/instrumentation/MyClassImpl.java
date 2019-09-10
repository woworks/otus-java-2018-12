package ru.otus.java.hw08.aop.instrumentation;

import ru.otus.java.hw08.annotation.annotations.Log;

public class MyClassImpl {

    @Log
    public void secureLoggedAccess(String secureLoggedAccessParam) {
        System.out.println("secureLoggedAccess, param: " + secureLoggedAccessParam);
    }

    @Log
    public void secureLoggedAccessInt(Integer secureLoggedAccessIntParam) {
        System.out.println("secureLoggedAccess, param:" + secureLoggedAccessIntParam);
    }

    public void secureAccess(String secureAccessParam) {
        System.out.println("secureAccess, secureAccessParam:" + secureAccessParam);
    }

    @Override
    public String toString() {
        return "MyClassImpl{}";
    }
}