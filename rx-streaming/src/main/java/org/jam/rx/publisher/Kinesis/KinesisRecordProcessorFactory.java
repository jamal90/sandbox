package org.jam.rx.publisher.Kinesis;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;

public class KinesisRecordProcessorFactory implements IRecordProcessorFactory {

	private final MultiKinesisSource.KinesisSubscription subscription;

	public KinesisRecordProcessorFactory(MultiKinesisSource.KinesisSubscription subscription) {
		this.subscription = subscription;
	}

	@Override
	public KinesisRecordProcessor createProcessor() {
		return new KinesisRecordProcessor(subscription);
	}
}
