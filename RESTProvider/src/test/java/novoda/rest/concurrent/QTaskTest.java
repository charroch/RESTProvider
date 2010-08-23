
package novoda.rest.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class QTaskTest {

    private CountDownLatch lock = new CountDownLatch(1);

    @Test
    public void test() throws InterruptedException {
        assertTrue(true);
        QTask<Integer> t = new QTask<Integer>();
        F d = new F();
        assertEquals(t.queue.size(), 0);
        t.compService.submit(d);
        synchronized (lock) {
            lock.await(2000, TimeUnit.MILLISECONDS);
        }
        assertEquals(t.queue.size(), 1);
    }

    class F implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            try {
                Thread.sleep(1000);
                return new Integer(1);
            } finally {
                lock.countDown();
            }
        }
    }
}
