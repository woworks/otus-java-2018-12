package ru.otus.java.hw15.messaging;


import org.springframework.web.socket.WebSocketSession;
import ru.otus.java.hw15.messagesystem.Addressee;


public interface FrontendService extends Addressee {
    void init();

    void submitToSearch(String searchMessage, WebSocketSession session);

    void pushToWeb(String replyMessage, String sessionId);
}

