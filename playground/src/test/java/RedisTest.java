import com.github.zxl0714.redismock.RedisServer;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by I076097 on 11/16/2016
 */
public class RedisTest {

    private static Jedis jedisConnection;

    @BeforeClass
    public static void setupJedisConnection() throws IOException {
        final RedisServer redisServer = RedisServer.newRedisServer();
        jedisConnection = new Jedis("localhost", 6379);
    }

    @Test
    public void simpleString() {
        jedisConnection.set("name", "Jamal");
        System.out.println(jedisConnection.get("name"));
    }


    @Test
    public void testConnection() {
        System.out.println(jedisConnection.isConnected());
        jedisConnection.connect();
        jedisConnection.set("name", "jamal");
        System.out.println(jedisConnection.isConnected());
        System.out.println(jedisConnection.get("name"));
        jedisConnection.close();
        System.out.println(jedisConnection.isConnected());
        System.out.println(jedisConnection.get("name"));
    }

    @Test
    public void storeHash() {
        final Map<String, String> val = new HashMap<>();
        val.put("one", "1");
        val.put("two", "2");
        val.put("three", "3");

        jedisConnection.hmset("mapObj1", val);
    }

}
