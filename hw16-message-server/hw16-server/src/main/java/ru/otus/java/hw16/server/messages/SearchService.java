package ru.otus.java.hw16.server.messages;


import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.messagesystem.Addressee;

public interface SearchService extends Addressee {

    void init();

    DBService getDBService();

}
