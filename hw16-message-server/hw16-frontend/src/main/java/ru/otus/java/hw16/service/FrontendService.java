package ru.otus.java.hw16.service;


import org.eclipse.jetty.websocket.api.Session;
import ru.otus.java.hw16.server.messagesystem.Addressee;


public interface FrontendService extends Addressee {
    void init();

    void submitToSearch(String searchMessage, Session session);

    void pushToWeb(String replyMessage, String sessionId);
}

