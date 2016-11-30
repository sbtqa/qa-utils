package ru.sbtqa.tag.qautils.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.sbtqa.tag.qautils.errors.AutotestError;
import ru.sbtqa.tag.qautils.properties.Props;

/**
 * The service for executing queries to database.
 */
public class Connector {

    private final Connection connection;

    public Connector(Connection connection) {
        this.connection = connection;
    }

    /**
     * Create connector to database.
     * Properties file should contains connection url. 
     * 
     * @param driver database jdbc driver
     * @param name connection url name from props.
     * @throws SQLException if there is an error with driver registering or getting connection
     */
    public Connector(Driver driver, String name) throws SQLException {
        DriverManager.registerDriver(driver);
        this.connection = DriverManager.getConnection(Props.get(name + ".url"));
    }

    /**
     * Get query as table. 
     * Example: fetchAll("SELECT ID, NAME FROM USERS").get(0).get("NAME")
     *
     * @param query the query to execute
     * @return a result set
     * @throws java.sql.SQLException if there is an error with executing query
     */
    public List<Map<String, String>> fetchAll(String query) throws SQLException {
        if (null == query || query.isEmpty()) {
            throw new SQLException("Query string is empty");
        }

        try (Statement statement = connection.createStatement()) {
            List<Map<String, String>> results = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData reslutSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, String> resultsTmp = new HashMap<>();
                for (int i = 1; i < reslutSetMetaData.getColumnCount() + 1; i++) {
                    resultsTmp.put(reslutSetMetaData.getColumnName(i), resultSet.getString(reslutSetMetaData.getColumnLabel(i)));
                }
                results.add(resultsTmp);
            }
            
            return results;
        }
    }

    /**
     * Execute query
     * 
     * @param query the query to execute
     * @throws java.sql.SQLException if there is an error with executing query
     */
    public void insertStatic(String query) throws SQLException {
        if (null == query || query.isEmpty()) {
            throw new SQLException("Query string is empty");
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new AutotestError("Failed to create statement for connection " + connection, e);
        }
    }
    
    /**
     * Execute query and return auto-generated key
     * 
     * @param query the query to execute
     * @return an auto-generated key
     * @throws java.sql.SQLException if there is an error with executing query
     */
    public int insertAuto(String query) throws SQLException {
        if (null == query || query.isEmpty()) {
            throw new SQLException("Query string is empty");
        }

        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new AutotestError("Failed to create statement for connection " + connection, e);
        }
    }
}
