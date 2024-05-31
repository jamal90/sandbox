import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by I076097 on 2/8/2017
 */
public class ScheduledExecutorTest {

    private static class ConsumerThread implements Runnable {
        int counter = 0;

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                    // setting back the interrupt signal - for it to stop in next run
                    Thread.currentThread().interrupt();
                }
                System.out.println("Counter at " + counter++ + " from thread " + Thread.currentThread().getName());
            }
        }
    }


    private Future<?> createExecutorAndReturn() {

        final ThreadFactory namedThreadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(final Runnable r) {
                return new Thread(r, "Consumer Thread " + new Random().nextInt(1000));
            }
        };

        final ExecutorService executorServiceCT = Executors.newSingleThreadExecutor(namedThreadFactory);
        final Future<?> submittedTask = executorServiceCT.submit(new ConsumerThread());
        // return executorServiceCT;
        return submittedTask;
    }

    @Test
    public void testExecShutdownCheck() throws InterruptedException {

        final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("CommitSchedulerThread-%d").build();

        // final ExecutorService executorServiceCT = createExecutorAndReturn();
        final Future<?> runningConsumerTask = createExecutorAndReturn(); // tbd

        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("Commit Scheduler thread name :" + Thread.currentThread().getName());

            // shutting down consumer thread exec service

            runningConsumerTask.cancel(true); // tbd - cancel the task and either shutdown the exec or submit a new task to the exec
            // executorServiceCT.shutdown();


            // restarting all
            try {
                testExecShutdownCheck();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

            // shutting down commit scheduler
            scheduledExecutorService.shutdown();

        }, 15, 15, TimeUnit.SECONDS);


        final CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }

}


