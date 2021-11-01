package ru.tuanviet.javabox;

import java.util.ArrayList;
import java.util.List;

public class SuperReadWriteLock {
    private int countWriters;
    private int countReaders;
    private List<Thread> listThreads = new ArrayList<>();

    public SuperReadWriteLock() {
        countReaders = 0;
        countWriters = 0;
    }

    public synchronized void acquireReadLock() {
        listThreads.add(Thread.currentThread());
        while (countWriters > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countReaders++;
    }

    public synchronized void releaseReadLock() {
        listThreads.remove(Thread.currentThread());
        countReaders--;
        notify();
    }

    public synchronized void acquireWriteLock() {
//        listThreads.add(Thread.currentThread());
//        if (listThreads.contains(Thread.currentThread()) && listThreads.size() == 1)

        //  if ()
        while (countWriters > 0 || countReaders > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countWriters++;
    }

    public synchronized void releaseWriteLock() {
        countWriters--;
        notify();
    }

}
