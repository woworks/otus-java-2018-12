package ru.otus.java.hw15.messaging;

import ru.otus.java.hw15.base.DBService;
import ru.otus.java.hw15.messagesystem.Address;
import ru.otus.java.hw15.messagesystem.MessageSystem;

public class SearchServiceImpl implements SearchService {
    private final Address address;
    private final MessageSystemContext context;
    private final DBService dbService;

    public SearchServiceImpl(MessageSystemContext context, Address address, DBService dbService) {
        this.context = context;
        this.address = address;
        this.dbService = dbService;
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

    @Override
    public DBService getDBService() {
        return dbService;
    }
}
