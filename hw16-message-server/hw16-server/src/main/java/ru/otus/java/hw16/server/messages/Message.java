package ru.otus.java.hw16.server.messages;

import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.Addressee;

public abstract class Message {

    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);

}
