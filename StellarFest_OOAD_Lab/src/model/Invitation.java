package model;

import java.sql.SQLException;
import java.util.Vector;

import database.Database;
import util.RandomIDGenerator;

public class Invitation {
	private String invitation_id;
	private String event_id;
	private String user_id;
	private String invitation_status;
	private String invitation_role;
	private Database db = Database.getInstance();
	
	public Invitation(String invitation_id, String event_id, String user_id, String invitation_status,
			String invitation_role) {
		super();
		this.invitation_id = invitation_id;
		this.event_id = event_id;
		this.user_id = user_id;
		this.invitation_status = invitation_status;
		this.invitation_role = invitation_role;
	}
	
	public Invitation() {
		
	}

	public void sendInvitation(String email) {
		
	}
	
	public String acceptInvitation(String eventID, String userID, String invitationRole) {
	    Invitation invitation;
	    String invitationID = RandomIDGenerator.generateUniqueID();
	    
	    invitation = new Invitation(invitationID, eventID, userID, "Accepted", invitationRole);
	    String message = "Invitation Accepted!";
	    
	    String query = "INSERT INTO invitation " +
	                   "VALUES ('"+ invitationID +"', '"+ eventID +"', '"+ userID +"', '"+ "Accepted" +"', '"+ invitationRole +"')";
	    
	    db.execUpdate(query);
	    
	    String queryDelete = "DELETE FROM invitation " +
                "WHERE event_id = '" + eventID + "' " +
                "AND user_id = '" + userID + "' " +
                "AND invitation_status = 'Pending'";
	    
		db.execUpdate(queryDelete);
	    
	    return message;
	}

	
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = new Vector<Event>();
		String query = "SELECT events.event_id, " +
		               "events.event_name, " +
		               "events.event_date, " +
		               "events.event_location, " +
		               "events.event_description, " +
		               "users.user_id " +
		               "FROM invitation " +
		               "JOIN events ON invitation.event_id = events.event_id " +
		               "JOIN users ON invitation.user_id = users.user_id " + 
		               "WHERE users.user_email = '"+ email +"' " +
		               "AND invitation.invitation_status != 'Accepted'";
		
		db.resultSet = db.execQuery(query);
		
		try {
			while(db.resultSet.next()) {
				String eventId = db.resultSet.getString("events.event_id");
				String eventName = db.resultSet.getString("events.event_name");
				String eventDate = db.resultSet.getString("events.event_date");
				String eventLocation = db.resultSet.getString("events.event_location");
				String eventDescription = db.resultSet.getString("events.event_description");
				String organizerId = db.resultSet.getString("users.user_id");
				
				invitations.add(new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return invitations;
	}
	
	// SETTER AND GETTER
	public String getInvitation_id() {
		return invitation_id;
	}

	public void setInvitation_id(String invitation_id) {
		this.invitation_id = invitation_id;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getInvitation_status() {
		return invitation_status;
	}

	public void setInvitation_status(String invitation_status) {
		this.invitation_status = invitation_status;
	}

	public String getInvitation_role() {
		return invitation_role;
	}

	public void setInvitation_role(String invitation_role) {
		this.invitation_role = invitation_role;
	}
}
