package model;

import database.Database;
import util.RandomIDGenerator;

public class Vendor extends User{
	private String accepted_invitations;
	private Database db = Database.getInstance();

	public Vendor(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		// TODO Auto-generated constructor stub
	}
	
	public Vendor() {
		// TODO Auto-generated constructor stub
	}

	public void acceptInvitation(String eventID) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public String manageVendor(String description, String product) {
		String productID = RandomIDGenerator.generateUniqueID();
		
	    String query = "INSERT INTO product " +
                	   "VALUES ('"+ productID +"' ,'"+ product +"', '"+ description +"')";
 
		db.execUpdate(query);
		
		String message = "Manage Success!";
		return message;
	}
	
	public void checkManageVendorInput(String description, String product) {
		
	}
}
