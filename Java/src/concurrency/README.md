- Reference: http://tutorials.jenkov.com/java-concurrency

- Concurrency vs. Parallelism
  - Concurrency: executing multiple task at the same time(= **concurrently**).
  - Parallelism: splitting up a task into smaller subtasks which can be processed in parallel.

- Thread-safe code
  - Code that is safe to call by multiple thread simultaneously.
  - No race conditions in the code.
  - Even if the use of an object is thread-safe, if that object points to a shared resource like a file or DB,
the application as a whole may not be thread-safe.
    - ex) If each of 2 threads creates DB connection object and use it to execute 'insertIfNull()' on the same record, then race condition occurs.

- Deadlock
  - 4 necessary condition of deadlock: Mutual exclusion / Hold and wait / No preemption / Circular wait
  - Deadlock prevention
    - Lock ordering: by avoiding circular wait.
    - Lock timeout: by avoiding hold and wait. (put timeout on 'lock attempt', not lock itself!)
    - Deadlock detection