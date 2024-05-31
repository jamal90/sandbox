package org.jam.akka.streams;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.ClosedShape;
import akka.stream.Outlet;
import akka.stream.UniformFanInShape;
import akka.stream.UniformFanOutShape;
import akka.stream.javadsl.Broadcast;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.GraphDSL;
import akka.stream.javadsl.Merge;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.util.concurrent.CompletionStage;

public class GraphDSLMultiSink {

	public static void main(String[] args) {
		final ActorSystem actorSystem = ActorSystem.create("GraphDSL2");

		final Source<Integer, NotUsed> source = Source.range(1, 2);
		final Sink<Object, CompletionStage<Done>> consoleSink = Sink.foreach(System.out::println);

		final Flow<Integer, Integer, NotUsed> f1 = Flow.of(Integer.class).map(i -> i + 10);
		final Flow<Integer, Integer, NotUsed> f2 = Flow.of(Integer.class).map(i -> i + 20);
		final Flow<Integer, Integer, NotUsed> f3 = Flow.of(Integer.class).map(i -> i + 100);
		final Flow<Integer, Integer, NotUsed> f4 = Flow.of(Integer.class).map(i -> i + 40);

		final RunnableGraph<NotUsed> graph = RunnableGraph.fromGraph(
				GraphDSL.create((builder) -> {
					final UniformFanOutShape<Integer, Integer> bcast = builder.add(Broadcast.create(2));

					Outlet outlet = builder.add(source).out();

					outlet = builder.from(outlet)
							.via(builder.add(f1))
							.out();

					builder.from(outlet)
							.via(builder.add(f4))
							.toFanOut(bcast);

					builder
							.from(bcast.out(0))
							.via(builder.add(f2))
							.to(builder.add(Sink.foreach(System.out::println)));

					builder
							.from(bcast.out(1))
							.via(builder.add(f3))
							.to(builder.add(Sink.foreach(System.out::println)));

					return ClosedShape.getInstance();
				})
		);

		graph.run(ActorMaterializer.create(actorSystem));
	}


}
