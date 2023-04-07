package threadpoolchallenge;

public class MyTaskExecutor implements Runnable {
    private volatile boolean shutdown;
    private final MyBlockingQueue<Runnable> taskQueue;

    public MyTaskExecutor(MyBlockingQueue<Runnable> taskQueue) {
        this.shutdown = false;
        this.taskQueue = taskQueue;
    }

    public void shutdown() {
        shutdown = true;
    }

    @Override
    public void run() {
        while (!shutdown) {
            try {
                taskQueue.poll().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
