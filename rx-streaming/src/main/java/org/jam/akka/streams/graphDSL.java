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

public class graphDSL {

	public static void main(String[] args) {
		final ActorSystem actorSystem = ActorSystem.create("GraphDSL");

		final Source<Integer, NotUsed> source = Source.range(1, 2);
		final Sink<Object, CompletionStage<Done>> consoleSink = Sink.foreach(System.out::println);

		final Flow<Integer, Integer, NotUsed> f1 = Flow.of(Integer.class).map(i -> i + 10);
		final Flow<Integer, Integer, NotUsed> f2 = Flow.of(Integer.class).map(i -> i + 20);
		final Flow<Integer, Integer, NotUsed> f3 = Flow.of(Integer.class).map(i -> i + 30);
		final Flow<Integer, Integer, NotUsed> f4 = Flow.of(Integer.class).map(i -> i + 40);

		final RunnableGraph<CompletionStage<Done>> graph = RunnableGraph.fromGraph(
				GraphDSL.create(consoleSink, (builder, out) -> {
					final UniformFanOutShape<Integer, Integer> bcast = builder.add(Broadcast.create(2));
					final UniformFanInShape<Integer, Integer> merge = builder.add(Merge.create(2));

					final Outlet<Integer> outlet = builder.add(source).out();

					builder
							.from(outlet)
							.via(builder.add(f1))
							.viaFanOut(bcast)
							.via(builder.add(f2))
							.viaFanIn(merge)
							.via(builder.add(f3))
							.to(out);

					builder.from(bcast)
							.via(builder.add(f4))
							.toFanIn(merge);

					return ClosedShape.getInstance();

				})
		);

		graph.run(ActorMaterializer.create(actorSystem))
				.thenRun(actorSystem::terminate);

	}

}
