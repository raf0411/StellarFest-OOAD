package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    // Database connection credentials
    private final String USERNAME = ""; // Your database username
    private final String PASSWORD = ""; // Your database password
    private final String DATABASE = ""; // Your database name
    private final String HOST = ""; // Database host (could be localhost or remote server)
    
    // The JDBC connection string to establish connection to the database
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    // Instance variables for ResultSet and ResultSetMetaData, which hold query results and metadata
    public ResultSet resultSet;
    public ResultSetMetaData resultSetMetaData;
    
    // Instance variables for database connection and statement
    private Connection connection;
    private Statement statement;

    // Singleton instance to ensure only one instance of the Database connection
    private static Database db;
    
    /**
     * Singleton method to get the single instance of the Database class.
     * If the instance is null, it creates a new Database instance.
     * 
     * @return Database instance
     */
    public static Database getInstance() {
        if(db == null) {
            return new Database(); // If instance doesn't exist, create it
        }
        
        return db; // Return the existing instance
    }
    
    /**
     * Private constructor that establishes the connection to the MySQL database
     * and creates the statement object. This constructor is called only once
     * when the singleton instance is created.
     */
    private Database() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection using the connection string and credentials
            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            
            // Create a Statement object to execute queries
            statement = connection.createStatement();
            
        } catch (Exception e) {
            // Print stack trace in case of errors during connection or statement creation
            e.printStackTrace();
        }
    }
    
    /**
     * Executes a SQL query (SELECT statement) and returns the ResultSet
     * containing the results of the query.
     * 
     * @param query SQL SELECT query string
     * @return ResultSet containing the results of the query
     */
    public ResultSet execQuery(String query) {
        try {
            // Execute the query and store the result in resultSet
            resultSet = statement.executeQuery(query);
            
            // Retrieve metadata about the result set (such as column names)
            resultSetMetaData = resultSet.getMetaData();
        } catch (SQLException e) {
            // Print stack trace in case of SQL exceptions
            e.printStackTrace();
        }
        
        return resultSet; // Return the result set
    }
    
    /**
     * Executes a SQL query (INSERT, UPDATE, DELETE) that does not return a result set.
     * Typically used for modifying database records.
     * 
     * @param query SQL query string to modify data in the database
     */
    public void execUpdate(String query) {
        try {
            // Execute the update query (INSERT, UPDATE, DELETE)
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // Print stack trace in case of SQL exceptions
            e.printStackTrace();
        }
    }
    
    /**
     * Prepares a SQL statement (used for parameterized queries).
     * 
     * @param query SQL query string with placeholders for parameters
     * @return PreparedStatement object that can be executed later
     */
    public PreparedStatement prepareStatement(String query) {
        PreparedStatement ps = null;
        
        try {
            // Prepare the SQL statement to be executed later
            ps = connection.prepareStatement(query);
        } catch (SQLException e) {
            // Print stack trace in case of SQL exceptions
            e.printStackTrace();
        }
        
        return ps; // Return the prepared statement
    }
}
