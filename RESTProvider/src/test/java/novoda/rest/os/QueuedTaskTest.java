
package novoda.rest.os;

import static org.junit.Assert.*;

import com.jayway.awaitility.Awaitility;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class QueuedTaskTest {

    private QueuedTask<Integer> task;

    @Before
    public void setup() {
        task = new QueuedTask<Integer>();
        Awaitility.reset();
    }
    
    class T extends FutureTask<Integer> {
        public T(Callable<Integer> arg0) {
            super(arg0);
        }
        
    }
    
    @Test
    public void testAddingFuture() throws InterruptedException, ExecutionException {
        
        task.addToQueue(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("calling this");
                Thread.sleep(100);
                return 69;
            }
       });
        
        task.addToe(new T(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("calling this");
                Thread.sleep(100);
                return 69;
            }
       }));
        Thread.sleep(10);
        assertEquals(0, task.getSize());
        System.out.println("After get size");
        Thread.sleep(1000);
        System.out.println("after sleep ");
        assertEquals(1, task.getSize());
        System.out.println("!");
        assertEquals(0, task.getWorkingSize());
    }
    
    
}
