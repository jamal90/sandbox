package org.jam.akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		final ActorSystem system = ActorSystem.create("sample");

		try {
			final ActorRef printer = system.actorOf(Printer.props(), "printer");
			final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", printer), "HelloGreeter");

			helloGreeter.tell(new Greeter.WhoToGreet("Akka"), ActorRef.noSender());
			helloGreeter.tell(new Greeter.Greet(), ActorRef.noSender());

			System.out.println("Enter to exit");
			System.in.read();

		} finally {
			system.terminate();
		}
	}
}