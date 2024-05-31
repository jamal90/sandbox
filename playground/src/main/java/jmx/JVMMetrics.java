package jmx;

import java.util.ArrayList;
import java.util.List;

public class JVMMetrics {


	public static void main(String[] args) throws InterruptedException {

		Thread thread = new Thread(() -> {
			List<String> strings = new ArrayList<>();
			int counter = 0;
			while(counter++ < 1_000_000) {
				strings.add("abcdlafoie");
				try {
					Thread.sleep(1_000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();

		new Thread(() -> {
			// monitoring thread


		});
		thread.join();

	}

}
