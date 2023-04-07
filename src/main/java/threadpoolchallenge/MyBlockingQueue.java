package threadpoolchallenge;

import java.util.LinkedList;
import java.util.Queue;

public class MyBlockingQueue<T> {
    private final Queue<T> queue;
    private final int MAX_TASK_COUNT;

    public MyBlockingQueue(int taskCount) {
        this.queue = new LinkedList<>();
        this.MAX_TASK_COUNT = taskCount;
    }

    public synchronized void add(T task) throws InterruptedException {
        while (queue.size() == MAX_TASK_COUNT) {
            wait();
        }

        queue.offer(task);

        if (queue.size() == 1) {
            notifyAll();
        }
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        if (queue.size() == MAX_TASK_COUNT) {
            notifyAll();
        }

        return queue.poll();
    }
}
