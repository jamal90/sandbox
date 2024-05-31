package cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class CassandraDataGen {

    public static void main(String[] args) {
        Cluster cassandraCluster = Cluster.builder()
                .addContactPoint("iotcosmoscassandradb.cassandra.cosmos.azure.com")
                .withPort(10350)
                .withCredentials("iotcosmoscassandradb", "s9wbRJjeyiV6cjJoP1K8L7glBrs5PhHhcP9pzIyXbdyoEFHL39YhnSpI9lNUoNzeZ0E6VoSbkNFm2vmlH0QByA==")
                .withSSL()
                .build();

        Session db = cassandraCluster.connect();

        ResultSet result = db.execute("select * from test.test");
        result.forEach(row -> {
            System.out.println(row.getInt(0));
            // do nothing;
        });
    }

}
