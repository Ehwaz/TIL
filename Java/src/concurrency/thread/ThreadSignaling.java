package concurrency.thread;

import java.util.*;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Ehwaz on 2016-05-29.
 */

interface MySharedObjInterface {
    public int getInt();
    public void putInt(int value);
}

class MySharedObjWaitNotify implements MySharedObjInterface {
    private Queue<Integer> intQueue;

    public MySharedObjWaitNotify() {
        this.intQueue = new LinkedList<>();
    }

    public int getInt() {
        // This code block is synced on the intQueue object.
        // TODO: why 'synchronized (this)' doesn't work?
        synchronized (intQueue) {
            while (intQueue.isEmpty()) {
                try {
                    intQueue.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return intQueue.poll();
        }
    }

    public synchronized void putInt(int value) {
        // This code block is synced on the intQueue object.
        synchronized (intQueue) {
            intQueue.add(value);
            intQueue.notify();
        }
    }
}

class ProducerRunnable implements Runnable {
    private MySharedObjInterface mySharedObj;
    private int start, end;

    public ProducerRunnable(MySharedObjInterface sharedObj, int start, int end) {
        this.mySharedObj = sharedObj;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            mySharedObj.putInt(i);
        }
    }
}

class ConsumerRunnable implements Runnable {
    private MySharedObjInterface mySharedObj;
    private int count;
    private boolean[] isTakenArr;

    public ConsumerRunnable(MySharedObjInterface sharedObj, int count) {
        this.mySharedObj = sharedObj;
        this.count = count;
        this.isTakenArr = new boolean[count];
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            int value = mySharedObj.getInt();
            if (value >= 0 && value < count) {
                isTakenArr[value] = true;
            }
        }
    }

    public void checkIfGotAll() {
        ArrayList<Integer> missingNumbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (isTakenArr[i] == false) {
                missingNumbers.add(i);
            }
        }
        if (missingNumbers.isEmpty()) {
            System.out.println("All numbers are got from producers.");
        } else {
            System.out.println(missingNumbers.size() + " numbers are missing.");
        }
    }
}

// TODO: Improve this use case. Current one is too simple.
public class ThreadSignaling {
    private static void runTestUsing(MySharedObjInterface sharedObj) {
        int count = (int)1e7;

        ConsumerRunnable consumerRunnable = new ConsumerRunnable(sharedObj, count);
        Thread thread1 = new Thread(consumerRunnable);

        ProducerRunnable producerRunnable1 = new ProducerRunnable(sharedObj, 0, count/4);
        ProducerRunnable producerRunnable2 = new ProducerRunnable(sharedObj, count/4, count/2);
        ProducerRunnable producerRunnable3 = new ProducerRunnable(sharedObj, count/2, 3*count/4);
        ProducerRunnable producerRunnable4 = new ProducerRunnable(sharedObj, 3*count/4, count);

        Thread thread21 = new Thread(producerRunnable1);
        Thread thread22 = new Thread(producerRunnable2);
        Thread thread23 = new Thread(producerRunnable3);
        Thread thread24 = new Thread(producerRunnable4);

        thread1.start();
        thread21.start();
        thread22.start();
        thread23.start();
        thread24.start();

        try {
            thread1.join();
            thread21.join();
            thread22.join();
            thread23.join();
            thread24.join();

            consumerRunnable.checkIfGotAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MySharedObjWaitNotify mySharedObjWaitNotify = new MySharedObjWaitNotify();
        runTestUsing(mySharedObjWaitNotify);
    }
}