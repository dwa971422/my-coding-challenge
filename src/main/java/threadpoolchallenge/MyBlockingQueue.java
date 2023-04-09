package threadpoolchallenge;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<T> {
    private final Queue<T> queue;
    private final int MAX_TASK_COUNT;
    private final Lock lock;
    private final Condition addCondition;
    private final Condition pollCondition;

    public MyBlockingQueue(int taskCount) {
        this.queue = new LinkedList<>();
        this.MAX_TASK_COUNT = taskCount;
        this.lock = new ReentrantLock();
        this.addCondition = this.lock.newCondition();
        this.pollCondition = this.lock.newCondition();
    }

    public void add(T task) throws InterruptedException {
        lock.lock();

        try {
            while (queue.size() == MAX_TASK_COUNT) {
                addCondition.await();
            }

            queue.add(task);

            pollCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T poll() throws InterruptedException {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                pollCondition.await();
            }

            T task = queue.poll();

            addCondition.signalAll();

            return task;
        } finally {
            lock.unlock();
        }
    }
}
