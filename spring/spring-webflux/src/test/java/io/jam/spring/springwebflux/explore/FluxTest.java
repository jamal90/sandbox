package io.jam.spring.springwebflux.explore;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void testFlux() {
        Flux<String> stringFlux = Flux.just("spring", "test", "jam")
//                        .concatWith(Flux.error(new RuntimeException("error from publisher")))
                        .concatWith(Flux.just("After Error"))
                        .log(); // logs the details of the reactive flow - subscribe, onSubscribe, request(n), onNext,

        /*
         when no exception is sent from publisher (commenting line 11)

            16:02:49.135 [main] INFO reactor.Flux.Array.1 - | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
            16:02:49.137 [main] INFO reactor.Flux.Array.1 - | request(unbounded)
            16:02:49.137 [main] INFO reactor.Flux.Array.1 - | onNext(spring)
            spring-map
            16:02:49.137 [main] INFO reactor.Flux.Array.1 - | onNext(test)
            test-map
            16:02:49.137 [main] INFO reactor.Flux.Array.1 - | onNext(jam)
            jam-map
            16:02:49.138 [main] INFO reactor.Flux.Array.1 - | onComplete()
         */

        stringFlux
                .map(elem -> elem.concat("-map"))
                .subscribe(System.out::println,
                        Throwable::printStackTrace,
                        ()-> System.out.println("Complete signal from publisher!"));
    }

    @Test
    public void testFluxWithoutError() {
        Flux<String> stringFlux = Flux.just("spring", "test", "jam")
                .map(elem -> elem.concat("-map"))
                .log(); // logs the details of the reactive flow - subscribe, onSubscribe, request(n), onNext,

        // should be in the same sequence as publisher
        StepVerifier.create(stringFlux)
                .expectNext("spring-map")
                .expectNext("test-map")
                .expectNext("jam-map")
                .verifyComplete(); // verify is the one that starts the subscriber that start listening to the publisher
    }

    @Test
    public void testFluxCombineNexts() {
        Flux<String> stringFlux = Flux.just("spring", "test", "jam")
                .map(elem -> elem.concat("-map"))
                .log(); // logs the details of the reactive flow - subscribe, onSubscribe, request(n), onNext,

        // should be in the same sequence as publisher
        StepVerifier.create(stringFlux)
                .expectNext("spring-map", "test-map", "jam-map")
                .verifyComplete(); // verify is the one that starts the subscriber that start listening to the publisher
    }

    @Test
    public void testFluxWithError() {
        Flux<String> stringFlux = Flux.just("spring", "test", "jam")
                .map(elem -> elem.concat("-map"))
                .concatWith(Flux.error(new RuntimeException("error")))
                .log(); // logs the details of the reactive flow - subscribe, onSubscribe, request(n), onNext,

        // should be in the same sequence as publisher
        StepVerifier.create(stringFlux)
                .expectNext("spring-map")
                .expectNext("test-map")
                .expectNext("jam-map")
                .expectErrorMessage("error")
                .verify(); // verify is the one that starts the subscriber that start listening to the publisher
    }

    @Test
    public void testFluxCount() {
        Flux<String> stringFlux = Flux.just("spring", "test", "jam")
                .map(elem -> elem.concat("-map"))
                .log(); // logs the details of the reactive flow - subscribe, onSubscribe, request(n), onNext,

        // should be in the same sequence as publisher
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyComplete(); // verify is the one that starts the subscriber that start listening to the publisher
    }
}
