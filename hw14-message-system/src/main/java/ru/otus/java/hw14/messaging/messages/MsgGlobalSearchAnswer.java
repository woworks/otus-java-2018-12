package ru.otus.java.hw14.messaging.messages;


import org.eclipse.jetty.websocket.api.Session;
import ru.otus.java.hw14.messagesystem.Address;
import ru.otus.java.hw14.messaging.FrontendService;
import ru.otus.java.hw14.messaging.MsgToFrontend;

import java.io.IOException;

public class MsgGlobalSearchAnswer extends MsgToFrontend {
    private final String replyMessage;
    private final Session session;

    public MsgGlobalSearchAnswer(Address from, Address to, Session session, String replyMessage) {
        super(from, to);
        this.replyMessage = replyMessage;
        this.session = session;
    }

    @Override
    public void exec(FrontendService frontendService) {
        try {
            this.session.getRemote().sendString(replyMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
