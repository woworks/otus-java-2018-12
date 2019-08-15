package ru.otus.java.hw16.server.messaging.messages;

import ru.otus.java.hw16.server.messages.MsgToFrontend;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.service.FrontendService;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

public class MsgGlobalSearchAnswer extends MsgToFrontend {
    private final String replyMessage;
    private final String sessionId;


    public MsgGlobalSearchAnswer(Address from, Address to, String replyMessage, String sessionId) {
        super(from, to);
        this.replyMessage = replyMessage;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(FrontendService frontendService, SocketMessageWorker worker) {
        System.out.println("MsgGlobalSearchAnswer:: exec..!");
        frontendService.pushToWeb(replyMessage, sessionId);
    }
}
