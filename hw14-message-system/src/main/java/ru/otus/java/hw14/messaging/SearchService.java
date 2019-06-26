package ru.otus.java.hw14.messaging;


import ru.otus.java.hw14.base.DBService;
import ru.otus.java.hw14.messagesystem.Addressee;

public interface SearchService extends Addressee {

    void init();

    DBService getDBService();

}
