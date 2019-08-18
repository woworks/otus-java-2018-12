package ru.otus.java.hw16.server.messaging.messages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messages.MsgToSearch;
import ru.otus.java.hw16.server.messages.SearchService;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.util.List;

public class MsgGlobalSearch extends MsgToSearch {

    private static final Logger LOG = LogManager.getLogger(MsgGlobalSearch.class);

    private final String searchMessage;
    private final String sessionId;

    public MsgGlobalSearch(Address from, Address to, String searchMessage, String sessionId) {
        super(from, to);
        this.searchMessage = searchMessage;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(SearchService searchService, SocketMessageWorker worker) {
        LOG.info("Searching for: {}", searchMessage);

        List<UserDataSet> userDataSets = searchService.getDBService().findByName(searchMessage);

        if (!userDataSets.isEmpty()) {
            LOG.info("Found reply for {} = {}", searchMessage, userDataSets);
        } else {
            LOG.warn("Nothing found for {}", searchMessage);
        }

        StringBuilder builder = new StringBuilder();
        userDataSets.forEach(user ->  builder.append(user.getId() + ":" + user.getName() + ";"));

        String replyMessage = builder.toString();

        worker.send(new MsgGlobalSearchReply(getTo(), getFrom(), replyMessage, sessionId, getId()));
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
