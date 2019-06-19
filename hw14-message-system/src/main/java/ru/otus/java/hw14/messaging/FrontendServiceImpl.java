package ru.otus.java.hw14.messaging;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.java.hw14.messagesystem.Address;
import ru.otus.java.hw14.messagesystem.MessageSystem;
import ru.otus.java.hw14.messaging.messages.MsgGlobalSearch;

import java.io.IOException;

public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final MessageSystemContext context;
    private Session session;

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void submitToSearch(String searchMessage, Session session) {
        this.session = session;
        context.getMessageSystem()
                .sendMessage(new MsgGlobalSearch(
                        context.getFrontAddress(),
                        context.getSearchAddress(),
                        searchMessage)
                );
    }

    @Override
    public void pushToWeb(String replyMessage) {
        try {
            this.session.getRemote().sendString(replyMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
