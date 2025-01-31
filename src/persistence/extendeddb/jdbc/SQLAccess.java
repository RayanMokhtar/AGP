package persistence.extendeddb.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQLSearcher class
 * 
 * Used to extract values from the database.
 */
public class SQLAccess {
    private String url;
    private String user;
    private String password;
    private Connection jdbcConnection;

    /**
     * SQLSearcher constructor
     * 
     * @param url      The url of the database.
     * @param user     A user of the database.
     * @param password Its password.
     */
    public SQLAccess(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * getConnection
     * 
     * Initiates a connection to the database (Singleton).
     * 
     * @throws SQLException
     * @return Connection
     */
    private Connection getConnection() throws SQLException {
    	 if (jdbcConnection == null) {
    	     String fullUrl = "jdbc:mysql://mysql-agp-antilles.alwaysdata.net:3306/agp-antilles_database?useSSL=false&serverTimezone=UTC";

             jdbcConnection = DriverManager.getConnection(fullUrl, user, password);
         }
         return jdbcConnection;
     }
       

    /**
     * search
     * 
     * Method used to execute SQL queries on the database.
     * 
     * @param query An SQL query.
     * @throws SQLException
     * @return SQLResults
     */
    public SQLResults search(String query) throws SQLException {
        Statement state;
        ResultSet result;
        ResultSetMetaData metaData;
        int numberAttributes;
        String attribute;
        String value;
        SQLResult tuple;
        SQLResults sqlResults;

        state = getConnection().createStatement();
        result = state.executeQuery(query);
        metaData = result.getMetaData();
        numberAttributes = metaData.getColumnCount();

        sqlResults = new SQLResults();

        while (result.next()) {
            tuple = new SQLResult();

            for (int i = 1; i <= numberAttributes; i++) {
                attribute = metaData.getColumnName(i);
                value = result.getObject(i).toString();
                tuple.addAttribute(attribute, value);
            }

            sqlResults.addTuple(tuple);
        }

        state.close();
        return sqlResults;
    }

    /**
     * close
     * 
     * Method used to close the database connection.
     * Use there as soon as you finish questioning the database.
     * 
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (jdbcConnection != null) {
            jdbcConnection.close();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}