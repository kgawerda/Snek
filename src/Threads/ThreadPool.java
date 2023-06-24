package Threads;

import java.util.LinkedList;
import java.util.List;

/**
 * The ThreadPool class represents a pool of worker threads that can execute tasks concurrently.
 */
public class ThreadPool extends ThreadGroup {
    private final int numberOfThreads;
    private boolean alive;
    private final List<Runnable> taskQueue;

    /**
     * Constructs a ThreadPool with the specified number of threads.
     *
     * @param numberOfThreads the number of threads in the pool
     */
    public ThreadPool(int numberOfThreads) {
        super("ThreadPool");
        setDaemon(true);
        this.numberOfThreads = numberOfThreads;
        this.alive = true;
        this.taskQueue = new LinkedList<>();
        createThreads();
    }

    /**
     * Creates and starts the worker threads in the pool.
     */
    private void createThreads() {
        for (int i = 0; i < this.numberOfThreads; ++i)
            new PooledThread(this).start();
    }

    /**
     * Retrieves a task from the task queue, waiting if necessary until a task is available.
     *
     * @return the next task to be executed
     * @throws InterruptedException if the thread is interrupted while waiting for a task
     */
    protected synchronized Runnable getTask() throws InterruptedException {
        while (this.taskQueue.size() == 0) {
            if (!this.alive) return null;
            wait();
        }
        return this.taskQueue.remove(0);
    }

    /**
     * Submits a task to the thread pool for execution.
     *
     * @param task the task to be executed
     * @throws IllegalStateException if the thread pool is no longer alive and cannot accept new tasks
     */
    public synchronized void runTask(Runnable task) {
        if (!this.alive) throw new IllegalStateException("ThreadPool is dead!");
        if (task != null) {
            taskQueue.add(task);
            notify();
        }
    }

}
