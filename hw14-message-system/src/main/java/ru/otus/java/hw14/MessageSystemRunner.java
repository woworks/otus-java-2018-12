package ru.otus.java.hw14;

import ru.otus.java.hw14.base.DBService;
import ru.otus.java.hw14.messagesystem.Address;
import ru.otus.java.hw14.messaging.*;

public class MessageSystemRunner {

    private DBService dbService;

    private static ru.otus.java.hw14.messagesystem.MessageSystem messageSystem = new ru.otus.java.hw14.messagesystem.MessageSystem();

    private Address frontAddress = new Address("Frontend");
    private Address searchAddress = new Address("Search");
    private MessageSystemContext context = new MessageSystemContext(messageSystem);
    private FrontendService frontendService;

    MessageSystemRunner(DBService dbService) {
        this.dbService = dbService;

        context.setFrontAddress(frontAddress);
        context.setSearchAddress(searchAddress);
    }

    void start() {
        frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();

        SearchService searchService = new SearchServiceImpl(context, searchAddress, dbService);
        searchService.init();

        messageSystem.start();
    }

    public FrontendService getFrontendService() {
        return frontendService;
    }
}
