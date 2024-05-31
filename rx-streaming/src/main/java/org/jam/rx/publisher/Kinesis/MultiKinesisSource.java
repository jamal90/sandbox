package org.jam.rx.publisher.Kinesis;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.metrics.impl.NullMetricsFactory;
import com.amazonaws.services.kinesis.metrics.interfaces.IMetricsFactory;
import com.amazonaws.services.kinesis.model.Record;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiKinesisSource implements Publisher<Record> {

	private static final int NUM_GROUPS = 5; // tmp - to be passed from main-engine
	private KinesisSubscription kinesisSubscription;
	private static final boolean LOCAL_RUN = Boolean.parseBoolean(System.getenv("LOCAL_RUN"));

	@Override
	public void subscribe(Subscriber<? super Record> subscriber) {

		// there can be only one subscription to the publisher - hence storing the subscription
		kinesisSubscription = new KinesisSubscription(subscriber);

		subscriber.onSubscribe(kinesisSubscription);
		// initialize the record processor factory
	}

	// tmp - notify source of sub-engine that a new is added
	public void addStream(String accessKey, String accessSecret, String region, String streamName, int groupIndex) {
		// todo: will be called for each new tenant requested to be processed by this group

		if (groupIndex == isAssigned(region, streamName) || LOCAL_RUN) {
			kinesisSubscription.listenToStream(accessKey, accessSecret, region, streamName);
		}
	}

	private int isAssigned(String region, String streamName) {
		// use consistent hashing - to be reshuffled when the no. of groups is increased / decreased
		return Objects.hash(region, streamName) % NUM_GROUPS;
	}

	public static class KinesisSubscription implements Subscription {

		private final Subscriber<? super Record> subscriber;
		private final AtomicLong demand = new AtomicLong(0);

		private final Lock lock = new ReentrantLock();
		private final Condition demandCondition = lock.newCondition();

		private final ExecutorService executorService;
		private Map<String, Worker> workers = new HashMap<>();


		public KinesisSubscription(Subscriber<? super Record> subscriber) {
			this.subscriber = subscriber;
			executorService = Executors.newFixedThreadPool(5);
		}

		private void listenToStream(String accessKey, String accessSecret, String region, String streamName) {

			final String kclAppName = String.format("%s-%s%s", "rx-publisher", region, streamName);

			BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, accessSecret);
			AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(cred);

			// Set KCL configuration
			String workerId = UUID.randomUUID().toString(); // will be unique per group instance

			KinesisClientLibConfiguration kclConfiguration = new KinesisClientLibConfiguration(kclAppName, streamName, credentialsProvider, workerId);

			kclConfiguration.withRegionName(region);
			kclConfiguration.withInitialPositionInStream(InitialPositionInStream.LATEST);

			// Start workers
			IRecordProcessorFactory recordProcessorFactory = new KinesisRecordProcessorFactory(this);
			IMetricsFactory metricsFactory = new NullMetricsFactory();

			executorService.execute(() -> {

				// running the executor running a separate thread
				Worker worker = new Worker.Builder().recordProcessorFactory(recordProcessorFactory).config(kclConfiguration)
						.metricsFactory(metricsFactory).build();
				try {
					System.out.printf("Running %s to process stream %s as worker %s...\n", kclAppName, streamName, workerId);
					worker.run();
					workers.put(region + "-" + streamName, worker);

				} catch (Throwable t) {
					System.err.println("Caught throwable while processing data.");
					t.printStackTrace();
				}
			});
		}

		@Override
		public void request(long n) {
			System.out.println("Incoming Demand: " + n);
			demand.addAndGet(n);

			try {
				lock.lock();
				demandCondition.signalAll();
			} finally {
				lock.unlock();
			}
		}

		@Override
		public void cancel() {
			System.out.println("Calling cancel");
			if (workers != null) {
				workers.values().forEach(Worker::shutdown);
			}
		}

		public AtomicLong getDemand() {
			return demand;
		}

		public Subscriber<? super Record> getSubscriber() {
			return subscriber;
		}

		public Condition getDemandCondition() {
			return demandCondition;
		}
	}
}
