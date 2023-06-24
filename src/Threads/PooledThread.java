package Threads;

/**
 * The PooledThread class represents a thread that is part of a thread pool.
 */
public class PooledThread extends Thread {
    private static final IDAssigner threadID = new IDAssigner(1);
    private final ThreadPool pool;

    /**
     * Constructs a PooledThread object associated with the given thread pool.
     *
     * @param pool the thread pool to which this thread belongs
     */
    public PooledThread(ThreadPool pool) {
        super(pool, "PooledThread-" + threadID.next());
        this.pool = pool;
    }

    /**
     * Executes the main logic of the thread.
     * Continuously retrieves tasks from the thread pool and executes them until interrupted.
     * Handles exceptions and passes them to the thread pool's uncaught exception handler.
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            Runnable task = null;
            try {
                task = pool.getTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (task == null)
                return;

            try {
                task.run();
            } catch (Throwable t) {
                pool.uncaughtException(this, t);
            }
        }
    }
}
