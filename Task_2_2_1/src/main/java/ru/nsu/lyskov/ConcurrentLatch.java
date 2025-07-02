package ru.nsu.lyskov;

public class ConcurrentLatch {
    private int count;
    private final Object lock = new Object();

    public ConcurrentLatch(int count) {
        this.count = count;
    }

    public void countDown() {
        synchronized (lock) {
            if (count > 0) {
                count--;
            }
            if (count == 0) {
                lock.notifyAll();
            }
        }
    }

    public void await() throws InterruptedException {
        synchronized (lock) {
            while (count > 0) {
                lock.wait();
            }
        }
    }
}
