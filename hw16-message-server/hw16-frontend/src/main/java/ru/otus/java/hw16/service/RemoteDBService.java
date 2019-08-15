package ru.otus.java.hw16.service;

import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messages.PingMessage;
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
        users = (List<UserDataSet>)sendMessage(new PingMessage(client.getAddress()));
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
            while (true){
                System.out.println("Waiting for reply");
                Message msg = null;
                try {
                    msg = (Message)client.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //MsgGlobalSearchAnswer msg = (MsgGlobalSearchAnswer)client.take();
                System.out.println("FrontClientMain:: Front Message received: " + msg.toString());
                if (msg.getId().equals(message.getId())) {
                    System.out.println("RETURN MDG = " + msg);
                    return msg;
                }
            }
        //});

    }
}
