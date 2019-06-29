package ru.otus.java.hw15.socket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.otus.java.hw15.WebServerWrapper;
import ru.otus.java.hw15.messaging.FrontendService;

public class MessageSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;
    private FrontendService frontendService;

    public MessageSocketHandler(WebServerWrapper webServerWrapper) {
        System.out.println("INIT1111: MessageSocketHandler");
        this.frontendService = webServerWrapper.getFrontendService();
        System.out.println("INIT2222: MessageSocketHandler");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("INIT3333: afterConnectionEstablished; session = " + session);
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("received message from UI: " + message);
        try {
            frontendService.submitToSearch(message.getPayload(), session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
