package ru.otus.java.hw16.server.base;

import ru.otus.java.hw16.server.messagesystem.Address;

public class ServerServiceImpl implements ServerService {
    @Override
    public void ping(Address to) {
        System.out.println("ServerServiceImpl:: PING!");
    }

    @Override
    public Address getAddress() {
        return null;
    }

}
