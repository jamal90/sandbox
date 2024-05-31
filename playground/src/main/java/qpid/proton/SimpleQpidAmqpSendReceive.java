//package qpid.proton;
//
//import io.vertx.core.Vertx;
//import io.vertx.core.VertxOptions;
//import io.vertx.proton.ProtonClient;
//import io.vertx.proton.ProtonConnection;
//
//public class SimpleQpidAmqpSendReceive {
//
//	public static void main(String[] args) {
//		Vertx vertx = Vertx.vertx();
//
////		Vertx.vertx(VertxOptions)
//		ProtonClient protonClient = ProtonClient.create(vertx);
//
//		protonClient.connect("localhost", 5672, res -> {
//			if (res.succeeded()) {
//				System.out.println("We're connected");
//
//				ProtonConnection connection = res.result();
//				sendAndReceiveMessage(connection);
//			} else {
//				res.cause().printStackTrace();
//			}
//		});
//	}
//
//	private static void sendAndReceiveMessage(ProtonConnection connection) {
//		System.out.println("done");
//	}
//
//}
