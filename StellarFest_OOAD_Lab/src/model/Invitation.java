package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Database;
import util.RandomIDGenerator;

/**
 * The Invitation class represents an invitation to an event for a user.
 * It allows the creation, acceptance, and retrieval of invitations to events.
 */
public class Invitation {
    private String invitation_id; // ID of the invitation
    private String event_id; // ID of the event being invited to
    private String user_id; // ID of the user receiving the invitation
    private String invitation_status; // Status of the invitation (e.g., Pending, Accepted)
    private String invitation_role; // Role of the user for the event (e.g., Guest, Organizer)
    
    private Database db = Database.getInstance(); // Database instance for executing queries
    private User user = new User(); // User object to interact with user data

    /**
     * Constructor for creating an Invitation object with all necessary details.
     * 
     * @param invitation_id The unique ID of the invitation
     * @param event_id The ID of the event being invited to
     * @param user_id The ID of the user receiving the invitation
     * @param invitation_status The current status of the invitation (Pending, Accepted, etc.)
     * @param invitation_role The role of the invited user in the event (Guest, Organizer, etc.)
     */
    public Invitation(String invitation_id, String event_id, String user_id, String invitation_status,
            String invitation_role) {
        this.invitation_id = invitation_id;
        this.event_id = event_id;
        this.user_id = user_id;
        this.invitation_status = invitation_status;
        this.invitation_role = invitation_role;
    }

    /**
     * Default constructor for the Invitation class.
     */
    public Invitation() {
        
    }

    /**
     * This method sends an invitation to a user for a specific event.
     * It generates a unique invitation ID and inserts the invitation into the database.
     * 
     * @param email The email of the user being invited
     * @param eventID The ID of the event to send the invitation for
     */
    public void sendInvitation(String email, String eventID) {
        String invitationID = RandomIDGenerator.generateUniqueID(); // Generate unique invitation ID
        String query = "INSERT INTO invitation\r\n"
                     + "VALUES (?, ?, ?, ?, ?)";
        User invitedUser = user.getUserByEmail(email); // Get the user details by email
        
        PreparedStatement ps = db.prepareStatement(query); // Prepare the SQL query
        
        try {
            // Set the parameters for the query
            ps.setString(1, invitationID);
            ps.setString(2, eventID);
            ps.setString(3, invitedUser.getUser_id());
            ps.setString(4, "Pending"); // Set the invitation status as 'Pending'
            ps.setString(5, invitedUser.getUser_role()); // Set the user role for the invitation
            
            ps.execute(); // Execute the insert query
        } catch (SQLException e) {
            e.printStackTrace(); // Print any SQL errors
        }
    }

    /**
     * This method allows a user to accept an invitation to an event.
     * The invitation status is updated to 'Accepted' in the database.
     * 
     * @param eventID The ID of the event being accepted
     * @param userID The ID of the user accepting the invitation
     * @return A message indicating the success of the operation
     */
    public String acceptInvitation(String eventID, String userID) {
        Invitation invitation;
        String message = "Invitation Accepted!";
        
        String query = "UPDATE invitation\r\n"
                     + "SET invitation_status = ?\r\n"
                     + "WHERE event_id = ? AND user_id = ?";
        
        PreparedStatement ps = db.prepareStatement(query); // Prepare the SQL query
        
        try {
            // Set the parameters for the query
            ps.setString(1, "Accepted"); // Set the status to 'Accepted'
            ps.setString(2, eventID);
            ps.setString(3, userID);
            
            ps.execute(); // Execute the update query
        } catch (SQLException e) {
            e.printStackTrace(); // Print any SQL errors
        }
        
        return message; // Return the success message
    }

    /**
     * This method retrieves an invitation from the database based on the event ID and user ID.
     * 
     * @param eventId The ID of the event
     * @param userId The ID of the user
     * @return The invitation corresponding to the event and user, or null if not found
     */
    public Invitation getInvitationByEventIdAndUserId(String eventId, String userId) {
        Invitation invitation = null;
        String query = "SELECT * FROM invitation\r\n"
                     + "WHERE event_id = ? and user_id = ?";
        
        PreparedStatement ps = db.prepareStatement(query); // Prepare the SQL query

        try {
            // Set the parameters for the query
            ps.setString(1, eventId);
            ps.setString(2, userId);
            
            ResultSet rs = ps.executeQuery(); // Execute the query
            
            // Iterate over the result set and create an Invitation object
            while(rs.next()) {
                String invitationID = rs.getString("invitation_id");
                String eventID = rs.getString("event_id");
                String userID = rs.getString("user_id");
                String invitationStatus = rs.getString("invitation_status");
                String invitationRole = rs.getString("invitation_role");
                
                invitation = new Invitation(invitationID, eventID, userID, invitationStatus, invitationRole); // Create the invitation object
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print any SQL errors
        }
        
        return invitation; // Return the invitation object, or null if not found
    }

    /**
     * This method retrieves all the invitations for a user (filtered by Pending status).
     * It returns a list of events the user has been invited to and the status of the invitations.
     * 
     * @param email The email of the user
     * @return A list of events with pending invitations for the user
     */
    public Vector<Event> getInvitations(String email) {
        Vector<Event> invitations = new Vector<Event>(); // Vector to store events with pending invitations
        String query = "SELECT events.event_id, events.event_name, events.event_date, events.event_location, " +
                       "events.event_description, events.organizer_id, invitation.invitation_id, " +
                       "invitation.invitation_status, invitation.invitation_role, users.user_id, users.user_email " +
                       "FROM invitation " +
                       "INNER JOIN events ON invitation.event_id = events.event_id " +
                       "INNER JOIN users ON invitation.user_id = users.user_id " +
                       "WHERE users.user_email = ? AND invitation.invitation_status = 'Pending'";
        
        PreparedStatement ps = db.prepareStatement(query); // Prepare the SQL query
        
        try {
            ps.setString(1, email); // Set the user's email as a parameter
            ResultSet rs = ps.executeQuery(); // Execute the query
            
            // Iterate over the result set and create Event objects for each invitation
            while(rs.next()) {
                String eventID = rs.getString("events.event_id");
                String eventName = rs.getString("events.event_name");
                String eventDate = rs.getString("events.event_date");
                String eventLocation = rs.getString("events.event_location");
                String eventDescription = rs.getString("events.event_description");
                String eventOrganizerID = rs.getString("events.organizer_id");
                
                invitations.add(new Event(eventID, eventName, eventDate, eventLocation, eventDescription, eventOrganizerID)); // Add the event to the list
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any SQL errors
        }
        
        return invitations; // Return the list of events
    }

    // Getters and setters for the Invitation class attributes
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
