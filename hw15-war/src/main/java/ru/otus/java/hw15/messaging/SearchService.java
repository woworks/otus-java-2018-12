package ru.otus.java.hw15.messaging;


import ru.otus.java.hw15.base.DBService;
import ru.otus.java.hw15.messagesystem.Addressee;

public interface SearchService extends Addressee {

    void init();

    DBService getDBService();

}
