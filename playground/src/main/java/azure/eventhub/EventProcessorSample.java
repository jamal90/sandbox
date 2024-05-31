package azure.eventhub;

import java.util.concurrent.ExecutionException;

public class EventProcessorSample {
	public static void main(String args[]) throws InterruptedException, ExecutionException {
		String consumerGroupName = "$Default";
		String eventHubName = "iot-hub";
		String eventHubConnString = "Endpoint=sb://...";
		String storageConnectionString = "...";
		String storageContainerName = "container-name";
		String hostNamePrefix = "message-mediator-test";

		EventProcessorHost host = new EventProcessorHost(
		EventProcessorHost.createHostName(hostNamePrefix),
				eventHubName,
				consumerGroupName,
				eventHubConnString,
				storageConnectionString,
				storageContainerName);

		System.out.println("Registering host named " + host.getHostName());
		EventProcessorOptions options = new EventProcessorOptions();

		options.setMaxBatchSize(100);
		options.setExceptionNotification(new ErrorNotificationHandler());

		host.registerEventProcessor(EventProcessor.class, options)
				.whenComplete((unused, e) -> {
					if (e != null) {
						System.out.println("Failure while registering: " + e.toString());
						if (e.getCause() != null) {
							System.out.println("Inner exception: " + e.getCause().toString());
						}
					}
				})
				.thenAccept((unused) -> {
					System.out.println("Press enter to stop.");
					try {
						System.in.read();
					} catch (Exception e) {
						System.out.println("Keyb1oard read failed: " + e.toString());
					}
				})
				.thenCompose((unused) -> {
					return host.unregisterEventProcessor();
				})
				.exceptionally((e) -> {
					System.out.println("Failure while unregistering: " + e.toString());
					if (e.getCause() != null) {
						System.out.println("Inner exception: " + e.getCause().toString());
					}
					return null;
				})
				.get();
	}
}