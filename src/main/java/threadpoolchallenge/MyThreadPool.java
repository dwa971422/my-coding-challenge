package threadpoolchallenge;

public class MyThreadPool {
    private final MyBlockingQueue<Runnable> taskQueue;
    private final Thread[] threads;

    public MyThreadPool(int taskQueueSize, int maxThreadCount) {
        this.taskQueue = new MyBlockingQueue<>(taskQueueSize);
        this.threads = new Thread[maxThreadCount];

        for (int i = 0; i < maxThreadCount; i++) {
            this.threads[i] = new Thread(new MyTaskExecutor(this.taskQueue));
            this.threads[i].start();
        }
    }

    public void submitTask(Runnable task) throws InterruptedException {
        taskQueue.add(task);
    }
}
