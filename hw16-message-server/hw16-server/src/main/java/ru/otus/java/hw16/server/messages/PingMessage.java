package ru.otus.java.hw16.server.messages;

public class PingMessage extends MsgToServer {
    private final long time;

    public PingMessage() {
        super(from, to);
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PingMessage{");
        sb.append("time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
