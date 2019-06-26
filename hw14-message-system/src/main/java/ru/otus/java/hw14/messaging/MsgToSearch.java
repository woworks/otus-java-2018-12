package ru.otus.java.hw14.messaging;

import ru.otus.java.hw14.messagesystem.Address;
import ru.otus.java.hw14.messagesystem.Addressee;
import ru.otus.java.hw14.messagesystem.Message;


public abstract class MsgToSearch extends Message {
    public MsgToSearch(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof SearchService) {
            exec((SearchService) addressee);
        }
    }

    public abstract void exec(SearchService searchServiceService);
}
