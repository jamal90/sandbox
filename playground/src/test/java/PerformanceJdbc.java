import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.test.MySQLConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * test performance of using jdbc template vs plain jdbc - with CF
 */
public class PerformanceJdbc {

    @Test
    public void testJdbcTemplate() throws SQLException {

        final JsonObject mysqlCredentials = new Gson().fromJson("{\"dbname\":\"refactoring_appiot\",\"hostname\":\"localhost\",\"password\":\"root\",\"port\":\"3306\",\"username\":\"root\"}", JsonObject.class);
        final BasicDataSource mysqlDataSource = new MySQLConnectionProvider().initializeDataSource(mysqlCredentials);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(mysqlDataSource);
        jdbcTemplate.setFetchSize(1000);

        final long then = System.currentTimeMillis();
        final List<String> things = jdbcTemplate.query("select * from thing", new RowMapper<String>() {
            @Override
            public String mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                return rs.getString("Thing");
            }
        });
        final long now = System.currentTimeMillis();
        // System.out.println(things.size());
        System.out.println("Time taken to execute: " + (now - then));

    }

    @Test
    public void testPlainJdbc() throws SQLException {
        final JsonObject mysqlCredentials = new Gson().fromJson("{\"dbname\":\"refactoring_appiot\",\"hostname\":\"localhost\",\"password\":\"root\",\"port\":\"3306\",\"username\":\"root\"}", JsonObject.class);
        final BasicDataSource mysqlDataSource = new MySQLConnectionProvider().initializeDataSource(mysqlCredentials);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(mysqlDataSource);

        final long then = System.currentTimeMillis();

        final Connection connToUse = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
        connToUse.setAutoCommit(false);
        final List<String> things = new ArrayList<>();
        try {
            final PreparedStatement preparedStatement = connToUse.prepareStatement("select * from thing");
            preparedStatement.setFetchSize(1000);

            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                things.add(rs.getString("Thing"));
            }
            rs.close();
        } catch (final SQLException e) {
            // ERROR
        } finally {
            DataSourceUtils.releaseConnection(connToUse, jdbcTemplate.getDataSource());
        }

        final long now = System.currentTimeMillis();
        System.out.println("Time taken to execute: " + (now - then));
    }


    @Test
    public void testPlainJdbcWithCF() throws SQLException, ExecutionException, InterruptedException {
        final JsonObject mysqlCredentials = new Gson().fromJson("{\"dbname\":\"refactoring_appiot\",\"hostname\":\"localhost\",\"password\":\"root\",\"port\":\"3306\",\"username\":\"root\"}", JsonObject.class);
        final BasicDataSource mysqlDataSource = new MySQLConnectionProvider().initializeDataSource(mysqlCredentials);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(mysqlDataSource);

        final long then = System.currentTimeMillis();

        final Connection connToUse = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
        connToUse.setAutoCommit(false);

        final CompletableFuture<List<String>> cfThings = CompletableFuture.supplyAsync(() -> {
            final List<String> things = new ArrayList<>();
            try {
                final PreparedStatement preparedStatement = connToUse.prepareStatement("select * from thing");
                preparedStatement.setFetchSize(1000);

                final ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    things.add(rs.getString("Thing"));
                }
                rs.close();
            } catch (final SQLException e) {
                // ERROR
            } finally {
                DataSourceUtils.releaseConnection(connToUse, jdbcTemplate.getDataSource());
            }
            return things;
        });

        final List<String> things = cfThings.get();
        final long now = System.currentTimeMillis();
        System.out.println("Time taken to execute: " + (now - then));
    }
}
