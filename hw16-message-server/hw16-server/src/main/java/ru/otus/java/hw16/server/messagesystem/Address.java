package ru.otus.java.hw16.server.messagesystem;

public final class Address {
    private final Integer port;
    public enum Type {SERVER, FRONTEND, DATABASE }
    private final Type type;

    public Address(Type type, Integer port){
        this.type = type;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return port != null ? port.equals(address.port) : address.port == null;
    }

    @Override
    public int hashCode() {
        return port != null ? port.hashCode() : 0;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Address{" +
                "port='" + port + '\'' +
                ", type=" + type +
                '}';
    }

    public Type getType() {
        return type;
    }
}
