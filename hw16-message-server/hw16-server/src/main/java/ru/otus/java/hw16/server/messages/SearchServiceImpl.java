package ru.otus.java.hw16.server.messages;

import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.messagesystem.Address;

public class SearchServiceImpl implements SearchService {
    private final Address address;
    private final DBService dbService;

    public SearchServiceImpl(Address address, DBService dbService) {
        this.address = address;
        this.dbService = dbService;
    }

    public void init() {
        //context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public DBService getDBService() {
        return dbService;
    }
}
