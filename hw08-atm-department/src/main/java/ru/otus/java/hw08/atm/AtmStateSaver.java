package ru.otus.java.hw08.atm;

public class AtmStateSaver {

    private AtmMemento memento;

    public AtmMemento getMemento() {
        return memento;
    }

    public void setMemento(AtmMemento memento) {
        this.memento = memento;
    }
}
