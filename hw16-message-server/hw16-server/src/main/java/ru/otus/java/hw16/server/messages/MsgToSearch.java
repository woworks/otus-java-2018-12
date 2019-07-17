package ru.otus.java.hw16.server.messages;


import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.Addressee;
import ru.otus.java.hw16.server.messagesystem.Message;

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
