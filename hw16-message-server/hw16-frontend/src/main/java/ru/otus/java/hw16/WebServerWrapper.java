package ru.otus.java.hw16;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.java.hw16.freemarker.TemplateProcessor;
import ru.otus.java.hw16.handlers.SocketHandler;
import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.service.FrontendService;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;
import ru.otus.java.hw16.service.FrontendServiceImpl;
import ru.otus.java.hw16.service.InOutMessagesService;
import ru.otus.java.hw16.service.RemoteDBServiceImpl;
import ru.otus.java.hw16.servlet.UserServlet;


class WebServerWrapper {

    private static final int PORT = 8080;
    private final TemplateProcessor templateProcessor;
    private final Server server = new Server(PORT);
    private final FrontendService frontendService;
    private final DBService dbService;
    private final InOutMessagesService inOutMessagesService;

    WebServerWrapper(SocketMessageWorker client) {
        this.inOutMessagesService = new InOutMessagesService(client);
        this.dbService = new RemoteDBServiceImpl(inOutMessagesService);

        this.templateProcessor = new TemplateProcessor();
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new UserServlet(dbService, templateProcessor)), "/users");

        this.frontendService = new FrontendServiceImpl(new Address(Address.Type.FRONTEND, FrontClientMain.localport), inOutMessagesService);
        this.frontendService.init();


        HandlerList handlers = new HandlerList();
        // first element  is webSocket handler, second element is first handler
        handlers.setHandlers(new Handler[] {new SocketHandler(frontendService), servletContextHandler});
        server.setHandler(handlers);

        this.inOutMessagesService.start();

    }



    void start() throws Exception {
        server.start();
        server.join();
    }

}
