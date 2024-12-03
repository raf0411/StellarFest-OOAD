package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;
import util.RandomIDGenerator;

public class User {
	private String user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	
	private Database db = Database.getInstance();
	
	public User(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super();
		this.user_id = user_id;
		this.user_email = user_email;
		this.user_name = user_name;
		this.user_password = user_password;
		this.user_role = user_role;
	}
	
	public User(String user_id, String user_email, String user_name, String user_role) {
		super();
		this.user_id = user_id;
		this.user_email = user_email;
		this.user_name = user_name;
		this.user_role = user_role;
	}
	
	public User() {
		
	}

	public void register(String email, String name, String password, String role) {
		User user;
		String userID = RandomIDGenerator.generateUniqueID();
		
		if(role == "Event Organizer") {
			user = new EventOrganizer(userID, email, name, password, role);
		} else if(role == "Vendor") {
			user = new Vendor(userID, email, name, password, role);
		} else if(role == "Guest") {
			user = new Guest(userID, email, name, password, role);
		}
		
		String query = "INSERT INTO users " +
				   	   "VALUES ('"+ userID +"', '"+ email +"', '"+ name +"', '"+ password +"', '"+ role +"')";
	
		db.execUpdate(query);
	}
	
	public Boolean login(String email, String password) {
		User user = getUserByEmail(email);
		
		if(user.getUser_password().equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void changeProfile(String email, String name, String oldPassword, String newPassword) {
		
	}
	
	public User getUserByEmail(String email) {
	    User user = null;
	    String query = "SELECT * FROM users WHERE user_email = ?";
	    
	    try (PreparedStatement stmt = db.getInstance().prepareStatement(query)) {
	        stmt.setString(1, email);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String userID = rs.getString("user_id");
	                String username = rs.getString("user_name");
	                String userEmail = rs.getString("user_email");
	                String password = rs.getString("user_password");
	                String role = rs.getString("user_role");
	                
	                user = new User(userID, username, userEmail, password, role);
	            }
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Error retrieving user by email: " + e.getMessage());
	    }

	    return user;
	}
	
	public User getUserByUsername(String name) {
	    User user = null;
	    String query = "SELECT * FROM users WHERE user_name = ?";
	    
	    try (PreparedStatement stmt = db.prepareStatement(query)) {
	        stmt.setString(1, name);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String userID = rs.getString("user_id");
	                String username = rs.getString("user_name");
	                String userEmail = rs.getString("user_email");
	                String password = rs.getString("user_password");
	                String role = rs.getString("user_role");
	                
	                user = new User(userID, username, userEmail, password, role);
	            }
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Error retrieving user by email: " + e.getMessage());
	    }

	    return user;
	}
	
	public void checkRegisterInput(String email, String name, String password) {
		
	}
	
	public void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		
	}
	
	//SETTER AND GETTER
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
}
