
package novoda.rest.os;

import android.os.Handler;
import android.os.Message;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A message queue implementation which puts callables into a queue and have a
 * thread pool executing them while putting the result (in the form of future)
 * into another queue for lifecycle purpose.
 * 
 * @param <Callable>
 * @param <Result>
 */
public class QueuedTask<Result> {

    private static final int CORE_POOL_SIZE = 5;

    private static final int MAXIMUM_POOL_SIZE = 128;

    private static final int KEEP_ALIVE = 10;

    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(10);

    private final BlockingQueue<Future<Result>> completionQueue = new LinkedBlockingQueue<Future<Result>>(
            10);

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "QueuedTask #" + mCount.getAndIncrement());
        }
    };

    private final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, workQueue, sThreadFactory);

    private CompletionService<Result> taskCompletionService = new ExecutorCompletionService<Result>(
            sExecutor, completionQueue);

//    private static final InternalHandler sHandler = new InternalHandler();
//
//    private static class InternalHandler extends Handler {
//        private static final int MESSAGE_ADDING_CALL_INFO = 0;
//
//        private static final int MESSAGE_CALL_INFO_DOWNLOADED = 1;
//
//        private static final int MESSAGE_CALL_INFO_FINISHED = 2;
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                Result t = (Result)msg.obj
//                case MESSAGE_ADDING_CALL_INFO:
//                    break;
//                case MESSAGE_CALL_INFO_DOWNLOADED:
//                    break;
//                case MESSAGE_CALL_INFO_FINISHED:
//                    break;
//            }
//        }
//    }

    void addToQueue(Callable<Result> c) {
        taskCompletionService.submit(c);
    }

    void addToe(FutureTask<Result> c) {
        sExecutor.submit(c);
    }

    public int getSize() {
        return completionQueue.size();
    }

    //    
    // void add(Callable);
    //    
    // void startExecution();
    //    
    // void readResult();

    public void start() throws InterruptedException, ExecutionException {
        // while(true) {
        // taskCompletionService.take();
        // }
    }

    public Result getResult() {
        return null;
    }

    public int getWorkingSize() {
        return sExecutor.getQueue().size();
    }
}
