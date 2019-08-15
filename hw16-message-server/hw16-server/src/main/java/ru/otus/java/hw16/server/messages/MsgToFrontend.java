package ru.otus.java.hw16.server.messages;


import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.Addressee;
import ru.otus.java.hw16.server.service.FrontendService;
import ru.otus.java.hw16.server.workers.MessageWorker;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.util.UUID;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {

    private final UUID requestId;

    public MsgToFrontend(Address from, Address to, UUID requestId) {
        super(from, to);
        this.requestId = requestId;
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

    public UUID getRequestId() {
        return requestId;
    }
}