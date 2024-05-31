package io.jam.spring.springwebflux.explore;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void testMono() {
        Mono<String> hello = Mono.just("hello")
                .log();

        StepVerifier.create(hello)
                .expectNext("hello")
                .verifyComplete();
    }

    @Test
    public void testMonoError() {
        Mono<Object> monoError = Mono.error(new RuntimeException("mono error"))
                .log();

        StepVerifier.create(monoError)
                .expectError(RuntimeException.class)
                .verify();
    }


}
