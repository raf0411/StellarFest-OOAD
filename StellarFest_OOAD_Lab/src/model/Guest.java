package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Database;

/**
 * The Guest class represents a user with the role of a guest who can accept invitations
 * and view events they have been invited to. The class interacts with the database to
 * retrieve information about the guests and their event participation.
 */
public class Guest extends User {
    private Vector<Event> accepted_invitations; // List of events the guest has accepted invitations to
    private static Database db = Database.getInstance(); // Database instance for executing queries

    /**
     * Constructor for creating a Guest object with user details.
     * 
     * @param user_id The user ID
     * @param user_email The user's email address
     * @param user_name The user's name
     * @param user_password The user's password
     * @param user_role The user's role (should be 'guest' for this class)
     */
    public Guest(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role); // Call the parent class constructor
    }

    /**
     * Default constructor for the Guest class.
     */
    public Guest() {
        
    }

    /**
     * This method allows a guest to accept an invitation to an event.
     * Currently, it is empty and needs to be implemented.
     * 
     * @param eventID The ID of the event to be accepted
     */
    public void acceptInvitation(String eventID) {
        // Logic for accepting an invitation should go here
    }
    
    /**
     * This method allows a guest to view the list of events they have accepted invitations to.
     * Currently, it is empty and needs to be implemented.
     * 
     * @param email The email of the guest to retrieve accepted events
     */
    public void viewAcceptedEvents(String email) {
        // Logic for viewing accepted events should go here
    }

    /**
     * This method retrieves a list of guests associated with a specific event using its event ID.
     * It joins the `attendees` table with the `users` table to fetch guest information.
     * 
     * @param eventID The ID of the event whose guests are being fetched
     * @return A vector containing the list of guests for the event
     */
    public static Vector<Guest> getGuestsByTransactionID(String eventID) {
        Vector<Guest> guests = new Vector<>(); // Vector to store guest objects

        try {
            // SQL query to retrieve guest details by event ID
            String query = "SELECT u.user_id, u.user_email, u.user_name, u.user_password, u.user_role " +
		                   "FROM invitation i " +
		                   "JOIN users u ON i.user_id = u.user_id " +
		                   "WHERE i.event_id = ? AND i.invitation_role = 'Guest' AND i.invitation_status = 'Accepted'";
		            
            PreparedStatement ps = db.prepareStatement(query); // Prepare the statement
            ps.setString(1, eventID); // Set the event ID as a parameter for the query
            ResultSet rs = ps.executeQuery(); // Execute the query

            // Iterate over the result set and create Guest objects for each row
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String email = rs.getString("user_email");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String role = rs.getString("user_role");

                guests.add(new Guest(userId, email, name, password, role)); // Add the guest to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print any SQL errors
        }

        return guests; // Return the list of guests
    }

    /**
     * This method retrieves all users with the role of 'Guest' from the database.
     * 
     * @return A vector containing all guests in the system
     */
    public Vector<Guest> getGuests() {
        Vector<Guest> guests = new Vector<Guest>(); // Vector to store guest objects
        
        // SQL query to retrieve all users with the role 'Guest'
        String query = "SELECT * FROM users WHERE user_role = ?";
        PreparedStatement ps = db.prepareStatement(query); // Prepare the statement
        
        try {
            ps.setString(1, "Guest"); // Set the role parameter as 'Guest'
            ResultSet rs = ps.executeQuery(); // Execute the query

            // Iterate over the result set and create Guest objects for each row
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String email = rs.getString("user_email");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String role = rs.getString("user_role");

                guests.add(new Guest(userId, email, name, password, role)); // Add the guest to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print any SQL errors
        }

        return guests; // Return the list of guests
    }
}
