
package novoda.rest.concurrent;

import android.os.Handler;
import android.os.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class QueuedTask<Params, Result> {
    private static final String LOG_TAG = "AsyncTask";

    private static final int CORE_POOL_SIZE = 5;

    private static final int MAXIMUM_POOL_SIZE = 128;

    private static final int KEEP_ALIVE = 10;

    private static final BlockingQueue<Runnable> sWorkQueue = new PriorityBlockingQueue<Runnable>(
            10);

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);

    private static final int MESSAGE_POST_RESULT = 0x1;

    private static final int MESSAGE_POST_PROGRESS = 0x2;

    private static final int MESSAGE_POST_CANCEL = 0x3;

    private static final InternalHandler sHandler = new InternalHandler();

    private final WorkerRunnable<Params, Result> mWorker  = new WorkerRunnable<Params, Result>() {
        @Override
        public Result call() throws Exception {
            return null;
        }
    };

    private final FutureTask<Result> mFuture = new FutureTask<Result>(null, null);

    private volatile Status mStatus = Status.PENDING;

    /**
     * Indicates the current status of the task. Each status will be set only
     * once during the lifetime of a task.
     */
    public enum Status {
        /**
         * Indicates that the task has not been executed yet.
         */
        PENDING,
        /**
         * Indicates that the task is running.
         */
        RUNNING,
        /**
         * Indicates that {@link AsyncTask#onPostExecute} has finished.
         */
        FINISHED,
    }

    private static class InternalHandler extends Handler {
        @SuppressWarnings( {
                "unchecked", "RawUseOfParameterizedType"
        })
        @Override
        public void handleMessage(Message msg) {
            // sWorkQueue.add(new CallContext ());
            // Object result = (Object) msg.obj;
            // switch (msg.what) {
            // case MESSAGE_POST_RESULT:
            // // There is only one result
            // result.mTask.finish(result.mData[0]);
            // break;
            // case MESSAGE_POST_PROGRESS:
            // result.mTask.onProgressUpdate(result.mData);
            // break;
            // case MESSAGE_POST_CANCEL:
            // result.mTask.onCancelled();
            // break;
            // }
        }
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }

    public Result addToQueue(Params p) {
//        BlockingQueue blockingQueue = new ArrayBlockingQueue(2 * 2);
//        try
//        {
//            ExecutorService executorService = Executors.newFixedThreadPool(2);
//            // invokeAll() blocks until both tasks have completed
//            executorService.submit(task, result);
//            executorService.shutdown();
//        }
//        catch (InterruptedException e)
//        {
//            log.error("Failed to load feed.", e);
//            throw new RuntimeException("Failed to load feed.", e);
//        }
//        persister.finalizeFeed();
//        return new FeedStats(...);
        return null;
    }
}
