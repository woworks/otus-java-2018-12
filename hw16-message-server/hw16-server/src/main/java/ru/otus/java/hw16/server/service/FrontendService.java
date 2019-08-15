package ru.otus.java.hw16.server.service;


import org.eclipse.jetty.websocket.api.Session;
import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messagesystem.Addressee;

import java.util.List;
import java.util.UUID;


public interface FrontendService extends Addressee {
    void init();

    void submitToSearch(String searchMessage, Session session);

    void pushToWeb(String replyMessage, String sessionId);

    void addToResults(UUID requestId, List<UserDataSet> users);
}

