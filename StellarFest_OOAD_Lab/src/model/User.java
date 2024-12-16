package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

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
		
		String query = "INSERT INTO users\r\n" 
		             + "VALUES (?, ?, ?, ?, ?)";
	
		PreparedStatement ps = db.prepareStatement(query);
		
		try {
			ps.setString(1, userID);
			ps.setString(2, email);
			ps.setString(3, name);
			ps.setString(4, password);
			ps.setString(5, role);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean login(String email, String password) {
		User user = getUserByEmail(email);
		
		if(user.getUser_password().equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void changeProfile(String userID, String email, String name, String oldPassword, String newPassword) {
	    StringBuilder query = new StringBuilder("UPDATE users SET ");
	    boolean first = true;

	    if (!name.isEmpty()) {
	        query.append("user_name = ?");
	        first = false;
	    }
	    if (!email.isEmpty()) {
	        if (!first) query.append(", ");
	        query.append("user_email = ?");
	        first = false;
	    }
	    if (!newPassword.isEmpty()) {
	        if (!first) query.append(", ");
	        query.append("user_password = ?");
	    }
	    query.append(" WHERE user_id = ?");

	    try (PreparedStatement ps = db.prepareStatement(query.toString())) {
	        int index = 1;

	        if (!name.isEmpty()) {
	            ps.setString(index++, name);
	        }
	        if (!email.isEmpty()) {
	            ps.setString(index++, email);
	        }
	        if (!newPassword.isEmpty()) {
	            ps.setString(index++, newPassword);
	        }
	        ps.setString(index, userID);

	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
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
	
	public Vector<User> getAllUsers(){
		Vector<User> users = new Vector<>();
		String query = "SELECT * FROM users";
		
		db.resultSet = db.execQuery(query);
		
		try {
			while(db.resultSet.next()) {
				String userId = db.resultSet.getString("user_id");
				String userEmail = db.resultSet.getString("user_email");
				String userName = db.resultSet.getString("user_name");
				String userPassword = db.resultSet.getString("user_password");
				String userRole = db.resultSet.getString("user_role");
				
				users.add(new User(userId, userEmail, userName, userPassword, userRole));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public void deleteUser(String userID) {
		String query = "DELETE FROM users WHERE user_id = ?";
		
	    try {
	        try (PreparedStatement ps = db.prepareStatement(query)) {
	        	ps.setString(1, userID);
	        	ps.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
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
