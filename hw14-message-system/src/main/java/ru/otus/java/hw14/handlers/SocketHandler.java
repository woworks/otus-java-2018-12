package ru.otus.java.hw14.handlers;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.java.hw14.socket.MessageSocket;

public class SocketHandler extends WebSocketHandler {
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(MessageSocket.class);
    }
}
