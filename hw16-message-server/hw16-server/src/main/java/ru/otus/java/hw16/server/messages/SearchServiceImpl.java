package ru.otus.java.hw16.server.messages;

import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.messages.SearchService;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.MessageSystem;
import ru.otus.java.hw16.server.messaging.MessageSystemContext;

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
