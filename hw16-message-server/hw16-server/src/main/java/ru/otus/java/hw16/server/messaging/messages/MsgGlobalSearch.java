package ru.otus.java.hw16.server.messaging.messages;

import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messages.MsgToSearch;
import ru.otus.java.hw16.server.messages.SearchService;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

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
    public void exec(SearchService searchService, SocketMessageWorker worker) {
        System.out.println("Searching for..." + searchMessage);


        List<UserDataSet> userDataSets = searchService.getDBService().findByName(searchMessage);

        if (userDataSets.size() > 0) {
            System.out.println("INFO:: Found reply for " + searchMessage + " = " + userDataSets);
        } else {
            System.out.println("WARN:: Nothing found for " + searchMessage);
        }

        StringBuilder builder = new StringBuilder();
        userDataSets.forEach(user ->  builder.append(user.getId() + ":" + user.getName() + ";"));

        String replyMessage = builder.toString();

        worker.send(new MsgGlobalSearchAnswer(getTo(), getFrom(), replyMessage, sessionId));
    }

    @Override
    public String toString() {
        return "MsgGlobalSearch{" +
                "from='" + getFrom() + '\'' +
                "to='" + getTo() + '\'' +
                "searchMessage='" + searchMessage + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }

}
