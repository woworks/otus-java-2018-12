package ru.otus.java.hw16.server.messaging.messages;

import ru.otus.java.hw16.server.messages.MsgToFrontend;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.service.FrontendService;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.util.UUID;

public class MsgGlobalSearchReply extends MsgToFrontend {
    private final String replyMessage;
    private final String sessionId;


    public MsgGlobalSearchReply(Address from, Address to, String replyMessage, String sessionId, UUID requestId) {
        super(from, to, requestId);
        this.replyMessage = replyMessage;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(FrontendService frontendService, SocketMessageWorker worker) {
        frontendService.pushToWeb(replyMessage, sessionId);
    }

    public String getReplyMessage() {
        return replyMessage;
    }
}
