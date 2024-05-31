import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by I326683 on 2/8/2017.
 */
public class ScheduledExecutorTest2 {

    private final ScheduledExecutorService commitScheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setDaemon(false)
            .setNameFormat
                    ("COMMIT_SCHEDULER-%d").build());
    private final ScheduledExecutorService commitScheduler1 = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setDaemon(true).setNameFormat
            ("CHECK_COUNT-%d").build());
    int count = 0;

    private void test() {
        commitScheduler.scheduleAtFixedRate(() -> {

            System.out.println("now the value is " + count);
            count++;
        }, 2, 2, TimeUnit.SECONDS);
    }

    private void checkCount() {
        commitScheduler.scheduleAtFixedRate(() -> {
            if (count == 10)
                commitScheduler.shutdown();
        }, 2, 2, TimeUnit.SECONDS);
    }

    public static void main(final String[] args) {
        final ScheduledExecutorTest2 t = new ScheduledExecutorTest2();
        t.test();
        t.checkCount();
    }

}