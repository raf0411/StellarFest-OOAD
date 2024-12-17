package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	private User user = new User();
	
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

	public void sendInvitation(String email, String eventID) {
		String invitationID = RandomIDGenerator.generateUniqueID();
		String query = "INSERT INTO invitation\r\n"
				     + "VALUES (?, ?, ?, ?, ?)";
		User invitedUser = user.getUserByEmail(email); 
		
		PreparedStatement ps = db.prepareStatement(query);
		
		try {
			ps.setString(1, invitationID);
			ps.setString(2, eventID);
			ps.setString(3, invitedUser.getUser_id());
			ps.setString(4, "Pending");
			ps.setString(5, invitedUser.getUser_role());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String acceptInvitation(String eventID, String userID) {
	    Invitation invitation;
	    String message = "Invitation Accepted!";
	    
	    String query = "UPDATE invitation\r\n"
	    		     + "SET invitation_status = ?\r\n"
	    		     + "WHERE event_id = ? AND user_id = ?";
	    
	    PreparedStatement ps = db.prepareStatement(query);
	    
	    try {
			ps.setString(1, "Accepted");
			ps.setString(2, eventID);
			ps.setString(3, userID);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    return message;
	}

	public Invitation getInvitationByEventIdAndUserId(String eventId, String userId) {
		Invitation invitation = null;
		String query = "SELECT * FROM invitation\r\n"
				     + "WHERE event_id = ? and user_id = ?";
		
		PreparedStatement ps = db.prepareStatement(query);

		try {
			ps.setString(1, eventId);
			ps.setString(2, userId);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
	            String invitationID = rs.getString("invitation_id");
	            String eventID = rs.getString("event_id");
	            String userID = rs.getString("user_id");
	            String invitationStatus = rs.getString("invitation_status");
	            String invitationRole = rs.getString("invitation_role");
				
				invitation = new Invitation(invitationID, eventID, userID, invitationStatus, invitationRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return invitation;
	}
	
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = new Vector<Event>();
		String query = "SELECT events.event_id, events.event_name, events.event_date, events.event_location, " +
		               "events.event_description, events.organizer_id, invitation.invitation_id, " +
		               "invitation.invitation_status, invitation.invitation_role, users.user_id, users.user_email " +
		               "FROM invitation " +
		               "INNER JOIN events ON invitation.event_id = events.event_id " +
		               "INNER JOIN users ON invitation.user_id = users.user_id " +
		               "WHERE users.user_email = ? AND invitation.invitation_status = 'Pending'";
		
		PreparedStatement ps = db.prepareStatement(query);
		
		try {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String eventID = rs.getString("events.event_id");
				String eventName = rs.getString("events.event_name");
				String eventDate = rs.getString("events.event_date");
				String eventLocation = rs.getString("events.event_location");
				String eventDescription = rs.getString("events.event_description");
				String eventOrganizerID = rs.getString("events.organizer_id");
				
				invitations.add(new Event(eventID, eventName, eventDate, eventLocation, eventDescription, eventOrganizerID));
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
