package org.jam.rx.publisher.Kinesis;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import org.reactivestreams.Subscriber;

import java.util.List;

public class KinesisRecordProcessor implements IRecordProcessor {

	private final MultiKinesisSource.KinesisSubscription subscription;
	private final Subscriber<? super Record> subscriber;
	private String shardId;

	public KinesisRecordProcessor(MultiKinesisSource.KinesisSubscription subscription) {
		this.subscription = subscription;
		this.subscriber = subscription.getSubscriber();
	}

	@Override
	public void initialize(String shardId) {
		System.out.println("Initializing shard " + shardId);
		this.shardId = shardId;
	}

	@Override
	public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {

		records.forEach(record -> {

			while (true) {
				if (this.subscription.getDemand().getAndDecrement() > 0) {
					subscriber.onNext(record);
					break;
				} else {
					try {
						subscription.getDemandCondition().await();
					} catch (InterruptedException e) {
						// should handle interrupt
					}
				}
			}
		});

	}

	@Override
	public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason shutdownReason) {
		if (shutdownReason == ShutdownReason.TERMINATE || shutdownReason == ShutdownReason.REQUESTED) {
			try {
				checkpointer.checkpoint();
			} catch (InvalidStateException | ShutdownException e) {
				// todo: exception handling
				e.printStackTrace();
			}
		}
	}
}
