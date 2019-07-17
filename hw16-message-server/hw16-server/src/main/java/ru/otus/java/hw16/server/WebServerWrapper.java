package ru.otus.java.hw16.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


class WebServerWrapper {
    /*private static MessageSystem messageSystem = new MessageSystem();

    private final int PORT = 8080;
    private final DBService dbService;
    private final TemplateProcessor templateProcessor;
    private final Server server = new Server(PORT);

    private final Address frontAddress;
    private final Address searchAddress;
    private final MessageSystemContext context;
    private final FrontendService frontendService;


    WebServerWrapper() {

        this.frontAddress = new Address("Frontend");
        this.searchAddress = new Address("Search");
        this.context = new MessageSystemContext(messageSystem);

        this.context.setFrontAddress(frontAddress);
        this.context.setSearchAddress(searchAddress);

        this.dbService = new HibernateDBServiceImpl();
        this.templateProcessor = new TemplateProcessor();
        new DBInitializerService(dbService).init();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new UserServlet(dbService, templateProcessor)), "/users");


        this.frontendService = new FrontendServiceImpl(context, frontAddress);
        this.frontendService.init();

        SearchService searchService = new SearchServiceImpl(context, searchAddress, dbService);
        searchService.init();

        HandlerList handlers = new HandlerList();
        // first element  is webSocket handler, second element is first handler
        handlers.setHandlers(new Handler[] {new SocketHandler(frontendService), servletContextHandler});
        server.setHandler(handlers);
    }



    void start() throws Exception {
        messageSystem.start();

        server.start();
        server.join();
    }*/

}
