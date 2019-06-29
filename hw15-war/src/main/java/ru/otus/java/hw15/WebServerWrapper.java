package ru.otus.java.hw15;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.java.hw15.base.DBService;
import ru.otus.java.hw15.hibernate.DBInitializerService;
import ru.otus.java.hw15.messagesystem.Address;
import ru.otus.java.hw15.messagesystem.MessageSystem;
import ru.otus.java.hw15.messaging.*;

public class WebServerWrapper {
    private static MessageSystem messageSystem = new MessageSystem();

    private DBService dbService;

    private Address frontAddress;
    private Address searchAddress;
    private MessageSystemContext context;
    private FrontendService frontendService;

    public FrontendService getFrontendService() {
        return frontendService;
    }

    @Autowired
    WebServerWrapper(DBService dbService) {

        System.out.println("Init WebServerWrapper");

        this.frontAddress = new Address("Frontend");
        this.searchAddress = new Address("Search");
        this.context = new MessageSystemContext(messageSystem);

        this.context.setFrontAddress(frontAddress);
        this.context.setSearchAddress(searchAddress);

        new DBInitializerService(dbService).init();


        this.frontendService = new FrontendServiceImpl(context, frontAddress);
        this.frontendService.init();

        SearchService searchService = new SearchServiceImpl(context, searchAddress, dbService);
        searchService.init();

        messageSystem.start();


/*        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        HandlerList handlers = new HandlerList();
        // first element  is webSocket handler, second element is first handler
        handlers.setHandlers(new Handler[] {new SocketHandler(frontendService), servletContextHandler});
        server.setHandler(handlers);*/

    }

}
