package ru.otus.java.hw16.server.messaging.messages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messages.MsgToSearch;
import ru.otus.java.hw16.server.messages.SearchService;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.util.List;

public class MsgGetAllUsers extends MsgToSearch {
    private static final Logger LOG = LogManager.getLogger(MsgGetAllUsers.class);

    public MsgGetAllUsers(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(SearchService searchService, SocketMessageWorker worker) {
        LOG.info("Getting all users...");

        List<UserDataSet> userDataSets = searchService.getDBService().readAll();
        LOG.info("Found users: {}", userDataSets);

        worker.send(new MsgGetAllUsersReply(getTo(), getFrom(), userDataSets, this.getId()));
    }

    @Override
    public String toString() {
        return "MsgGetAllUsers{" +
                "id='" + getId() + '\'' +
                "from='" + getFrom() + '\'' +
                "to='" + getTo() + '\'' +
                '}';
    }

}
