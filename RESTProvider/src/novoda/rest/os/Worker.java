
package novoda.rest.os;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import novoda.rest.context.CallContext;

public class Worker<T> {

    private final BlockingQueue<CallContext<T>> workQueue;

    private final ExecutorService service;

    public Worker(int numWorkers, int workQueueSize) {
        workQueue = new LinkedBlockingQueue<CallContext<T>>(workQueueSize);
        service = Executors.newFixedThreadPool(numWorkers);

        for (int i = 0; i < numWorkers; i++) {
            service.submit(new Consumer<T>(workQueue));
        }
    }

    public void produce(CallContext<T> item) {
        try {
            workQueue.put(item);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private static class Consumer<T> implements Runnable {
        private final BlockingQueue<CallContext<T>> workQueue;

        public Consumer(BlockingQueue<CallContext<T>> workQueue) {
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    CallContext<T> item = workQueue.take();
                  //  item.handle(item.getData());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
