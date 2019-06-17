package ru.otus.java.hw14.messaging.messages;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.java.hw14.datasets.UserDataSet;
import ru.otus.java.hw14.messagesystem.Address;
import ru.otus.java.hw14.messaging.SearchService;
import ru.otus.java.hw14.messaging.MsgToSearch;

import java.util.List;

public class MsgGlobalSearch extends MsgToSearch {
    private final String searchMessage;
    private final Session session;

    public MsgGlobalSearch(Address from, Address to, Session session, String searchMessage) {
        super(from, to);
        this.searchMessage = searchMessage;
        this.session = session;
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

        searchService.getMS().sendMessage(new MsgGlobalSearchAnswer(getTo(), getFrom(), session, replyMessage));
    }
}
