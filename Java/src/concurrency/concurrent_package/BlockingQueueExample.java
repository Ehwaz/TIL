package concurrency.concurrent_package;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Ehwaz on 2016-05-29.
 */


class ProducerRunnableBusyWaiting implements Runnable {
    private LinkedBlockingQueue<Integer> intQueue;
    private int count;

    public ProducerRunnableBusyWaiting(LinkedBlockingQueue<Integer> intQueue, int count) {
        this.intQueue = intQueue;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                intQueue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ConsumerRunnableBusyWaiting implements Runnable {
    private LinkedBlockingQueue<Integer> intQueue;
    private int count;

    boolean[] isTakenArr = null;

    public ConsumerRunnableBusyWaiting(LinkedBlockingQueue<Integer> intQueue, int count) {
        this.intQueue = intQueue;
        this.count = count;
        isTakenArr = new boolean[count];
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            int value = 0;
            try {
                value = intQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isTakenArr[value] = true;
        }
    }

    public void checkIfAllPrinted() {
        ArrayList<Integer> missingNumbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (isTakenArr[i] == false) {
                missingNumbers.add(i);
            }
        }
        if (missingNumbers.isEmpty()) {
            System.out.println("All numbers are got from producer.");
        } else {
            System.out.println(missingNumbers.size() + " numbers are missing.");
        }
    }
}


public class BlockingQueueExample {
    public static void main(String[] args) {
        int count = (int)1e7;

        LinkedBlockingQueue<Integer> intQueue = new LinkedBlockingQueue<>();
        ConsumerRunnableBusyWaiting consumerRunnable = new ConsumerRunnableBusyWaiting(intQueue, count);
        ProducerRunnableBusyWaiting producerRunnable = new ProducerRunnableBusyWaiting(intQueue, count);

        Thread thread1 = new Thread(consumerRunnable);
        Thread thread2 = new Thread(producerRunnable);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
            consumerRunnable.checkIfAllPrinted();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}