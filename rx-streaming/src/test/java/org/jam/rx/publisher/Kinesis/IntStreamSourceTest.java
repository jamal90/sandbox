package org.jam.rx.publisher.Kinesis;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.jam.rx.publisher.IntStreamSource;
import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class IntStreamSourceTest extends PublisherVerification<Integer> {

	public IntStreamSourceTest() {
		super(new TestEnvironment());
	}

	@Override
	public Publisher<Integer> createPublisher(long l) {
		return new IntStreamSource(() -> IntStream.range(0, (int) l).boxed());
	}

	@Override
	public Publisher<Integer> createFailedPublisher() {
		return new IntStreamSource(() -> {
			throw new RuntimeException("Error");
		});
	}

	public static void main(String[] args) {
		final ActorSystem actorSystem = ActorSystem.create("simple-kinesis-consumer");

		final RunnableGraph<NotUsed> graph = Source.fromPublisher(new IntStreamSource(() -> IntStream.range(0, 10).boxed()))
				.mapAsync(3, i -> CompletableFuture.supplyAsync(() -> i + 10))
				.to(Sink.foreach(System.out::println));

		graph.run(ActorMaterializer.create(actorSystem));
	}
}