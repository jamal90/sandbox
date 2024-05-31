import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

/**
 * Created by I076097 on 8/12/2016.
 */
public class LearnRxJava {

    static class Person {

        String name;
        int age;

        Person(final String name, final int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }


        public int getAge() {
            return age;
        }

        public void setAge(final int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return this.name + " at " + this.age;
        }


    }

    @Test
    public void higherOrderFunctions() {
        final Supplier<String> combiner = getStringCombiner("Hello", "World");
        System.out.println(combiner.get());
    }

    private Supplier<String> getStringCombiner(final String s1, final String s2) {
        return () -> s1 + " " + s2;
    }

    @Test
    public void testSimpleObservable() {
        final List<String> items = new ArrayList<>();
        items.add("abc");
        items.add("def");

        final Observable<String> firstObservable = Observable.from(items);
        firstObservable.subscribe(s -> {
            System.out.println(s);
        });

    }


    @Test
    public void createObservableFromFture() {
        final FutureTask<List<String>> futureTask = new FutureTask<List<String>>(() -> {
            return Arrays.asList("ab", "cd", "ef", "gh");
        });

        final Observable<List<String>> observable = Observable.from(futureTask);
        Schedulers.computation().createWorker().schedule(() -> {
            futureTask.run();
        });

        observable.subscribe(list -> {
            list.forEach(System.out::println);
        });
    }

    @Test
    public void testRxJavaThreadSubcribeOnNewThread() throws InterruptedException {
        /**
         * all the observers code run on the new thread created, as we not mentioned specifically
         */
        final Object waitObject = new Object();
        synchronized (waitObject) {
            final List<Integer> listOfInts = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
            final Observable<Integer> listObservable = Observable.from(listOfInts);
            System.out.println(Thread.currentThread().getName());

            final Subscription a = listObservable
                    .subscribeOn(Schedulers.newThread())
                    // .observeOn(Schedulers.io())
                    .subscribe(val -> {
                                System.out.println(Thread.currentThread().getName());
                                System.out.println(val);
                            },
                            (err) -> {
                                err.printStackTrace();
                            },
                            () -> {
                                System.out.println(Thread.currentThread().getName());
                                System.out.println("Completed");
                            });


        }

    }

    @Test
    public void testRxJavaThreadObserveOnNewThread() throws InterruptedException {
        /**
         * all the observers code run on the new thread created, as we not mentioned specifically
         */
        final CountDownLatch waitObject = new CountDownLatch(1);

        final List<Integer> listOfInts = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Observable<Integer> listObservable = Observable.from(listOfInts);
        System.out.println(Thread.currentThread().getName());

        final Subscription a = listObservable
                // .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(val -> {
                            System.out.println(Thread.currentThread().getName());
                            System.out.println(val);
                        },
                        (err) -> {
                            err.printStackTrace();
                        },
                        () -> {
                            System.out.println(Thread.currentThread().getName());
                            System.out.println("Completed");
                            waitObject.countDown();
                        });

        waitObject.await();


    }

    @Test
    public void testRxJavaThreadsMainThread() throws InterruptedException {
        /**
         * all the observers code run on the same thread, as we not mentioned specifically
         */

        final List<Integer> listOfInts = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Observable<Integer> listObservable = Observable.from(listOfInts);
        System.out.println(Thread.currentThread().getName());
        listObservable.subscribe(val -> {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(val);
                },
                (err) -> {
                    err.printStackTrace();
                },
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("Completed");
                });

    }

    /**
     * this method order the person class by age who are less than 40 and then print them out
     */
    @Test
    public void testRxDoComposition() {

        final List<Person> persons = getPersonsList();
        Observable.from(persons)
                .filter(person -> person.getAge() < 40)
                .toSortedList((person, person2) -> {
                    return Integer.valueOf(person.getAge()).compareTo(person2.getAge());
                })
                .doOnCompleted(() -> {
                    System.out.println("Done processing!");
                })
                .subscribe(personList -> {
                            for (final Person person : personList) {
                                System.out.println(person.getName());
                            }
                        }
                );

    }

    @Test
    public void testRxElementAtAndOtherOpsOnList() {
        final List<Person> list = getPersonsList();

        Observable.from(list)
                .elementAt(1)
                .subscribe(elem -> System.out.println(elem.getName()));


        Observable.from(list)
                .elementAtOrDefault(10, new Person("!Jam", 10))
                .subscribe(elem -> System.out.println(elem.getName()));

        Observable.from(list)
                .last()
                .subscribe(System.out::println);

    }

    @Test
    public void testScanTransformation() {
        final List<String> names = Arrays.asList("ab", "cd", "ef", "gh", "ij", "kl", "mn", "op", "qr", "st", "uv", "wx", "yz");
        Observable.from(names)
                .scan(new StringBuilder(), (accumulated, currValue) -> {
                    return accumulated.append(currValue);
                })
                .subscribe(stringBuilder -> System.out.println(stringBuilder.toString()));


        System.out.println("***** last state******");
        Observable.from(names)
                .scan(new StringBuilder(), (accumulated, currValue) -> {
                    return accumulated.append(currValue);
                })
                .last()
                .subscribe(stringBuilder -> System.out.println(stringBuilder.toString()));


    }

    @Test
    public void testGroupBy() {
        final List<Person> persons = getPersonsList();
        Observable.from(persons)
                .groupBy(person -> person.getAge() < 30 ? "YOUNG" : "OLD")
                .subscribe(grouped -> {
                    System.out.println("Start New Group" + grouped.getKey());
                    grouped.subscribe(person -> {
                        System.out.println(grouped.getKey() + "::" + "(" + person.getName() + "," + person.getAge() + ")");
                    });
                });
    }

    @Test
    public void testSubscribeOn() throws InterruptedException {

        /**
         * the thread on which we do subscribe is responsible for pushing down the emissions from observable to the subscribers
         * <b>
         * In summary, subscribeOn() instructs the source Observable which thread to emit items on, and this thread will push items all the way to the Subscriber.*
         * </b>
         *
         */

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Observable<String> pairs = Observable.just("abc", "def", "ghi");
        System.out.println(Thread.currentThread().getName());

        pairs.subscribeOn(Schedulers.computation()).map(String::length)
                .doOnCompleted(() -> countDownLatch.countDown())
                .subscribe(len -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    System.out.println(len);
                });

        countDownLatch.await();
    }

    @Test
    public void testObserveOn() throws Exception {

        /**
         * observeOn move the emission of items to a new thread. From now on we can two threads effectivley
         * One - T1 - Tx(1) -> Tx(2) -> ObserveOn(T2) -> Tx(3) -> Tx(4) -> subscribeOn
         * From above, T1 produce an item till Tx(2) and then moves this to the new thread. now T1 can start producing the next item
         * Effectively, you can only use one subscribeOn(), but you can have any number of observeOn() operators.
         * You can switch emissions from one thread to another with ease using observeOn().
         *
         */

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Observable<Integer> integerObservable = Observable.just(1, 2, 3, 4, 5, 6, 7);

        integerObservable.map(num -> num + 1)
                .map(num -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    return num * 5;
                })
                .observeOn(Schedulers.newThread())
                .map(num -> {
                    System.out.println("Now on new thread: " + Thread.currentThread().getName());
                    return num - 1;
                })
                .doOnCompleted(() -> countDownLatch.countDown())
                .subscribe((finalRes) -> {
                    System.out.println(" Final Thread : " + Thread.currentThread().getName());
                    System.out.println(finalRes);
                });

        countDownLatch.await();
    }

    @Test
    public void tryFlatMap() {

        final Observable<String> firstObservable = Observable.just("a,b,c", "d,e,f", "g,h,i", "j,k,l");

        firstObservable.flatMap((str) -> Observable.from(str.split(",")))
                .forEach(System.out::println);
    }

    @Test
    public void testCreatingObservable() throws Exception {

        final Observable<String> observableSimple = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hellow World");
                        subscriber.onCompleted();
                    }
                }
        );

        final Subscriber<String> simpleSubscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {
                System.out.println("Completed emitting items");
            }

            @Override
            public void onError(final Throwable throwable) {

            }

            @Override
            public void onNext(final String s) {
                System.out.println("Observable Emitting: " + s);
            }
        };

        observableSimple.subscribe(simpleSubscriber);


    }

    private List<Person> getPersonsList() {
        final List<Person> persons = new ArrayList<>();
        persons.add(new Person("Jam", 41));
        persons.add(new Person("Jam1", 22));
        persons.add(new Person("Jam2", 34));
        persons.add(new Person("Jam3", 23));
        persons.add(new Person("Jam4", 6));
        persons.add(new Person("Jam5", 21));
        persons.add(new Person("Jam6", 51));
        return persons;
    }


}
