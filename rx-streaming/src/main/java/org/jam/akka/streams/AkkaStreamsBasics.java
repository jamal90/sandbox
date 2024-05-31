package org.jam.akka.streams;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class AkkaStreamsBasics {

	private final ActorSystem system = ActorSystem.create("sample");

	private Flow<String, Integer, NotUsed> parseContent() {
		return Flow.of(String.class)
				.mapConcat(s -> {
					return Arrays.stream(s.split(";"))
							.map(Integer::parseInt)
							.collect(Collectors.toList());
				});
	}

	private Flow<Integer, Double, NotUsed> calculatePairAvg() {
		return Flow.of(Integer.class)
				.grouped(2)
				.mapAsync(8, list ->
						CompletableFuture.supplyAsync(() ->
								list.stream()
										.mapToDouble(v -> v)
										.average()
										.orElse(1.0)
						)
				);
	}

	private Flow<String, Double, NotUsed> calculateAvg() {
		return Flow.of(String.class)
				.via(parseContent())
				.via(calculatePairAvg());
	}

	private Sink<Double, CompletionStage<Done>> storeAvg () {
		return Flow.of(Double.class)
				.mapAsync(4, avg -> CompletableFuture.supplyAsync(() -> {
					System.out.println("AVERAGE - " + avg);
					return avg;
				}))
				.toMat(Sink.ignore(), Keep.right());
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		final AkkaStreamsBasics akkaStreamsBasics = new AkkaStreamsBasics();
		final CompletionStage<Done> done = Source.single("1;10;20;25")
				.via(akkaStreamsBasics.calculateAvg())
				.runWith(akkaStreamsBasics.storeAvg(), ActorMaterializer.create(akkaStreamsBasics.system))
				.whenComplete((d, e) -> {
					if (d != null) {
						System.out.println("DONE");
					} else {
						e.printStackTrace();
					}
				});

		done.toCompletableFuture().get();
		akkaStreamsBasics.system.terminate();
	}

}
