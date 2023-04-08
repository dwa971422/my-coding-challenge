package threadpoolchallenge;

public class MyThread implements Runnable {
    private final MyBlockingQueue<Runnable> taskQueue;
    private final Thread thread;

    public MyThread(MyBlockingQueue<Runnable> taskQueue, String threadName) {
        this.taskQueue = taskQueue;
        this.thread = new Thread(this, threadName);
        this.thread.start();
    }

    public void interrupt() {
        thread.interrupt();
    }

    @Override
    public void run() {
        try {
            while (true) {
                taskQueue.poll().run();
            }
        } catch (InterruptedException e) {
            System.out.println(thread.getName() + " is shut down manually");
        }
    }
}
