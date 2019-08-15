package ru.otus.java.hw16.server.messages;

import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.Addressee;
import ru.otus.java.hw16.server.workers.MessageWorker;

import java.util.UUID;

public abstract class Message {

    private final Address from;
    private final Address to;
    private final String type;
    private final UUID id;

    public Message(Address from, Address to) {
        this.id = UUID.randomUUID();
        this.from = from;
        this.to = to;
        this.type = this.getClass().getTypeName();
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public abstract void exec(Addressee addressee, MessageWorker worker);

}
