package ru.otus.java.hw16.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.hw16.server.datasets.UserDataSet;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messages.MsgToFrontend;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messaging.messages.MsgGetAllUsers;
import ru.otus.java.hw16.server.messaging.messages.MsgGetAllUsersReply;
import ru.otus.java.hw16.server.messaging.messages.MsgGlobalSearch;
import ru.otus.java.hw16.server.messaging.messages.MsgGlobalSearchReply;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InOutMessagesService {
    private static final Logger LOG = LogManager.getLogger(InOutMessagesService.class);
    private final int TIMEOUT = 2_000;
    private final SocketMessageWorker client;
    private final CopyOnWriteArrayList<MsgToFrontend> receivedMessages = new CopyOnWriteArrayList<>();


    public InOutMessagesService(SocketMessageWorker client) {
        this.client = client;
    }

    public void start() {
        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(() -> {
            while (true){
                MsgToFrontend msg = (MsgToFrontend)client.take();
                System.out.println("FrontClientMain:: Front Message received: " + msg.toString());
                receivedMessages.add(msg);
            }
        });
    }

    public List<UserDataSet> sendReadAllMessage() {
        Message readAllMessage = new MsgGetAllUsers(client.getAddress(), new Address(Address.Type.DATABASE));
        client.send(readAllMessage);
        MsgToFrontend msgToFrontend = getResponse(readAllMessage);
        if (msgToFrontend != null) {
            return ((MsgGetAllUsersReply)msgToFrontend).getUsers();
        } else {
            return Collections.emptyList();
        }
    }

    public String sendSearchMessage(String searchString, String sessionId) {
        MsgGlobalSearch searchMessage = new MsgGlobalSearch(
                client.getAddress(),
                new Address(Address.Type.DATABASE),
                searchString, sessionId);
        client.send(searchMessage);

        MsgToFrontend msgToFrontend = getResponse(searchMessage);

        if (msgToFrontend != null) {
            return ((MsgGlobalSearchReply)msgToFrontend).getReplyMessage();
        } else {
            return null;
        }
    }

    private MsgToFrontend getResponse(Message message) {
        LOG.info("Waiting for reply for message id: {}", message.getId());
        int time = 0;

        while (time < TIMEOUT) {

            for (MsgToFrontend msg : receivedMessages) {
                if (msg.getRequestId().equals(message.getId())) {
                    System.out.println("[" + time + "]RETURN MSG = " + msg);
                    receivedMessages.remove(msg);
                    return msg;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOG.error("Sleep failed", e);
                Thread.currentThread().interrupt();
            }
            time = time + 100;
        }

        return null;
    }


}
