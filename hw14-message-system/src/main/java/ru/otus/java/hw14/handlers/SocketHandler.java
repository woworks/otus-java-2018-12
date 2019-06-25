package ru.otus.java.hw14.handlers;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.java.hw14.messaging.FrontendService;
import ru.otus.java.hw14.socket.MessageSocket;

public class SocketHandler extends WebSocketHandler {

    private FrontendService frontendService;
    public SocketHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.setCreator((servletUpgradeRequest, servletUpgradeResponse) -> new MessageSocket(frontendService));
    }
}
