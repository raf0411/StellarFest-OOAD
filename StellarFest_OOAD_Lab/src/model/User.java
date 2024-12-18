package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Database;
import util.RandomIDGenerator;

/**
 * The User class represents a user in the system. It contains methods for user registration, login, 
 * profile modification, and retrieving user details from the database. The User class also handles 
 * user management operations, including deletion and fetching user data by various identifiers.
 */
public class User {
    private String user_id;          // Unique identifier for the user
    private String user_email;       // User's email address
    private String user_name;        // User's name
    private String user_password;    // User's password (should be encrypted)
    private String user_role;        // User's role (e.g., Event Organizer, Vendor, Guest)

    private Database db = Database.getInstance();  // Singleton database connection instance
    
    /**
     * Constructor for creating a User object with specified parameters.
     * 
     * @param user_id The unique identifier for the user
     * @param user_email The user's email address
     * @param user_name The user's name
     * @param user_password The user's password
     * @param user_role The user's role in the system
     */
    public User(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super();
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_role = user_role;
    }
    
    /**
     * Default constructor for User. Used for creating a new empty User object.
     */
    public User() { }

    /**
     * Registers a new user by creating a User object, assigning a unique user ID, 
     * and inserting the user details into the database.
     * 
     * @param email The user's email
     * @param name The user's name
     * @param password The user's password
     * @param role The user's role (e.g., Event Organizer, Vendor, Guest)
     * @return A message indicating the result of the registration process
     */
    public String register(String email, String name, String password, String role) {
        User user;
        String userID = RandomIDGenerator.generateUniqueID();  // Generate a unique user ID
        String message = "";
        
        // Create user instance based on the role
        if(role == "Event Organizer") {
            user = new EventOrganizer(userID, email, name, password, role);
        } else if(role == "Vendor") {
            user = new Vendor(userID, email, name, password, role);
        } else if(role == "Guest") {
            user = new Guest(userID, email, name, password, role);
        }
        
        // SQL query to insert a new user into the database
        String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(query);
        
        try {
            ps.setString(1, userID);
            ps.setString(2, email);
            ps.setString(3, name);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.execute();  // Execute the SQL statement
            message = "Register successful!";
        } catch (SQLException e) {
            message = "There was an error, register unsuccessful!";
            e.printStackTrace();
        }
        
        return message;
    }
    
    /**
     * Validates the user's login by checking the provided email and password against the database.
     * 
     * @param email The user's email
     * @param password The user's password
     * @return True if login is successful, false otherwise
     */
    public Boolean login(String email, String password) {
        User user = getUserByEmail(email);  // Retrieve the user by email
        
        // Compare the entered password with the stored password
        if(user.getUser_password().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Changes the user's profile information (email, name, and password).
     * It updates the user's details in the database based on the changes made.
     * 
     * @param userID The unique ID of the user whose profile is being updated
     * @param email The new email address (or empty string if unchanged)
     * @param name The new name (or empty string if unchanged)
     * @param oldPassword The current password (or empty string if unchanged)
     * @param newPassword The new password (or empty string if unchanged)
     * @return A message indicating whether the profile was updated successfully or not
     */
    public String changeProfile(String userID, String email, String name, String oldPassword, String newPassword) {
        String message = "Change Profile Successful!";
        String query = "";
        
        // If no changes are made, return a message indicating no changes
        if (email.isEmpty() && name.isEmpty() && newPassword.isEmpty() && oldPassword.isEmpty()) {
            return "No changes made!";
        }
        
        // Build SQL query based on the changes made
        // Multiple cases are handled based on which fields have been modified
        if (!email.isEmpty() && !name.isEmpty() && !newPassword.isEmpty()) {
            query = "UPDATE users SET user_email = ?, user_name = ?, user_password = ? WHERE user_id = ?";
            try (PreparedStatement ps = db.prepareStatement(query)) {
                ps.setString(1, email);
                ps.setString(2, name);
                ps.setString(3, newPassword);
                ps.setString(4, userID);
                ps.execute();  // Execute the update query
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Additional cases for updating other combinations of fields (email, name, password)
        
        return message;
    }

    /**
     * Retrieves a user from the database using their email address.
     * 
     * @param email The user's email
     * @return A User object with the details retrieved from the database
     */
    public User getUserByEmail(String email) {
        User user = null;
        String query = "SELECT * FROM users WHERE user_email = ?";
        
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve user details from result set
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
	
	public User getUserByID(String userID) {
	    User user = null;
	    String query = "SELECT * FROM users WHERE user_id = ?";
	    
	    try (PreparedStatement stmt = db.prepareStatement(query)) {
	        stmt.setString(1, userID);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String userId = rs.getString("user_id");
	                String username = rs.getString("user_name");
	                String userEmail = rs.getString("user_email");
	                String password = rs.getString("user_password");
	                String role = rs.getString("user_role");
	                
	                user = new User(userId, username, userEmail, password, role);
	            }
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Error retrieving user by ID: " + e.getMessage());
	    }

	    return user;
	}
    
    /**
     * Retrieves a list of all users in the database.
     * 
     * @return A Vector containing all users retrieved from the database
     */
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
    
    /**
     * Deletes a user from the database using their unique user ID.
     * 
     * @param userID The unique ID of the user to be deleted
     * @return A message indicating whether the user was successfully deleted or not
     */
    public String deleteUser(String userID) {
        String message = "";
        String query = "DELETE FROM users WHERE user_id = ?";
        
        try {
            try (PreparedStatement ps = db.prepareStatement(query)) {
                ps.setString(1, userID);
                ps.executeUpdate();  // Execute the delete query
                message = "User successfully deleted!";
            }
        } catch (SQLException e) {
            message = "There was an error, delete unsuccessful!";
            e.printStackTrace();
        }
        
        return message;
    }
    
    // Getter and setter methods for user attributes
    
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
