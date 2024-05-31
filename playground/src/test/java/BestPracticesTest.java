import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by I076097 on 11/20/2016
 */
public class BestPracticesTest {


    @Test
    public void testArray() {
        final List<String> strings = new ArrayList<>(1);
        strings.add("abc");

        final List<String> abc = Collections.singletonList("abc");

    }

    @Test
    public void testUnnecessaryObjects() {
        Long sum = 0L;
        final long then = System.currentTimeMillis();

        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println("Time taken: " + (System.currentTimeMillis() - then));
    }

    @Test
    public void testWithoutUnnecessaryObjects() {
        long sum = 0L;
        final long then = System.currentTimeMillis();
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println("Time taken: " + (System.currentTimeMillis() - then));

    }

    @Test
    public void testStreamsReadingFromExternalResource() throws FileNotFoundException {

        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("testStream.txt")));
        final Stream<String> lines = bufferedReader.lines();
        System.out.println(findAllWordsExpectArticles(lines));

    }

    private String findAllWordsExpectArticles(final Stream<String> lines) {
        final List<String> articles = Arrays.asList("a", "the", "an");
        return lines.filter(s -> s.length() > 0)
                .map(s -> s.split(" "))
                .flatMap(strings -> Stream.of(strings))
                .filter(word -> !articles.contains(word))
                .distinct()
                .map(String::toLowerCase)
                .collect(Collectors.joining(", "));
    }


    @Test
    public void nativeThreads() throws InterruptedException {
        final Runnable summer = new Runnable() {
            @Override
            public void run() {
                System.out.println("Current Thread name: " + Thread.currentThread().getName());
                long sum = 0L;
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    sum += i;
                }
                System.out.println("Sum is " + sum);
            }
        };

        final Thread[] allthreads = new Thread[2];

        for (int i = 0; i < 2; i++) {
            final Thread thread = new Thread(summer, "thread-" + i);
            thread.start();
            allthreads[i] = thread;
        }

        for (int i = 0; i < 2; i++) {
            allthreads[i].join();
        }

        System.out.println("DONE!");

    }

    @Test
    public void testWithExecutors() throws ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        final Runnable r1 = () -> {
            System.out.println("Current Thread name: " + Thread.currentThread().getName());
            long sum = 0L;
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sum += i;
            }
            System.out.println("Sum is " + sum);
        };

        final List<Future> allFutures = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            allFutures.add(executorService.submit(r1));
        }

        for (final Future f1 : allFutures) {
            f1.get();
        }

        executorService.shutdown();
        if (!executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("Executor service not shutdown");
        }

        System.out.println("DONE!");

    }

//    @Test
//    public void testSimpleHystrix() {
//        final NorthWindCommand northWindCommand = new NorthWindCommand();
//        final JsonObject res = northWindCommand.execute();
//        System.out.println(res);
//    }

}
