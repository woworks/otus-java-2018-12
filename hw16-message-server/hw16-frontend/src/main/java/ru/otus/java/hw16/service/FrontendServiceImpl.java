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
    private Map<String, Session> sessions = new HashMap<>();

    public FrontendServiceImpl(Address address) {
        this.address = address;
    }

    public void init() {
    }

    @Override
    public void submitToSearch(String searchMessage, Session session) {
        String sessionId = String.valueOf(session.hashCode());
        this.sessions.put(sessionId, session);

/*        context.getMessageSystem()
                .sendMessage(new MsgGlobalSearch(
                        context.getFrontAddress(),
                        context.getSearchAddress(),
                        searchMessage, sessionId)
                );*/
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
        System.out.println("Users are back to FRONT requestId :" + requestId);
        System.out.println("Users are back to FRONT users:" + users);
    }

    @Override
    public Address getAddress() {
        return address;
    }

}
