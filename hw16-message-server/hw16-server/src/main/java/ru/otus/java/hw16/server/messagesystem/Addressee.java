package ru.otus.java.hw16.server.messagesystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
