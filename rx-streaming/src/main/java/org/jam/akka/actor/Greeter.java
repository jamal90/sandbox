package org.jam.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Greeter extends AbstractActor {

	public static Props props (final String message, final ActorRef printerActor) {
		return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
	}

	static public class WhoToGreet {
		public final String who;

		public WhoToGreet(String who) {
			this.who = who;
		}
	}

	static public class Greet {
		public Greet() {

		}
	}

	private final String message;
	private final ActorRef printerActor;
	private String greeting = "";

	public Greeter(String message, ActorRef printerActor) {
		this.message = message;
		this.printerActor = printerActor;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(WhoToGreet.class, wtg -> {
					this.greeting = message + ", " + wtg.who;
				})
				.match(Greet.class, greet -> {
					printerActor.tell(new Printer.Greeting(greeting), self());
				})
				.build();
	}
}
