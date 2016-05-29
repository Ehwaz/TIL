package concurrency.thread;

/**
 * Created by Ehwaz on 2016-05-29.
 */

// Example code from the reference.
public class ThreadLocalExample {
    public static class PlainRunnable implements Runnable {
        private int threadLocal;

        @Override
        public void run() {
            threadLocal =  (int) (Math.random() * 100D);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(threadLocal);
        }
    }

    private static class ThreadLocalRunnable implements Runnable {
        private ThreadLocal<Integer> threadLocal =
                new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadLocal.set( (int) (Math.random() * 100D) );
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            // 2 thread prints their own value.
            System.out.println(threadLocal.get());
        }
    }

    private static void runTestWith(Runnable runnable) {
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

        try {
            thread1.join(); //wait for thread 1 to terminate
            thread2.join(); //wait for thread 2 to terminate
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ThreadLocalRunnable sharedThreadLocalRunnable = new ThreadLocalRunnable();
        PlainRunnable sharedPlainRunnable = new PlainRunnable();

        System.out.println("===== Testing ThreadLocalRunnable =====");
        runTestWith(sharedThreadLocalRunnable);

        System.out.println("===== Testing PlainRunnable =====");
        runTestWith(sharedPlainRunnable);
    }
}
