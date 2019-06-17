package ru.otus.java.hw14.socket;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import ru.otus.java.hw14.base.DBService;
import ru.otus.java.hw14.di.BasicModule;
import ru.otus.java.hw14.messagesystem.Address;
import ru.otus.java.hw14.messagesystem.MessageSystem;
import ru.otus.java.hw14.messaging.*;
import ru.otus.java.hw14.messaging.messages.MsgGlobalSearch;

import java.io.IOException;

@WebSocket
public class MessageSocket {

    @Inject
    private DBService dbService;
    private Session session;


    private static MessageSystem messageSystem = new MessageSystem();

    private Address frontAddress = new Address("Frontend");
    private Address searchAddress = new Address("Search");
    private MessageSystemContext context = new MessageSystemContext(messageSystem);

    public MessageSocket() {

        Injector injector = Guice.createInjector(new BasicModule());
        injector.injectMembers(this);

        context.setFrontAddress(frontAddress);
        context.setSearchAddress(searchAddress);

        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();

        SearchService searchService = new SearchServiceImpl(context, searchAddress, dbService);
        searchService.init();

        messageSystem.start();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        System.out.println("Connect session: " + session.hashCode());
        System.out.println("Connect: instance = " + this.hashCode());
        System.out.println("Connect: message instance = " + messageSystem.hashCode());
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
            messageSystem.sendMessage(new MsgGlobalSearch(frontAddress, searchAddress, session, searchMessage));
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
