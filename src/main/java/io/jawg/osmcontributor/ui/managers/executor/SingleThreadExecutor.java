package io.jawg.osmcontributor.ui.managers.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

public class SingleThreadExecutor implements ThreadExecutor {

    private final Executor executor;
    private final ThreadFactory threadFactory;

    @Inject
    public SingleThreadExecutor() {
        this.threadFactory = new SingleThreadFactory("Single thread");
        this.executor = Executors.newSingleThreadExecutor(threadFactory);
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("Runnable to execute cannot be null");
        }
        executor.execute(runnable);
    }

    private static class SingleThreadFactory implements ThreadFactory {
        private final String threadName;
        private int counter = 0;

        private SingleThreadFactory(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, threadName + counter++);
        }
    }
}
