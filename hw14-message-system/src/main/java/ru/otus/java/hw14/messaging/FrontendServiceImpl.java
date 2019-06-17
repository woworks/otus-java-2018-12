package ru.otus.java.hw14.messaging;

import ru.otus.java.hw14.messagesystem.Address;
import ru.otus.java.hw14.messagesystem.MessageSystem;

public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final MessageSystemContext context;

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
