package ru.otus.java.hw16.server.messaging;


import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.MessageSystem;

/**
 * Created by tully.
 */
public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontAddress;
    private Address searchAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getSearchAddress() {
        return searchAddress;
    }

    public void setSearchAddress(Address searchAddress) {
        this.searchAddress = searchAddress;
    }
}
