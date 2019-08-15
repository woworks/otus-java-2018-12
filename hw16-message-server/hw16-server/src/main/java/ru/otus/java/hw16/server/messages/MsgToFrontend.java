package ru.otus.java.hw16.server.messages;


import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.Addressee;
import ru.otus.java.hw16.server.service.FrontendService;
import ru.otus.java.hw16.server.workers.MessageWorker;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee, MessageWorker worker) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee, (SocketMessageWorker) worker);
        } else {
            //todo error!
        }

    }

    public abstract void exec(FrontendService frontendService, SocketMessageWorker worker);
}