package org.jam.rx.publisher.Kinesis;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleKinesisSubscriber {
	public static void main(String[] args) {

		final ActorSystem actorSystem = ActorSystem.create("simple-kinesis-consumer");

		final MultiKinesisSource multiKinesisSource = new MultiKinesisSource();

		final RunnableGraph<NotUsed> graph = Source.fromPublisher(multiKinesisSource)
				.map(record -> new String(record.getData().array(), StandardCharsets.UTF_8))
//				.grouped(10)
				.to(Sink.foreach(System.out::println));

		graph.run(ActorMaterializer.create(actorSystem));

		final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

		// tenant - 1
		scheduledExecutorService.schedule(() -> {
			System.out.println("Started Stream 1 after 5s");
			multiKinesisSource.addStream("access_key", "access_secret", "eu-central-1", "kinesis-1", 1);
		}, 5, TimeUnit.SECONDS);


		// tenant - 2
		scheduledExecutorService.schedule(() -> {
			System.out.println("Started Stream 2 after 10s");
			multiKinesisSource.addStream("access_key", "access_secret", "eu-central-1", "kinesis-2", 1);
		}, 20, TimeUnit.SECONDS);

	}
}
