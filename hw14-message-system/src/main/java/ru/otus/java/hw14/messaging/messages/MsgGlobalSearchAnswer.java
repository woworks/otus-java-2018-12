package ru.otus.java.hw14.messaging.messages;


import ru.otus.java.hw14.messagesystem.Address;
import ru.otus.java.hw14.messaging.FrontendService;
import ru.otus.java.hw14.messaging.MsgToFrontend;

public class MsgGlobalSearchAnswer extends MsgToFrontend {
    private final String replyMessage;

    public MsgGlobalSearchAnswer(Address from, Address to, String replyMessage) {
        super(from, to);
        this.replyMessage = replyMessage;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.pushToWeb(replyMessage);
    }
}
