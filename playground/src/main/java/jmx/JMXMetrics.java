package jmx;

import com.codahale.metrics.*;
import com.codahale.metrics.jmx.JmxReporter;

import javax.management.JMX;
import java.util.concurrent.TimeUnit;

public class JMXMetrics {
	static final MetricRegistry metrics = new MetricRegistry();
	public static void main(String[] args) throws InterruptedException {
		startReport();

		Meter requests = metrics.meter("requests");
		Counter counters = metrics.counter("counters");
		Timer timer = metrics.timer(MetricRegistry.name(JMXMetrics.class, "timer"));

		Thread thread = new Thread(() -> {
			for (int i = 0; i < 300; i++) {
				counters.inc();
				requests.mark();
				long start = System.currentTimeMillis();
				try {
					TimeUnit.SECONDS.sleep(1);
					timer.update(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
		thread.join();
	}

	static void startReport() {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build();
		reporter.start(10, TimeUnit.SECONDS);

		JmxReporter jmxReporter = JmxReporter.forRegistry(metrics)
				.build();

		jmxReporter.start();

	}

	static void wait5Seconds() {
		try {
			Thread.sleep(300*1000);
		}
		catch(InterruptedException e) {}
	}
}