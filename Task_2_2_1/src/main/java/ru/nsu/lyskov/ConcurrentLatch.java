package ru.nsu.lyskov;

/**
 * Класс-счетчик, позволяющий потокам ожидать, пока счетчик не достигнет нуля. Аналог
 * {@link java.util.concurrent.CountDownLatch}, но реализован самостоятельно.
 */
public class ConcurrentLatch {
    private int count;
    private final Object lock = new Object();

    /**
     * Создаёт экземпляр счетчика с начальным значением.
     *
     * @param count начальное значение счетчика, определяющее, сколько вызовов {@link #countDown()}
     *              нужно, чтобы снять блокировку в {@link #await()}
     */
    public ConcurrentLatch(int count) {
        this.count = count;
    }

    /**
     * Уменьшает значение счетчика на единицу. Если значение достигло нуля, пробуждает все
     * ожидающие потоки.
     */
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

    /**
     * Блокирует вызывающий поток, пока значение счетчика не станет равным нулю.
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    public void await() throws InterruptedException {
        synchronized (lock) {
            while (count > 0) {
                lock.wait();
            }
        }
    }
}
