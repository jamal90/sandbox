package cassandra;

import java.time.Instant;

public class DataGenWorker implements Runnable{

    private final Instant start;
    private final Instant end;
    private final int step; // milliseconds

    public DataGenWorker(Instant start, Instant end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public void run() {

    }
}
