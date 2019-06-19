package ru.otus.java.hw14.messaging;


import org.eclipse.jetty.websocket.api.Session;
import ru.otus.java.hw14.messagesystem.Addressee;


public interface FrontendService extends Addressee {
    void init();

    void submitToSearch(String searchMessage, Session session);

    void pushToWeb(String replyMessage);
}

