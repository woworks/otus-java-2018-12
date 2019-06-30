package ru.otus.java.hw15.messaging.messages;


import ru.otus.java.hw15.messagesystem.Address;
import ru.otus.java.hw15.messaging.FrontendService;
import ru.otus.java.hw15.messaging.MsgToFrontend;

public class MsgGlobalSearchAnswer extends MsgToFrontend {
    private final String replyMessage;
    private final String sessionId;


    public MsgGlobalSearchAnswer(Address from, Address to, String replyMessage, String sessionId) {
        super(from, to);
        this.replyMessage = replyMessage;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.pushToWeb(replyMessage, sessionId);
    }
}
