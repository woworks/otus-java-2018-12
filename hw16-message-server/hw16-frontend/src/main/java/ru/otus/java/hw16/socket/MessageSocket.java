package ru.otus.java.hw16.socket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import ru.otus.java.hw16.service.FrontendService;

import java.io.IOException;

@WebSocket
public class MessageSocket {

    private Session session;
    private FrontendService frontendService;

    public MessageSocket(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        System.out.println("Connect session: " + session.hashCode());
        System.out.println("Connect: instance = " + this.hashCode());
        try {
            this.session = session;
            this.session.getRemote().sendString(buildMessages());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onText(String searchMessage) {
        System.out.println("received message from UI: " + searchMessage);
        try {
            frontendService.submitToSearch(searchMessage, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    private String buildMessages() {
        StringBuilder builder = new StringBuilder("global search connected \n");
        return builder.toString();
    }

    @OnWebSocketError
    public void onWebSocketError(Throwable error) {
        System.out.println("--------------------");
        System.out.println(error.getCause());
        System.out.println("--------------------");

    }


}
