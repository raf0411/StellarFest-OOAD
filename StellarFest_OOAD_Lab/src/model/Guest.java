package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Database;

public class Guest extends User{
	private Vector<Event> accepted_invitations;
	private static Database db = Database.getInstance();

	public Guest(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
	}

	public Guest() {
		
	}

	public void acceptInvitation(String eventID) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public static Vector<Guest> getGuestsByTransactionID(String eventID) {
        Vector<Guest> guests = new Vector<>();

        try {
            String query = "SELECT u.user_id, u.user_email, u.user_name, u.user_role " +
                           "FROM attendees ea " +
                           "JOIN users u ON ea.attendee_id = u.user_id " +
                           "WHERE ea.event_id = ? AND u.user_role = 'guest'";
            
            PreparedStatement ps = db.prepareStatement(query);
            ps.setString(1, eventID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("user_id");
                String email = rs.getString("user_email");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String role = rs.getString("user_role");

                guests.add(new Guest(userId, email, name, password, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guests;
    }
	
	public Vector<Guest> getGuests() {
		Vector<Guest> guests = new Vector<Guest>();
		
		String query = "SELECT * FROM users WHERE user_role = ?";
		PreparedStatement ps = db.prepareStatement(query);
        
		try {
        	ps.setString(1, "Guest");
			
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("user_id");
                String email = rs.getString("user_email");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String role = rs.getString("user_role");

                guests.add(new Guest(userId, email, name, password, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return guests;
	}
}
