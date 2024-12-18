package model;

/**
 * The Admin class represents an Admin user in the system.
 * It extends the User class and provides specific methods for managing events, users, and invitations.
 * The Admin class provides functionalities such as viewing, deleting events, and managing users.
 */
public class Admin extends User {

    /**
     * Constructor to initialize an Admin object with user details.
     *
     * @param user_id The unique ID of the admin user.
     * @param user_email The email of the admin user.
     * @param user_name The name of the admin user.
     * @param user_password The password of the admin user.
     * @param user_role The role of the user, which in this case would be "Admin".
     */
    public Admin(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role);
    }
    
    /**
     * Default constructor for the Admin class.
     */
    public Admin() {
        // Default constructor for Admin object
    }

    /**
     * Views all events in the system.
     * This method retrieves all events and is intended to be used by the admin to manage events.
     */
    public void viewAllEvents() {
        // TODO: Implement functionality to view all events
    }

    /**
     * Views the details of a specific event by its event ID.
     *
     * @param eventID The ID of the event to view details for.
     */
    public void viewEventDetails(String eventID) {
        // TODO: Implement functionality to view event details by eventID
    }

    /**
     * Deletes an event from the system based on the event ID.
     *
     * @param eventID The ID of the event to be deleted.
     */
    public void deleteEvent(String eventID) {
        // TODO: Implement functionality to delete an event by eventID
    }

    /**
     * Deletes a user from the system based on the user ID.
     *
     * @param userID The ID of the user to be deleted.
     * @return A string message indicating the result of the deletion attempt.
     */
    public String deleteUser(String userID) {
        // TODO: Implement functionality to delete a user by userID
        return null; // Placeholder return statement
    }

    /**
     * Retrieves all events from the system.
     * This method is used to load the list of all events available in the system.
     */
    public void getAllEvents() {
        // TODO: Implement functionality to get all events
    }

    /**
     * Retrieves the list of guests by the transaction ID associated with a specific event.
     *
     * @param eventID The ID of the event for which to retrieve guest data.
     */
    public void getGuestsByTransactionID(String eventID) {
        // TODO: Implement functionality to get guests by transaction ID
    }

    /**
     * Retrieves the list of vendors by the transaction ID associated with a specific event.
     *
     * @param eventID The ID of the event for which to retrieve vendor data.
     */
    public void getVendorsByTransactionID(String eventID) {
        // TODO: Implement functionality to get vendors by transaction ID
    }
}
