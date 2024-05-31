import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;

public class GuavaCacheTest {

	@Test
	public void testNullFromLoadMethod() {

		final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String s) throws Exception {
						if (s.equals("nine")) {
							return getAny();
						} else if (s.equals("two")) {
							return getTwo();
						} else {
							return s.toUpperCase();
						}
					}
				});


		try {
//			System.out.println(cache.get("one"));
			System.out.println(cache.get("two"));
//			System.out.println(cache.get("nine"));
		} catch (ExecutionException ex) {
			System.out.println("Error! - " + ex.getMessage());
		}


	}

	private String getTwo() {
		throw new RuntimeException("Error from method");
	}

	private String getAny() {
		return null;
	}

}
