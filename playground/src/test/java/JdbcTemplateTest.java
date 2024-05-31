import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by I076097 on 10/15/2016
 */
public class JdbcTemplateTest {

    public static org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void setUpConnection() {
        final JsonObject credentials = new Gson().fromJson("{\"dbname\":\"APPIOT\",\"hostname\":\"localhost\",\"password\":\"...\",\"port\":\"30041\",\"username\":\"root\"}", JsonObject.class);
        final String url = "jdbc:mysql://" + credentials.get("hostname").getAsString() + ":" + credentials.get("port").getAsInt() + "/" + credentials.get("dbname").getAsString();
        final DriverManagerDataSource dataSource = new DriverManagerDataSource(url, credentials.get("username").getAsString(), credentials.get("password").getAsString());
        jdbcTemplate = new org.springframework.jdbc.core.JdbcTemplate(dataSource);
    }


    @Test
    public void testSimpleQuery() {
        final List<String> list = jdbcTemplate.query("select * from \"APPIOT\".\"appiot.ds.p::DS.Thing\" limit 100", new Object[]{}, new RowMapper<String>() {
            @Override
            public String mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                return rs.getString("Name");
            }
        });

        System.out.println(list.size());
    }

    @Test
    public void testSimpleQueryMySQL() {
        final List<String> list = jdbcTemplate.query("select * from `table1` limit 100", new Object[]{}, new RowMapper<String>() {
            @Override
            public String mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                System.out.println(rs.getString("Name"));
                return rs.getString("Name");
            }
        });
    }

    @Test
    public void testProcedureCallWithPlainJdbc() throws SQLException {
        final Connection connection = jdbcTemplate.getDataSource().getConnection();
        final CallableStatement callableStatement = connection.prepareCall("{call `new_procedure_3` (?, ?)}");
        callableStatement.setString(1, "Jamal1");
        callableStatement.registerOutParameter(2, Types.VARCHAR);
        callableStatement.executeUpdate();
        System.out.println(callableStatement.getString(2));

        // ==================
        // multiple return table types
        // =================
        System.out.println("\n\n");
        System.out.println("multiple return table types");
        final CallableStatement callableStatement1 = connection.prepareCall("{call `new_procedure_1` (?, ?)}");
        callableStatement1.setString(1, "Jamal1");
        callableStatement1.registerOutParameter(2, Types.VARCHAR);

        int resultSetsReturned = 0;
        final boolean executed = callableStatement1.execute();
        final List<ResultSet> resultSets = new ArrayList<>();
        do {
            final ResultSet resultSet = callableStatement1.getResultSet();
            resultSetsReturned++;
            resultSets.add(resultSet);

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("Id") + "," + resultSet.getString("Name"));
            }

        } while (callableStatement1.getMoreResults());

       /* for (final ResultSet resultSet : resultSets) {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("Id") + "," + resultSet.getString("Name"));
            }
        }*/

//        System.out.println(callableStatement1.getMoreResults(0));
        System.out.println(callableStatement1.getMoreResults(1));
        System.out.println(callableStatement1.getMoreResults(2));
        System.out.println(callableStatement1.getMoreResults(3));

        System.out.println(callableStatement1.getString(2));

    }

    @Test
    public void testProcedureCallWithJdbcTemplate() {


        final SqlParameter param1 = new SqlParameter(Types.VARCHAR);
        final SqlOutParameter outParam1 = new SqlOutParameter("out_param1", Types.VARCHAR);
        final List<SqlParameter> paramList = new ArrayList<>();
        paramList.add(param1);
        paramList.add(outParam1);

        final String procedureCallStmt = "{call `new_procedure_3` (?, ?)}";

        // ======================
        // not type safe - returns objects
        // ======================
        final Map<String, Object> results = jdbcTemplate.call(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(final Connection con) throws SQLException {
                final CallableStatement callableStatement = con.prepareCall(procedureCallStmt);
                callableStatement.setObject(1, "Jamal1");
                callableStatement.registerOutParameter(2, Types.VARCHAR);
                return callableStatement;
            }
        }, paramList);

        System.out.println(results.get("out_param1"));
        System.out.println(results);

        // ====================
        // other way - typed
        // ====================

        System.out.println("\n\n Using CallableStatementCreator");

        final String resFromCallback = jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(final Connection con) throws SQLException {
                final CallableStatement callableStatement = con.prepareCall(procedureCallStmt);
                callableStatement.setObject(1, "Jamal1");
                callableStatement.registerOutParameter(2, Types.JAVA_OBJECT);
                return callableStatement;
            }
        }, new CallableStatementCallback<String>() {
            @Override
            public String doInCallableStatement(final CallableStatement cs) throws SQLException, DataAccessException {
                final boolean execute = cs.execute();
                return cs.getString(2);
                // close the resultSets that are created here
            }
        });

        System.out.println(resFromCallback);

        // ========================================
        // multiple result sets from same procedure
        // ========================================
        final String preparedProcStmtWithMultipleReturns = "{call `new_procedure_1` (?, ?) }";
        System.out.println("\n\n");
        System.out.println("multiple return table types");

        jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(final Connection con) throws SQLException {
                final CallableStatement callableStatement = con.prepareCall(preparedProcStmtWithMultipleReturns);
                callableStatement.setObject(1, "Jamal1");
                callableStatement.registerOutParameter(2, Types.JAVA_OBJECT);
                return callableStatement;
            }
        }, new CallableStatementCallback<String>() {
            @Override
            public String doInCallableStatement(final CallableStatement cs) throws SQLException, DataAccessException {
                int resultSetsReturned = 0;
                final boolean executed = cs.execute();
                do {
                    resultSetsReturned++;
                    final ResultSet rs = cs.getResultSet();

                    while (rs.next()) {
                        System.out.println(rs.getInt("Id") + "," + rs.getString("Name"));
                    }

                } while (cs.getMoreResults(resultSetsReturned));

                return cs.getString(2);
            }
        });
    }


}
