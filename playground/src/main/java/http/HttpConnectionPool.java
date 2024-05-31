package http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpConnectionPool {

	public static void main(String[] args) throws InterruptedException {

		PoolingHttpClientConnectionManager connManager
				= new PoolingHttpClientConnectionManager();
		connManager.setDefaultMaxPerRoute(2);
		connManager.setMaxTotal(2);


		CloseableHttpClient client = HttpClients.custom()
				.setConnectionManager(connManager)
				.build();

		MultiHttpClientConnThread[] threads
				= new  MultiHttpClientConnThread[10];
		for(int i = 0; i < threads.length; i++){
			threads[i] = new MultiHttpClientConnThread(client, connManager);
		}
		for (MultiHttpClientConnThread thread: threads) {
			thread.start();
		}
		for (MultiHttpClientConnThread thread: threads) {
			thread.join(1000);
		}

		TimeUnit.MINUTES.sleep(20L);
	}

	public static class MultiHttpClientConnThread extends Thread {

		private final CloseableHttpClient client;
		private final PoolingHttpClientConnectionManager cm;

		public MultiHttpClientConnThread(CloseableHttpClient client, PoolingHttpClientConnectionManager connManager) {
			this.client = client;
			this.cm = connManager;
		}

		@Override
		public void run() {
			HttpGet get = new HttpGet("https://www.google.com");

			try {
				System.out.println(String.format("[Before] Running thread %s - Leased Connections: %d", Thread.currentThread().getName(), cm.getTotalStats().getLeased()));
				CloseableHttpResponse resp = client.execute(get);

				System.out.println(String.format("[After] Running thread %s - Leased Connections: %d", Thread.currentThread().getName(), cm.getTotalStats().getLeased()));
				String s = EntityUtils.toString(resp.getEntity());



//				System.out.println("RES: " + s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
