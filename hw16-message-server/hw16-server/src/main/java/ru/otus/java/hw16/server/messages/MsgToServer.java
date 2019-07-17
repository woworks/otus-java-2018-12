package ru.otus.java.hw16.server.messages;


import ru.otus.java.hw16.server.base.ServerService;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.messagesystem.Addressee;
import ru.otus.java.hw16.server.messagesystem.Message;

public abstract class MsgToServer extends Message {
    public MsgToServer(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof ServerService) {
            exec((ServerService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(ServerService serverService);
}