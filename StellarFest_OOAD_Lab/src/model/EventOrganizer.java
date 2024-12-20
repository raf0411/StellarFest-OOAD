package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import database.Database;

/**
 * EventOrganizer class extends the User class and represents an event organizer.
 * This class contains methods to create and manage events, view organized events,
 * view event details, and validate inputs for creating or modifying events.
 */
public class EventOrganizer extends User {
    private String events_created; // Stores events created by the organizer
    private Vector<Event> events; // Holds the list of events associated with the organizer
    private Database db = Database.getInstance(); // Database instance for querying the database

    /**
     * Constructor for creating an EventOrganizer object with user details.
     * 
     * @param user_id The user ID
     * @param user_email The user's email address
     * @param user_name The user's name
     * @param user_password The user's password
     * @param user_role The user's role
     */
    public EventOrganizer(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role); // Call the parent class constructor
    }

    /**
     * Default constructor for the EventOrganizer class.
     */
    public EventOrganizer() {
        
    }

    /**
     * This method is intended for creating an event. Currently, it is empty and needs to be implemented.
     * 
     * @param eventName The name of the event
     * @param date The date of the event
     * @param location The location of the event
     * @param description The description of the event
     * @param organizerID The ID of the event organizer
     */
    public void createEvent(String eventName, String date, String location, String description, String organizerID) {
        // Logic for creating an event will go here
    }

    /**
     * This method retrieves a list of events organized by the given user ID.
     * 
     * @param userID The ID of the user whose events are to be retrieved
     * @return A vector containing the events organized by the given user
     */
    public Vector<Event> viewOrganizedEvents(String userID) {
        String query = "SELECT * FROM events WHERE organizer_id = ?";

        // Initialize the events vector if it is null
        if (events == null) {
            events = new Vector<Event>();
        }

        try (PreparedStatement stmt = db.getInstance().prepareStatement(query)) { 
            stmt.setString(1, userID); // Set the userID as a parameter for the query
            
            try (ResultSet rs = stmt.executeQuery()) { 
                // Iterate over the result set and create Event objects for each row
                while (rs.next()) {
                    String eventID = rs.getString("event_id");
                    String eventName = rs.getString("event_name");
                    String eventDate = rs.getString("event_date");
                    String eventLocation = rs.getString("event_location");
                    String eventDescription = rs.getString("event_description");
                    
                    // Add the event to the events list
                    events.add(new Event(eventID, eventName, eventDate, eventLocation, eventDescription, userID));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors
        }

        return events; // Return the list of events
    }

    /**
     * This method retrieves the details of a specific event based on the event ID.
     * 
     * @param eventID The ID of the event whose details are to be retrieved
     * @return An Event object containing the event details
     */
    public Event viewOrganizedEventDetails(String eventID) {
        Event detailEvent = null; // Initialize the event object

        try {
            // Query to retrieve event details from the database
            String eventQuery = "SELECT * FROM events WHERE event_id = ?";
            PreparedStatement psEvent = db.prepareStatement(eventQuery);
            psEvent.setString(1, eventID); // Set the event ID as a parameter for the query
            ResultSet rsEvent = psEvent.executeQuery(); // Execute the query

            if (rsEvent.next()) { 
                // Extract the event details from the result set
                String eventName = rsEvent.getString("event_name");
                String eventDate = rsEvent.getString("event_date");
                String eventLocation = rsEvent.getString("event_location");
                String eventDescription = rsEvent.getString("event_description");
                String organizerId = rsEvent.getString("organizer_id");

                // Create an Event object with the retrieved data
                detailEvent = new Event(eventID, eventName, eventDate, eventLocation, eventDescription, organizerId);
        
                // Retrieve associated vendors and guests for the event
                Vector<Vendor> vendors = Vendor.getVendorsByTransactionID(eventID);
                Vector<Guest> guests = Guest.getGuestsByTransactionID(eventID);
                
                // Set the vendors and guests for the event
                detailEvent.setVendors(vendors);
                detailEvent.setGuests(guests);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors
        }

        return detailEvent; // Return the event with the details
    }

    /**
     * This method retrieves the list of guests for an event. Currently, it is empty and needs to be implemented.
     */
    public void getGuests() {
        
    }

    /**
     * This method retrieves the list of vendors for an event. Currently, it is empty and needs to be implemented.
     */
    public void getVendors() {
        
    }

    /**
     * This method retrieves guests by their transaction ID, but it currently returns null.
     * The logic to fetch guests should be implemented.
     * 
     * @param eventID The event ID for which guests are being fetched
     * @return A list of guests for the event (currently returns null)
     */
    public static Vector<Guest> getGuestsByTransactionID(String eventID) {
        return null;
    }

    /**
     * This method retrieves vendors by their transaction ID, but it currently returns null.
     * The logic to fetch vendors should be implemented.
     * 
     * @param eventID The event ID for which vendors are being fetched
     * @return A list of vendors for the event (currently returns null)
     */
    public static Vector<Vendor> getVendorsByTransactionID(String eventID) {
        return null;
    }

    /**
     * This method validates the input data when creating an event. Currently, it is empty and needs to be implemented.
     * 
     * @param eventName The name of the event
     * @param date The date of the event
     * @param location The location of the event
     * @param description The description of the event
     */
    public void checkCreateEventInput(String eventName, String date, String location, String description) {
        // Logic for input validation will go here
    }

    /**
     * This method validates the input data when adding a vendor to an event. Currently, it is empty and needs to be implemented.
     * 
     * @param vendorID The ID of the vendor to be added
     */
    public void checkAddVendorInput(String vendorID) {
        
    }

    /**
     * This method validates the input data when adding a guest to an event. Currently, it is empty and needs to be implemented.
     * 
     * @param vendorID The ID of the vendor to be added as a guest
     */
    public void checkAddGuestInput(String vendorID) {
        
    }

    /**
     * This method allows editing the name of an event. Currently, it is empty and needs to be implemented.
     * 
     * @param eventID The ID of the event to be edited
     * @param eventName The new name of the event
     */
    public void editEventName(String eventID, String eventName) {
        
    }

    /**
     * Gets the list of events created by the event organizer.
     * 
     * @return A vector containing the list of events
     */
    public Vector<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of events for the event organizer.
     * 
     * @param events A vector containing the list of events to set
     */
    public void setEvents(Vector<Event> events) {
        this.events = events;
    }
}
