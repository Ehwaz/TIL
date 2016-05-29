package concurrency.thread;

import java.lang.Runnable;

/**
 * Created by Ehwaz on 2016-05-18.
 */

class MyThread1 extends Thread {
    public MyThread1(String name) {
        super(name);
    }
    public void run() {
        System.out.println("Running MyThread1");
    }
}

// The author prefers this way because it is easier to queue up tasks as Runnable instances
// when there is no usable thread in thread pool.
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Running MyThread2");
    }
}

public class ThreadBasicUsage {
    public static void main(String[] args) {
        System.out.println("Now in: " + Thread.currentThread().getName());

        MyThread1 my1 = new MyThread1("Thread1");
        System.out.println("Calling " + my1.getName());
        my1.start();

        Thread my2 = new Thread(new MyRunnable(), "Thread2");
        System.out.println("Calling " + my2.getName());
        my2.start();

        // Creating & running threads at the same time.
        int numOfThreads = 10;
        for (int i = 0; i < numOfThreads; i++) {
            new Thread(Integer.toString(i)) {
                public void run() {
                    System.out.println("Thread: " + getName() + " running");
                }
            }.start();
        }
    }
}
