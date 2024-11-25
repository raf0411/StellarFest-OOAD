package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private final String USERNAME = "raffi";
	private final String PASSWORD = "soccerball99!";
	private final String DATABASE = "stellarfestdb";
	private final String HOST = "desktop-6mm7jdb";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	public ResultSet resultSet;
	public ResultSetMetaData resultSetMetaData;
	
	private Connection connection;
	private Statement statement;
	private static Database db;
	
	public static Database getInstance() {
		if(db == null) {
			return new Database();
		}
		
		return db;
	}
	
	private Database() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			statement = connection.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			resultSet = statement.executeQuery(query);
			resultSetMetaData = resultSet.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultSet;
	}
	
	public void execUpdate(String query) {
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}
}
