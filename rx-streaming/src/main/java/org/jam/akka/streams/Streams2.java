package org.jam.akka.streams;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.time.Duration;

public class Streams2 {

	private static final ActorSystem system = ActorSystem.create("stream2");

	public static void main(String[] args) throws InterruptedException {

		final Source<Integer, NotUsed> source = Source.range(1, Integer.MAX_VALUE)
				.throttle(1, Duration.ofSeconds(1))
				.take(5);

		final RunnableGraph<NotUsed> graph = source
				.map(i -> i * 2)
				.to(Sink.foreach(System.out::println));

		graph.run(ActorMaterializer.create(system));

	}

}
