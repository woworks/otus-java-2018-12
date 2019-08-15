package ru.otus.java.hw16.server.messages;


import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.Addressee;
import ru.otus.java.hw16.server.workers.MessageWorker;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

public abstract class MsgToSearch extends Message {
    public MsgToSearch(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee, MessageWorker worker) {
        if (addressee instanceof SearchService) {
            exec((SearchService) addressee, (SocketMessageWorker)worker);
        }
    }

    public abstract void exec(SearchService searchServiceService, SocketMessageWorker client);
}
