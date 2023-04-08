import threadpoolchallenge.MyThreadPool;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");

        MyThreadPool myThreadPool = new MyThreadPool(3, 3);

        myThreadPool.submitTask(() -> {
            System.out.println("Start task 1");
            try {
                Thread.sleep(10000);
                System.out.println("Finish task 1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        myThreadPool.submitTask(() -> {
            System.out.println("Start task 2");
            try {
                Thread.sleep(20000);
                System.out.println("Finish task 2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        myThreadPool.submitTask(() -> {
            System.out.println("Start task 3");
            try {
                Thread.sleep(30000);
                System.out.println("Finish task 3");
                myThreadPool.shutdownAllMyThreads();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        myThreadPool.submitTask(() -> {
            System.out.println("Start task 4");
            try {
                Thread.sleep(3000);
                System.out.println("Finish task 4");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        myThreadPool.submitTask(() -> {
            System.out.println("Start task 5");
            try {
                Thread.sleep(3000);
                System.out.println("Finish task 5");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        myThreadPool.submitTask(() -> {
            System.out.println("Start task 6");
            try {
                Thread.sleep(3000);
                System.out.println("Finish task 6, then submit task 7");
                myThreadPool.submitTask(() -> System.out.println("This is task 7"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
