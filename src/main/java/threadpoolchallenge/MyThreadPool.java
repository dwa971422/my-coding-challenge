package threadpoolchallenge;

public class MyThreadPool {
    private final MyBlockingQueue<Runnable> taskQueue;
    private final MyThread[] myThreads;

    public MyThreadPool(int taskQueueSize, int maxThreadCount) {
        this.taskQueue = new MyBlockingQueue<>(taskQueueSize);
        this.myThreads = new MyThread[maxThreadCount];

        for (int i = 0; i < maxThreadCount; i++) {
            this.myThreads[i] = new MyThread(this.taskQueue, "MyThread-" + i);
        }
    }

    public void submitTask(Runnable task) throws InterruptedException {
        taskQueue.add(task);
    }

    public void shutdownAllMyThreads() {
        for (MyThread myThread : myThreads) {
            myThread.interrupt();
        }
    }
}
