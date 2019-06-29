package ru.otus.java.hw15.messaging;

import ru.otus.java.hw15.messagesystem.Address;
import ru.otus.java.hw15.messagesystem.Addressee;
import ru.otus.java.hw15.messagesystem.Message;


/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}