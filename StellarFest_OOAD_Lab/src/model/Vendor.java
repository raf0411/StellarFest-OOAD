package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Database;
import util.RandomIDGenerator;

public class Vendor extends User{
	private String accepted_invitations;
	private Vector<Vendor> vendors;
	private static Database db = Database.getInstance();

	public Vendor(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
	}
	
	public Vendor() {
	}

	public void acceptInvitation(String eventID) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public String manageVendor(String description, String product) {
	    String productID = RandomIDGenerator.generateUniqueID();
	    String message = "Manage Success!";
	    
	    String query = "INSERT INTO product (product_id, product_name, product_description) VALUES (?, ?, ?)";
	    
        PreparedStatement ps = db.prepareStatement(query);
	    
        try{ 
	    	ps.setString(1, productID);   
	        ps.setString(2, product);  
	        ps.setString(3, description); 
	        
	        ps.execute();
	    } catch (Exception e) {
	        e.printStackTrace();
	        message = "Error manage product!";
	    }
	    
	    return message;
	}
	
	public void checkManageVendorInput(String description, String product) {
		
	}
	
	public static Vector<Vendor> getVendorsByTransactionID(String eventID) {
	       Vector<Vendor> vendors = new Vector<Vendor>();

	        try {
	            String query = "SELECT u.user_id, u.user_email, u.user_name, u.user_role " +
	                           "FROM attendees ea " +
	                           "JOIN users u ON ea.attendee_id = u.user_id " +
	                           "WHERE ea.event_id = ? AND u.user_role = 'vendor'";
	            PreparedStatement ps = db.prepareStatement(query);
	            ps.setString(1, eventID);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                String userId = rs.getString("user_id");
	                String email = rs.getString("user_email");
	                String name = rs.getString("user_name");
	                String password = rs.getString("user_password");
	                String role = rs.getString("user_role");

	                vendors.add(new Vendor(userId, email, name, password, role));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	   return vendors;
	}
	
	public Vector<Vendor> getVendors(){
		Vector<Vendor> vendors = new Vector<Vendor>();
		
        try {
        	String query = "SELECT * FROM users WHERE user_role = 'Vendor'";
            
            PreparedStatement ps = db.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("user_id");
                String email = rs.getString("user_email");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String role = rs.getString("user_role");

                vendors.add(new Vendor(userId, email, name, password, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return vendors;
	}
}