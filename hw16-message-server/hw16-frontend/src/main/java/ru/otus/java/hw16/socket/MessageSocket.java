package ru.otus.java.hw16.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import ru.otus.java.hw16.server.service.FrontendService;

import java.io.IOException;

@WebSocket
public class MessageSocket {

    private static final Logger LOG = LogManager.getLogger(MessageSocket.class);
    private Session session;
    private FrontendService frontendService;

    public MessageSocket(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        LOG.info("Connect: {}", session.getRemoteAddress().getAddress());
        LOG.info("Connect session: {}", session.hashCode());
        LOG.info("Connect: instance = {}", this.hashCode());
        try {
            this.session = session;
            this.session.getRemote().sendString(buildMessages());
        } catch (IOException e) {
            LOG.error("Could not send message to remote web ui", e);
        }
    }

    @OnWebSocketMessage
    public void onText(String searchMessage) {
        LOG.info("received message from UI: {}", searchMessage);
        try {
            frontendService.submitToSearch(searchMessage, session);
        } catch (Exception e) {
            LOG.error("Could not submit to search", e);
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOG.info("Close: statusCode= {}, reason= {}", statusCode, reason);
    }

    private String buildMessages() {
        StringBuilder builder = new StringBuilder("name search connected \n");
        return builder.toString();
    }

    @OnWebSocketError
    public void onWebSocketError(Throwable error) {
        LOG.error("--------------------");
        LOG.error(error.getCause());
        LOG.error("--------------------");

    }


}
