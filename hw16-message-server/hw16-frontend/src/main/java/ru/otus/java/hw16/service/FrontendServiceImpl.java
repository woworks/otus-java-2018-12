package ru.otus.java.hw16.service;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.service.FrontendService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final InOutMessagesService inOutMessagesService;
    private Map<String, Session> sessions = new HashMap<>();

    public FrontendServiceImpl(Address address, InOutMessagesService inOutMessagesService) {
        this.address = address;
        this.inOutMessagesService = inOutMessagesService;
    }

    public void init() {
        // no init needed
    }

    @Override
    public void submitToSearch(String searchMessage, Session session) {
        String sessionId = String.valueOf(session.hashCode());
        this.sessions.put(sessionId, session);

        String searchResult = this.inOutMessagesService.sendSearchMessage(searchMessage, sessionId);
        pushToWeb(searchResult, sessionId);
    }

    @Override
    public void pushToWeb(String replyMessage, String sessionId) {
        try {
            this.sessions.get(sessionId).getRemote().sendString(replyMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToResults(UUID requestId, List<UserDataSet> users) {
        // not used
    }

    @Override
    public Address getAddress() {
        return address;
    }

}
