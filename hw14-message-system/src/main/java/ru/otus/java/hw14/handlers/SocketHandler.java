package ru.otus.java.hw14.handlers;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.java.hw14.MessageSystemRunner;
import ru.otus.java.hw14.socket.MessageSocket;

public class SocketHandler extends WebSocketHandler {

    private MessageSystemRunner messageSystem;
    public SocketHandler(MessageSystemRunner messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.setCreator((servletUpgradeRequest, servletUpgradeResponse) -> new MessageSocket(messageSystem));
    }
}
