import com.amazonaws.annotation.ThreadSafe;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Range;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by I076097 on 8/12/2016.
 */
public class PlayGround {

    private class SomeCheckedException extends Exception {

    }

    private class A1 {

    }

    private class A2 extends A1 {

    }

    enum DeploymentMode {
        PRODUCER("PRODUCER"), CONSUMER("CONSUMER"), COUPLED("COUPLED");

        private final String deploymentMode;

        DeploymentMode(final String mode) {
            this.deploymentMode = mode;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(PlayGround.class);

    @Test
    public void testEnumValueOf() {
        final String newVal = "BOTH";
        /*try{
            DeploymentMode mode = DeploymentMode.valueOf(newVal);
        }catch (IllegalArgumentException e){
            System.out.println("In Exception");
        }
*/

        System.out.println(DeploymentMode.COUPLED.toString().equalsIgnoreCase(newVal));
    }


    static class GenericA<T> {

        List<T> list;

        public List<T> getList() {
            return list;
        }

        public void setList(final List<T> list) {
            this.list = list;
        }

        static class Builder<T> {
            public List<T> getList2() {
                return list2;
            }

            public void setList2(final List<T> list2) {
                this.list2 = list2;
            }

            List<T> list2;

        }


    }

    @Test
    public void testClassGenerics() {
        final GenericA<String> t1 = new GenericA<>();
        final GenericA.Builder<String> t2 = new GenericA.Builder<>();
        final List<Integer> list2 = Arrays.asList(1, 2, 3);

        final List<String> list1 = Arrays.asList("abc", "cde");
        t1.setList(list1);

        // t2.setList2(Arrays.asList(1, 2, 3));
    }

    @Test
    public void testInheritenceInstanceOf() {
        final A2 a2 = new A2();
        if (a2.getClass().isAssignableFrom(A1.class)) {
            System.out.println("Instance of inheritance!");
        } else {
            System.out.println("otherwise");
        }
    }

    @Test
    public void testExceptionFromCatchBlock() {

        try {
            throwCheckedException();
            // do nothing
        } catch (final Exception e) {
            throwRuntimeException();
        } finally {
            System.out.println("Hello World!");
        }
    }

    @Test
    public void testCompletedExceptionally() {
        final CompletableFuture<Void> f1 = new CompletableFuture<>();
        f1.completeExceptionally(new Exception("Error!"));

        try {
            f1.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            e.printStackTrace();
        }

    }

    @Test
    public void testThreadSafetyRunnableLocalMember() throws Exception {


        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        System.out.println("First Line");
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                private int val = 0;

                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " Before Any Op: " + val);
                    val += 10;
                    System.out.println(Thread.currentThread().getName() + " After First Op: " + val);
                }
            });
            executorService.submit(new Runnable() {
                private int val = 0;

                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " Before Any Op: " + val);
                    val += 10;
                    System.out.println(Thread.currentThread().getName() + " After First Op: " + val);
                }
            });
        }


        executorService.shutdown();
    }

    @Test
    public void testModulo() {
        final long elapse = 15;
        long counter = 0;
        while (counter <= 900) {
            System.out.println(counter);
            if (++counter % elapse == 0) {

                System.out.println("To be flushed");
            }
        }

    }

    @Test
    public void testNullEquals() {
        System.out.println("S3".equalsIgnoreCase("S13"));
    }

    @Test
    public void InstantIsBefore() throws Exception {
        final Instant t1 = Instant.now();
        System.out.println(t1.isBefore(t1));
    }

    @Test
    public void testPassToWildCardMethods() {
        final List<List<?>> listListInteger = new ArrayList<>();
        listListInteger.add(Collections.singletonList(1));
        listListInteger.add(Collections.singletonList("abc"));
        methodWithWildCards(listListInteger);
    }

    private void methodWithWildCards(final List<? extends List<?>> numbers) {
        // do nothing
    }

    @Test
    public void testLongtolong() {
        System.out.println(Integer.MAX_VALUE);
        final Long l = Integer.MAX_VALUE + 100l;
        final int i = l.intValue();
        final int castedInt = (int) (long) l;
        System.out.println("Long.intValue" + i);
        System.out.println("Doubel Casted" + castedInt);
    }

    @Test
    public void testMapToList() throws Exception {

        final Map<String, String> map = new HashMap<>();
        //map.put("one", "1");
        final List listOfValues = (List) new ArrayList<String>(map.values());
    }

    @Test
    public void testResetOfCompFuture() {
        CompletableFuture<String> cf = new CompletableFuture<>();

        cf.completeExceptionally(new Exception("Error 1"));

        System.out.println(cf.isCompletedExceptionally());
        cf = new CompletableFuture<>();
        cf.complete("Completed successfully");

        String a = "Empty";
        try {
            a = cf.get();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(a);

    }


    @Test
    public void getNextHourSlot() throws Exception {
        final Instant now = Instant.now();

        System.out.println("Truncate to hours" + Instant.now().truncatedTo(ChronoUnit.HOURS));

        final Instant someTimeLater = now.plus(2, ChronoUnit.HOURS);
        System.out.println(Duration.between(now, someTimeLater).toHours());
    }

    @Test
    public void testSingleThreadPool() throws Exception {
        startNested();
        Thread.sleep(20000);

    }

    @Test
    public void testReduceAndPeakOnEmptyStream() throws Exception {
        // final Stream<Integer> stringStream = Stream.empty();
        final Stream<Integer> stringStream = Stream.of(1, 2, 4, 3);
        final Optional<Integer> lastValue = stringStream.peek(intVal -> System.out.println(intVal)).reduce((first, second) -> second);
        if (lastValue.isPresent()) {
            System.out.println(lastValue.get());
        }
    }

    @Test
    public void testRange() throws Exception {

        System.out.println(Range.closed(Instant.MIN, Instant.now()));

    }

    private void startNested() {
        final ScheduledExecutorService a = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "One" + new Random().nextInt()));
        a.scheduleAtFixedRate(() -> {
            final ScheduledExecutorService b = a;
            System.out.println("Thread Name " + Thread.currentThread().getName());
            startNested();
            b.shutdown();
        }, 1000, 1000, TimeUnit.MILLISECONDS);

    }

    @Test
    public void testUnicode() throws Exception {

        System.out.println("\u0026");

    }

    private void throwRuntimeException() {
        throw new RuntimeException();
    }

    private void throwCheckedException() throws SomeCheckedException {
        throw new SomeCheckedException();
    }


    @Test
    public void testConcurrentModification() throws Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final List<String> myList = new ArrayList<String>();

        final Future<?> f1 = executorService.submit(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        myList.add(String.valueOf(System.currentTimeMillis()));
                        final Iterator<String> iter = myList.iterator();
                        if (myList.iterator().hasNext()) {
                            iter.next();
                            iter.remove();
                        }

                    }
                } catch (final ConcurrentModificationException e) {
                    System.out.println("HEre!");
                }

            }
        });

        final Future<?> f2 = executorService.submit(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        myList.add(String.valueOf(System.currentTimeMillis()));
                        final Iterator<String> iter = myList.iterator();
                        if (myList.iterator().hasNext()) {
                            iter.next();
                            iter.remove();
                        }
                    }
                } catch (final ConcurrentModificationException e) {
                    System.out.println("Here!");
                }

            }
        });

    }


    class GenericType<T> {
        public T a;

        public GenericType(final T a) {
            this.a = a;
        }
    }

    class GenericContainer {
        public List<GenericType<?>> list = new ArrayList<>();

        public <T> T getAtIndexAs(final int index, final Class<T> clazz) {
            final GenericType<?> genericType = list.get(index);
            return clazz.cast(genericType.a);
        }
    }

    @Test
    public void testInsertRandomTypeToList() {
        final List<GenericType<?>> anyTypeList = new ArrayList<>();
        anyTypeList.add(new GenericType<String>("abc"));
        anyTypeList.add(new GenericType<Integer>(1));

        final GenericContainer genericContainer = new GenericContainer();
        genericContainer.list = anyTypeList;

        genericContainer.getAtIndexAs(0, Integer.class);
    }

    @Test
    public void testStartsWithCondNull() {

        System.out.println("hello".startsWith(null));
    }

    @Test
    public void testSimple() {
        final List list1 = new ArrayList();
        list1.add("12");
        list1.add(1);

    }

    class BuilderTest {
        int a;
        int b;
        int c;

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }

        public int getC() {
            return c;
        }

        public BuilderTest() {
            // nothing
        }

        public BuilderTest withA(final int a) {
            this.a = a;
            return this;
        }

        public BuilderTest withB(final int b) {
            this.b = b;
            return this;
        }

        public BuilderTest withC(final int c) {
            this.c = c;
            return this;
        }


        @Override
        public String toString() {
            return "VALUE " + (a + b + c);
        }
    }

    @Test
    public void testBuilderPartialBuild() {
        final BuilderTest builderTest = new BuilderTest();
        builderTest.withA(5);
        builderTest.withB(100);
        builderTest.withC(102);

        // System.out.println(builderTest.getA());
        System.out.println(builderTest);
    }

    @Test
    public void testMultiLevelExceptionThrow() throws Exception {
        try {
            throw new RuntimeException("Hello World1", new IllegalStateException("IllegalState"));
        } catch (final RuntimeException ex) {
            try {
                throw new RuntimeException("Hello World2", ex);
            } catch (final RuntimeException ex1) {
                System.out.println(ex1.getMessage());
                System.out.println(ex1);
            }


        }
    }

    @Test
    public void testSupplyAsync() throws ExecutionException, InterruptedException {
        System.out.println("From Main: " + Thread.currentThread().getName());
        final CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                // long running!
                Thread.sleep(2000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            return "ABCD";  // can be a jdbc / external http call
        });

        CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Hello");
            // long running!
            try {
                // long running!
                Thread.sleep(2000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        });


        Thread.sleep(5000);

        System.out.println("After sleeping for 5 seconds");

        final String s = stringCompletableFuture.get();
        System.out.println("From Main: " + Thread.currentThread().getName() + " " + s);
    }

    @Test
    public void loggingWithLevelCauses() throws Exception {
        try {
            try {
                throw new IllegalStateException("inner");
            } catch (final RuntimeException ex) {
                throw new IllegalArgumentException("outer", ex);
            } finally {
                try {
                    throw new IllegalArgumentException("From finally thrown");
                } catch (final RuntimeException ex) {
                    throw new IllegalStateException("from finally", ex);
                }
            }
        } catch (final Exception ex) {
            logger.error("As Throwable:", ex); // error(String, Throwable)
            // logger.error("As {}", "String", ex); // error(String, Object arg1, Object arg2)
        }

    }

    @Test
    public void testLimitOnThreadPoolSize() {
        final ExecutorService executorService = Executors.newFixedThreadPool(5);

        final Runnable r1 = new Runnable() {
            @Override
            public void run() {
                try {

                    System.out.println("Hello");
                    Thread.sleep(5000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            executorService.execute(r1);
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) ;

    }

    @Test
    public void testBase64() {
        final String credential = "AeUr2rW6j8wMfwm6SOJUfbisxsKKOe_y" + ":" + "8FoUxsXbGnKDbV_3d6Y1XgTBKZ0Y2D3u";
        System.out.println(Base64.getEncoder().encodeToString(credential.getBytes()));
    }

    @Test
    public void convertMapToJSon() {

        final Map<String, String> credentials = new HashMap<>();
        credentials.put("user", "user1");
        credentials.put("password", "password1");

        final Map<String, Object> oSaaSCredentials = new HashMap<String, Object>();
        oSaaSCredentials.put("tenant_id", "test");
        oSaaSCredentials.put("status", "CREATION_SUCCEEDED");
        oSaaSCredentials.put("credentials", credentials);

        /*final Gson gson = new GsonBuilder().create();
        final String jsonString = gson.toJson(oSaaSCredentials);*/

        final String jsonString = new Gson().toJson(oSaaSCredentials);
        System.out.println(jsonString);

        final JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
        final JsonObject credentials1 = jsonObject.get("credentials").getAsJsonObject();
        System.out.println(credentials1.get("user").getAsString());

    }

    @Test
    public void testThreadLocal() throws Exception {

        final ExecutorService executorService = Executors.newFixedThreadPool(1);


        executorService.execute(new Runnable() {

            @Override
            public void run() {
                MockHandler.handleReq("hello1");
                try {
                    Thread.sleep(2000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
                MockHandler.handleReq("hello2");
            }
        });

        Thread.sleep(5000);
    }

    private static class MockHandler {

        private static final ThreadLocal<String> localThreadVal = new ThreadLocal<String>() {
            @Override
            protected String initialValue() {
                return "Hello";
            }
        };

        static void handleReq(final Object... params) {
            System.out.println(localThreadVal.get());
        }

    }

    abstract class ABC {
        abstract void getAllMetadata();
    }

    class XYZ extends ABC {

        @Override
        void getAllMetadata() {

        }
    }


    @Test
    public void testIntegerOverflow() {
        final Long aLong = new Long("9223372036854775806");
        System.out.println(aLong.intValue());
    }

    @Test
    public void testBoolean() {
        System.out.println(Boolean.parseBoolean(null));
    }

    @Test
    public void testRightShift() {
        checkQCValue(4294967294L);

        checkQCValue(4294967295L);

        checkQCValue(4294967296L);

    }

    private void checkQCValue(final long val) {
        System.out.println("For Value : " + val);
        if (val >> 32 != 0)
            System.out.println("value greater than Max");
        else
            System.out.println("value OK");
    }

    @Test
    public void testDuration() {
        final Duration duration = Duration.ofDays(30);
        System.out.println(Duration.between(Instant.now().truncatedTo(ChronoUnit.DAYS), Instant.now().minus(300, ChronoUnit.DAYS)).isNegative());
    }

    @Test
    public void testPerformanceWithInstantFunc() {

        final long then1 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            System.out.println(Instant.now().truncatedTo(ChronoUnit.DAYS).minus(Duration.ofDays(100)));
        }


        final long then2 = System.currentTimeMillis();
        final Instant t1 = Instant.now().truncatedTo(ChronoUnit.DAYS).minus(Duration.ofDays(100));
        for (int i = 0; i < 100_000; i++) {
            System.out.println(t1);
        }

        System.out.println("Time Diff for Scenario 1: " + (System.currentTimeMillis() - then1));
        System.out.println("Time Diff for Scenario 2: " + (System.currentTimeMillis() - then2));


    }

    @Test
    public void testOptionalOfNull() {
        final Optional<Object> o = Optional.of(null);
        if (o.isPresent()) {
            System.out.println("Present with null !");
        }
    }

    @Test
    public void testConvertToEpochHour() {
        // 2016-11-28T14:58:01.333Z
        // 2016-09-30 01:29:59+0530
        // 2016-11-27T14:40:01.333Z
        // final LocalDateTime localDate = LocalDateTime.of(2016, 9, 29, 19, 29, 59);

        final LocalDateTime localDate = LocalDateTime.of(2016, 11, 27, 14, 40, 1);

        final Instant instant = localDate.toInstant(ZoneOffset.UTC);

        System.out.println(String.format("%012d", instant.toEpochMilli() / 1000 / 3600));

    }

    @Test
    public void testRequireNonNull() {
        Objects.requireNonNull("acb", "should not be null");
    }

    public static final String CONST_TO_BE_CHANGED = "Hello";

    @Test
    public void testConstantChange() {
        System.out.println("Printing actual constnat: " + CONST_TO_BE_CHANGED);

        final String changedConstant = CONST_TO_BE_CHANGED + "Jamal";
        System.out.println(changedConstant);

        System.out.println("Printing actual constnat: " + CONST_TO_BE_CHANGED);
    }

    @Test
    public void testComputeIfAbsent() {
        final Map<String, String> map = new HashMap<>();

        map.put("name", "jamal");
        final String s1 = map.computeIfAbsent("name", s -> "NOT KNOWN");
        System.out.println(s1);

        final String s2 = map.computeIfAbsent("age", s -> "NOT KNOWN");
        System.out.println(s2);

        // computeIfAbsent returns the existing value if present
    }


    @Test
    public void testJsonGet() {

        final JsonObject jsonObject = new Gson().fromJson("{\"name\" : \"jam\", \"age\": \"21\"}", JsonObject.class);

        if (jsonObject.get("age") != null) {
            System.out.println("Property not null!");
        }

        if (jsonObject.get("gender") != null) {
            System.out.println("Still not null");
        }
    }

    @Test
    public void testHasMethodInGson() throws Exception {
        // final JsonObject jsonObject = new Gson().fromJson("{}", JsonObject.class);
        final JsonObject jsonObject = new JsonObject();

        if (jsonObject.has("abc"))
            System.out.println("Not present");
    }


    static class Abc {

        public String v1() {
            return "abc.acutal method - v1";
        }

        public String v2() {
            return "abc.acutal method - v2";
        }
    }

    @Test
    public void testMock() {
        final Abc a1 = Mockito.mock(Abc.class);
        System.out.println(a1.v1());

        final Abc a2 = new Abc();
        System.out.println(a2.v1());
    }

    @Test
    public void testFilePath(){
        System.out.println(PlayGround.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }
    
    @Test
    public void getFirstItemFromList(){
        List<String> list = Collections.singletonList("abc");
        String path = list.stream().findFirst().orElse("/results");
    }
    
    @Test
    public void testReplaceWithNull(){
        String str = "abcHello";
        str = str.replace("abc", null);
        System.out.println(str);
    }
    
    @Test
    public void testGsonParseNull(){
        // returns null json object even with null or "" as input. 
        Gson gson = new Gson();
        String abc = null; 
        JsonObject obj = gson.fromJson("{}", JsonObject.class);
        System.out.println(obj);
    }
    
    
    @Test
    public void testRegExp(){

        String regExp = String.format("^%s\\.%s\\.(.*)$", "rec", "config_1");

        Pattern p = Pattern.compile(regExp);
        System.out.println(p.matcher("rec.config1111.hello").find());

        System.out.println(p.matcher("rec.config_1.ab.cd.ef.gh").find());
        Matcher matcher = p.matcher("rec.config_1.ab.cd.ef.gh");
        int grp = 1; 
        
        while(matcher.find()){
            String group = matcher.group(grp++);
            System.out.println(group);
        }
//        System.out.println(p.matcher("rec.config_1.ab.cd.ef.gh").group(1));
        
    }
    
    @Test
    public void testSumOfStreamWithException(){
        
        List<String> numbers = Arrays.asList("3.1", "2.5", "4.4", "ab", "21", "1.0");
        
        AtomicReference<Double> maxVal = new AtomicReference<>(0.0);
        double sum = numbers.stream()
                .filter(PlayGround::isDouble)
                .map(Double::parseDouble)
                .mapToDouble(Double::doubleValue)
                .peek(seq -> maxVal.set(Double.max(maxVal.get(), seq)))
                .sum();

        System.out.println(maxVal.get());
        System.out.println(sum);
    }
    
    private static boolean isDouble(String val){
        try {
            Double.parseDouble(val);
        } catch (NumberFormatException | NullPointerException ex){
            return false; 
        }
        
        return true; 
    }
    @Test
    public void testPrintMap(){
        Map<String, String> map = new HashMap<>();
        map.put("one", "one");
        map.put("two", "two");

        System.out.println(map);
    }
    
    @Test
    public void testTernaryNull(){
        Map<String, Boolean> map = new HashMap<>();
        map.put("a", true);

        String result = Boolean.TRUE.equals(map.get("a")) ? "Hello" : "Bye!";
        System.out.println(result);
        
        result = Boolean.TRUE.equals(map.get("b")) ? "Hello" : "Bye!"; // get returns null.. 
        System.out.println(result);
    }
    
    @Test
    public void testDivideByZero(){
        Double val = 12.33;
        
        try {
            double res = new Double(10.32) / new Double(0);
            System.out.println(res);
        } catch (ArithmeticException ex) {
            System.out.println("ERR!!");
        }
        
    }
    
    @Test
    public void testInstanceOf() {
        try { 
            getRandomException();
        } catch (Exception ex) {
            if (ex instanceof IllegalArgumentException) {
                System.out.println("Instance of check on IllegalArgumentException - works");
            } 
            
            if (ex instanceof RuntimeException) {
                System.out.println("Instance of check on RuntimeException - works");
            } else {
                System.out.println("Instance of check - failed");
            }
        }
        
    }

    @Test
    public void putNullInJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Object val = null;
        jsonObject.put("name", val);
        System.out.println(jsonObject.toString());
    }

    @Test
    public void putNullInGsonJson() throws JSONException {
        JsonObject jsonObject = new JsonObject();
        String val = null;
        jsonObject.addProperty("name", val);
        System.out.println(jsonObject.toString());
    }

    @Test
    public void testRegExArrayIndexExtractor() {

        String val = "arr[1][2][3]";
        Pattern arrayIndexExtract = Pattern.compile("[^\\[]+\\[(\\d+)\\]");

        Matcher matcher = arrayIndexExtract.matcher(val);
        while(matcher.matches()){
            System.out.println(matcher.group(1));
            val = val.replaceFirst("\\[(\\d+)\\]", "");
            matcher = arrayIndexExtract.matcher(val);
        }
    }

    @Test
    public void testObjectMapperToMap() throws IOException {
        String val = "";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = objectMapper.readValue(val, Map.class);
		System.out.println(map);

	}

    interface FilterQueryMapper {
        <T> T mapFilterParameter(Object ast);
    }

    class FilterToConditionMapper implements FilterQueryMapper {

        @Override
        public String mapFilterParameter(final Object ast) {
            return null;
        }
    }

    public void testMapper() {
        final FilterQueryMapper t1 = new FilterToConditionMapper();
        final Object abcd = t1.mapFilterParameter("abcd");
    }

    static class  SingletonA {
        private final int val;
        private static SingletonA singleInstance;
        private SingletonA() {
            this.val = 5;
        }

        public static SingletonA getInstance(){
            if (singleInstance == null){
                synchronized (SingletonA.class){
                     if (singleInstance == null){
                         singleInstance = new SingletonA();
                     }
                }
            }
            return singleInstance;
        }
    }

    static class  SingletonB {
        private static SingletonB singleInstance  = new SingletonB();
        private final int val;

        private SingletonB() {
            this.val = 10;
        }

        public static SingletonB getInstance(){
            return singleInstance;
        }
    }
    
    private static void getRandomException() {
         throw new IllegalArgumentException("ERR!");
    }
}
