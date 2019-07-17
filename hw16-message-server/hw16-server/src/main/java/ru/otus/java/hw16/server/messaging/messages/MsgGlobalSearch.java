package ru.otus.java.hw16.server.messaging.messages;

import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messages.MsgToSearch;
import ru.otus.java.hw16.server.messages.SearchService;

import java.util.List;

public class MsgGlobalSearch extends MsgToSearch {
    private final String searchMessage;
    private final String sessionId;

    public MsgGlobalSearch(Address from, Address to, String searchMessage, String sessionId) {
        super(from, to);
        this.searchMessage = searchMessage;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(SearchService searchService) {
        System.out.println("Searching for..." + searchMessage);


        List<UserDataSet> userDataSets = searchService.getDBService().findByName(searchMessage);
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Found reply for " + searchMessage  + " = " + userDataSets);

        StringBuilder builder = new StringBuilder();
        userDataSets.forEach(user ->  builder.append(user.getId() + ":" + user.getName() + ";"));

        String replyMessage = builder.toString();

        searchService.getMS().sendMessage(new MsgGlobalSearchAnswer(getTo(), getFrom(), replyMessage, sessionId));
    }
}
