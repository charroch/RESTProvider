
package novoda.rest.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class QTask<Result> {

    protected BlockingQueue<Future<Result>> queue = new LinkedBlockingQueue<Future<Result>>();

    protected CompletionService<Result> compService = new ExecutorCompletionService<Result>(Executors
            .newFixedThreadPool(5), queue);

    void addToQueue(Callable<Result> t) {
        compService.submit(t);
    }
    
}