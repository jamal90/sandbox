import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.SocketOptions;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by I076097 on 10/12/2016.
 */
public class CassandraUtil {

    @Test
    public void testCassandraKeyspaceSize() throws ExecutionException, InterruptedException {
        final Cluster cluster = Cluster.builder()
                .addContactPoint("localhost")
                .withPort(12345)
//                .withPort(9042)
                .withCredentials("iotadmin", "5BA;nn")
                .withSocketOptions(
                        new SocketOptions()
                                .setConnectTimeoutMillis(Integer.MAX_VALUE))
                .build();


        final Session session = cluster.connect("dataingestion_002dlr_002dtenant_002d1");
//        final Session session = cluster.connect("default_005ftenant");


        final ResultSetFuture future = session.executeAsync(new SimpleStatement("select count(*) from measurements").setReadTimeoutMillis(Integer.MAX_VALUE));
        final ResultSet resultSet = future.get();

        final List<Row> result = StreamSupport.stream(resultSet.spliterator(), false).collect(Collectors.toList());
        System.out.print("Number of records in measurements table: ");
        result.forEach((row) -> {
            System.out.println(row.getLong(0));
        });

        final ResultSetFuture future_str = session.executeAsync(new SimpleStatement("select count(*) from structures").setReadTimeoutMillis(65000));
        final ResultSet resultSet_str = future_str.get();

        final List<Row> result_str = StreamSupport.stream(resultSet_str.spliterator(), false).collect(Collectors.toList());


        System.out.print("Number of records in structures table: ");
        result_str.forEach((row) -> {
            System.out.println(row.getLong(0));
        });


        final ResultSetFuture future_str_by_id = session.executeAsync(new SimpleStatement("select count(*) from structures").setReadTimeoutMillis(65000));
        final ResultSet resultSet_str_by_id = future_str_by_id.get();

        final List<Row> result_str_by_id = StreamSupport.stream(resultSet_str_by_id.spliterator(), false).collect(Collectors.toList());


        System.out.print("Number of records in structures_by_id table: ");
        result_str_by_id.forEach((row) -> {
            System.out.println(row.getLong(0));
        });


    }

}
