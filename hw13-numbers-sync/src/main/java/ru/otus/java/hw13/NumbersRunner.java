package ru.otus.java.hw13;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class NumbersRunner {

    private static final Logger LOG = LogManager.getLogger(NumbersRunner.class.getName());
    private static final Object lock = new Object();
    private static Long firstThreadId = -1L;
    private boolean firstAlreadyRead = false;
    private boolean forward = true;

    private final AtomicInteger counter = new AtomicInteger(1);

    private void printNumbers() {
        while ((forward && counter.get() < 10) || (!forward && counter.get() > 1)) {
            synchronized (lock) {
                if (!firstAlreadyRead && Thread.currentThread().getId() != firstThreadId && firstThreadId != -1) {
                    //System.out.println("Second Thread wants to be First");
                } else if (!firstAlreadyRead) {
                    if (firstThreadId == -1) {
                        firstThreadId = Thread.currentThread().getId();
                    }
                    System.out.print("FThread [" + Thread.currentThread().getName() + "] " + counter.get() + " |  ");
                    try {
                        firstAlreadyRead = true;
                        lock.notifyAll();
                        lock.wait();
                    } catch (InterruptedException e) {
                        LOG.error("ThreadId = " + Thread.currentThread().getId(), e);
                    }
                } else {
                    System.out.println("SThread [" + Thread.currentThread().getName() + "] " + counter.get());
                    firstAlreadyRead = false;
                    if (forward) {
                        counter.getAndIncrement();
                    } else {
                        counter.getAndDecrement();
                    }

                    try {
                        lock.notifyAll();
                        lock.wait();

                    } catch (InterruptedException e) {
                        LOG.error("Wait failed", e);
                    }
                }
                lock.notifyAll();
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                LOG.error("Sleep failed", e);
            }

            if (counter.get() == 10) {
                forward = false;
            } else if (counter.get() == 1) {
                forward = true;
            }
        }

    }


    public void run() {
        Thread printerOne = new Thread(this::printNumbers);
        Thread printerTwo = new Thread(this::printNumbers);

        printerOne.start();
        printerTwo.start();

        try {
            printerOne.join();
            printerTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
