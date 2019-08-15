package ru.otus.java.hw16.service;

import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messages.PingMessage;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messaging.messages.MsgGetAllUsers;
import ru.otus.java.hw16.server.messaging.messages.MsgGetAllUsersReply;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteDBService implements DBService {

    private final SocketMessageWorker client;
    private final Map<UUID, Object> messages = new HashMap<>();

    public RemoteDBService(SocketMessageWorker client) {
        this.client = client;
    }

    @Override
    public void save(UserDataSet dataSet) {

    }

    @Override
    public UserDataSet read(long id) {
        return null;
    }

    @Override
    public List<UserDataSet> readAll() {
        List<UserDataSet> users = new ArrayList<>();
        users = (List<UserDataSet>)sendMessage(new MsgGetAllUsers(client.getAddress(), new Address(Address.Type.DATABASE, 0)));
        return users;
    }

    @Override
    public List<UserDataSet> findByName(String name) {
        return null;
    }

    @Override
    public void flush() {

    }

    private Object sendMessage(Message message) {
        client.send(message);

        //ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //executorService.submit(() -> {
        System.out.println("Waiting for reply for message id " + message.getId());
            while (true){
                System.out.println("Waiting for reply");
                MsgGetAllUsersReply replyMessage = null;
                try {
                    replyMessage = (MsgGetAllUsersReply)client.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //MsgGlobalSearchReply replyMessage = (MsgGlobalSearchReply)client.take();
                System.out.println("FrontClientMain:: Message RePLY received: " + replyMessage.toString());
                if (replyMessage.getRequestId().equals(message.getId())) {
                    System.out.println("RETURN MDG = " + replyMessage);
                    return replyMessage.getUsers();
                }
            }
        //});

    }
}
