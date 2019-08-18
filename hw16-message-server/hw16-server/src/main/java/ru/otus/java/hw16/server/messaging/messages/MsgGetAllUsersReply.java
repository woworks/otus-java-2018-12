package ru.otus.java.hw16.server.messaging.messages;

import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messages.MsgToFrontend;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.service.FrontendService;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.util.List;
import java.util.UUID;

public class MsgGetAllUsersReply extends MsgToFrontend {
    private final List<UserDataSet> users;


    public MsgGetAllUsersReply(Address from, Address to, List<UserDataSet> users, UUID requestId) {
        super(from, to, requestId);
        this.users = users;
    }

    @Override
    public void exec(FrontendService frontendService, SocketMessageWorker worker) {
        frontendService.addToResults(getRequestId(), users);
    }

    @Override
    public String toString() {
        return "MsgGetAllUsersReply{" +
                "id=" + getId() +
                "requestId=" + getRequestId() +
                "users=" + users +
                '}';
    }

    public List<UserDataSet> getUsers() {
        return users;
    }


}
