import com.microsoft.azure.eventhubs.EventHubException;
import com.microsoft.azure.eventhubs.PayloadSizeExceededException;
import com.microsoft.azure.eventhubs.QuotaExceededException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class CompletableFutureTests {

	@Test
	public void testClosureKind() throws ExecutionException, InterruptedException {

		final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
		List<CompletableFuture<Void>> allCFs = new ArrayList<>();

		for (Integer num: integers) {
			allCFs.add(CompletableFuture.runAsync(() -> {
				try {
					Thread.sleep(num * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(num);
			}));
		}

		CompletableFuture.allOf(allCFs.toArray(new CompletableFuture[0])).get();
		System.out.println("DONE");

	}

	@Test
	public void testComputeIfAbsent() {

		Map<String, String> map = new HashMap<>();
		System.out.println(map.computeIfAbsent("key1", key -> key + "-value"));


		System.out.println(map.computeIfAbsent("key1", key -> {
			System.out.println("COMPUTING AGAIN");
			return key + "-value";
		}));
	}

	@Test
	public void testEmptyCFsAllOf() throws ExecutionException, InterruptedException {

		List<CompletableFuture<Void>> cfs = new ArrayList<>();

		final CompletableFuture<Void> allCf = CompletableFuture.allOf(cfs.stream().toArray(CompletableFuture[]::new));

		allCf.get();
		System.out.println("DONE!");
	}


	@Test
	public void testExceptionallyCause() throws ExecutionException, InterruptedException {

		CompletableFuture.supplyAsync(() -> {
			throw new RuntimeException("Error");
		})
				.exceptionally(ex -> {
					// NOTE: WRAPPED AS EXECUTION EXCEPTION
					System.out.println(ex);
					return CompletableFuture.completedFuture(null);
				})
		.get();

	}

	@Test
	public void testWhenCompleteCause() throws ExecutionException, InterruptedException {

		CompletableFuture.supplyAsync(() -> {
			throw new RuntimeException("Error");
		})
				.whenCompleteAsync((res, ex) -> {
					ex.printStackTrace();
				})
				.get();



	}

	@Test
	public void testIsAssignable() {

		EventHubException hello = new QuotaExceededException("hello");
		ExecutionException ex = new ExecutionException(hello);

		boolean isTransient = true;

		if (ex instanceof ExecutionException && EventHubException.class.isAssignableFrom(ex.getCause().getClass())) {
			isTransient = ((EventHubException) ex.getCause()).getIsTransient();
		}

		System.out.println(isTransient);

	}

}
