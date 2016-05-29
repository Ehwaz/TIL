package concurrency.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ehwaz on 2016-05-23.
 */

/*
- Quote from the reference:
  - "More formally, the situation where multiple threads compete for the same resource,
    where the sequence in which the resource is accessed is significant, is called race conditions.
    A code section that leads to race conditions is called a critical section."
 */
interface Counter {
    public void add();
    public int getCount();
}

class BasicCounter implements Counter {
    private int count;
    public BasicCounter() { count = 0; }
    public void add() { count++; }
    public int getCount() { return count; }
}

class AtomicCounter implements Counter {
    private AtomicInteger count;
    public AtomicCounter() {
        count = new AtomicInteger(0);
    }
    public void add() { count.incrementAndGet(); }
    public int getCount() { return count.get(); }
}

/*
- Quote from the reference:
  - "All synchronized blocks synchronized on the same object can only have one thread
    executing inside them at the same time. All other threads attempting to enter the synchronized block are
    blocked until the thread inside the synchronized block exits the block."
 */
class SynchronizedCounter implements Counter {
    private int count;
    public SynchronizedCounter() { count = 0; }
    // Or, declare 'add' method like 'public synchronized void add()'
    // This method is synced on the object owning this method.
    public synchronized void add() {
        count++;
    }
    public int getCount() { return count; }
}

/*
- Quote from the reference:
  - "'volatile' keyword guarantees that all reads of a volatile variable are read directly from main memory,
    and all writes to a volatile variable are written directly to main memory."
  - "the volatile keyword comes with a "happens before guarantee".
    The happens before guarantee guarantees that read and write instructions of volatile variables cannot be reordered.
    Instructions before and after can be reordered,
    but the volatile read/write instruction cannot be reordered with any instruction occurring before or after it."
- When using variables with 'volatile' keyword,user have to consider performance degradation because:
  - reading and writing of volatile variable are always applied to the main memory,
  - 'volatile' keyword prevents JVM instruction reordering.
 */
/*
In this case, however, only using 'volatile' is not enough because new value of shared variable depends on its previous value.
- Quote from the reference:
  - "The short time gap in between the reading of the volatile variable and the writing of its new value
    creates an race condition where multiple threads might read the same value of the volatile variable,
    generate a new value for the variable, and when writing the value back to main memory - overwrite each other's values."
 */
class VolatileCounter implements Counter {
    private volatile int count;
    public VolatileCounter() { count = 0; }
    public void add() { count++; }
    public int getCount() { return count; }
}

class CounterRunnable implements Runnable {
    Counter counter;
    int numToAdd;
    public CounterRunnable(Counter counter, int numToAdd) {
        this.counter = counter;
        this.numToAdd = numToAdd;
    }
    public void run() {
        for (int i = 0; i < numToAdd; i++) { counter.add(); }
    }
}

public class RaceCondition {
    static int thread1Cnt = 1000000;
    static int thread2Cnt = 500000;

    public static void runThreadWithCounter(Counter cnt) {
        Thread cntThread1 = new Thread(new CounterRunnable(cnt, thread1Cnt));
        Thread cntThread2 = new Thread(new CounterRunnable(cnt, thread2Cnt));

        cntThread1.start();
        cntThread2.start();

        try {
            cntThread1.join();
            cntThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Increased counter : " + (thread1Cnt + thread2Cnt) + " times.");
        System.out.println("Resulting counter value: " + cnt.getCount());
    }

    public static void main(String[] args) {
        System.out.println("================== Race condition demo ==================");
        BasicCounter cnt1 = new BasicCounter();
        runThreadWithCounter(cnt1);
        System.out.println("\n===== Preventing race condition using AtomicCounter =====");
        AtomicCounter cnt2 = new AtomicCounter();
        runThreadWithCounter(cnt2);
        System.out.println("\n===== Preventing race condition using \'synchronized\' =====");
        SynchronizedCounter cnt3 = new SynchronizedCounter();
        runThreadWithCounter(cnt3);
        System.out.println("\n========== Just using \'volatile\' doesn't work. ==========");
        VolatileCounter cnt4 = new VolatileCounter();
        runThreadWithCounter(cnt4);
    }
}
