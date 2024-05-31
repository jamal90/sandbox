package org.jam.rx.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class IntStreamSource implements Publisher<Integer> {

	private final Supplier<Stream<Integer>> intStreamSupplier;

	public IntStreamSource(Supplier<Stream<Integer>> intStreamSupplier) {
		this.intStreamSupplier = intStreamSupplier;
	}

	@Override
	public void subscribe(Subscriber<? super Integer> subscriber) {
		subscriber.onSubscribe(new IntStreamSourceSubscription());

		try {
			final Stream<Integer> integerStream = intStreamSupplier.get();
			integerStream.forEach(subscriber::onNext);
			subscriber.onComplete();
		} catch (RuntimeException ex) {
			subscriber.onError(ex);
		}

	}

	private static class IntStreamSourceSubscription implements Subscription {

		@Override
		public void request(long n) {

		}

		@Override
		public void cancel() {

		}
	}
}