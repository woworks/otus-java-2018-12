package ru.otus.java.hw16.server.base;


import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.Addressee;


public interface ServerService extends Addressee {
    void ping(Address to);
}

