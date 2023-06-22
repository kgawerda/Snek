package Threads;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool extends ThreadGroup
{
    private final int numberOfThreads;
    private boolean alive;
    private final List<Runnable> taskQueue;

    public ThreadPool(int numberOfThreads)
    {
        super("ThreadPool");
        setDaemon(true);
        this.numberOfThreads = numberOfThreads;
        this.alive = true;
        this.taskQueue = new LinkedList<Runnable>();
        createThreads();
    }

    private void createThreads()
    {
        for(int i=0; i<this.numberOfThreads; ++i)
            new PooledThread(this).start();
    }

    protected synchronized Runnable getTask() throws InterruptedException
    {
        while (this.taskQueue.size() == 0)
        {
            if(!this.alive) return null;
            wait();
        }
        return this.taskQueue.remove(0);
    }

    public synchronized void runTask(Runnable task)
    {
        if(!this.alive) throw new IllegalStateException("ThreadPool is dead!");
        if(task != null)
        {
            taskQueue.add(task);
            notify();
        }
    }

    public synchronized void close()
    {
        if(!this.alive) return;
        this.alive = false;
        this.taskQueue.clear();
        interrupt();
    }

    public void join()
    {
        synchronized (this) {
            this.alive = false;
            notifyAll();
        }

        Thread[] threads = new Thread[activeCount()];
        int count = enumerate(threads);

        for(int i=0; i<count; ++i)
        {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
