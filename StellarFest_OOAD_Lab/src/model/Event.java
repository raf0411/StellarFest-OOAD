package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Database;
import util.RandomIDGenerator;

/**
 * The Event class represents an event in the system.
 * It contains methods for creating, viewing, editing, and deleting events.
 * It also includes functionality to manage event guests and vendors.
 */
public class Event {
	private String event_id;
	private String event_name;
	private String event_date;
	private String event_location;
	private String event_description;
	private String organizer_id;
	private Vector<Vendor> vendors;
	private Vector<Guest> guests;
	
	private Database db = Database.getInstance();
	
	/**
	 * Constructor to initialize an Event object with details.
	 *
	 * @param event_id The unique ID of the event.
	 * @param event_name The name of the event.
	 * @param event_date The date of the event.
	 * @param event_location The location of the event.
	 * @param event_description A description of the event.
	 * @param organizer_id The ID of the organizer of the event.
	 */
	public Event(String event_id, String event_name, String event_date, String event_location, String event_description,
			String organizer_id) {
		super();
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_date = event_date;
		this.event_location = event_location;
		this.event_description = event_description;
		this.organizer_id = organizer_id;
	}
	
	/**
	 * Default constructor for the Event class.
	 */
	public Event() {
		// Default constructor
	}
	
	/**
	 * Creates a new event in the database.
	 *
	 * @param eventName The name of the event.
	 * @param date The date of the event.
	 * @param location The location of the event.
	 * @param description A description of the event.
	 * @param organizerID The ID of the event's organizer.
	 */
	public void createEvent(String eventName, String date, String location, String description, String organizerID) {
		String eventID = RandomIDGenerator.generateUniqueID();
		String query = "INSERT INTO events VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = db.prepareStatement(query);
		
		try {
			ps.setString(1, eventID);
			ps.setString(2, eventName);
			ps.setString(3, date);
			ps.setString(4, location);
			ps.setString(5, description);
			ps.setString(6, organizerID);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Views the details of an event based on the event ID.
	 *
	 * @param eventID The ID of the event to view.
	 * @return The Event object containing details of the event.
	 */
	public Event viewEventDetails(String eventID) {
		return null; // Placeholder for functionality
	}
	
	/**
	 * Retrieves the events that the user has accepted invitations for.
	 *
	 * @param email The email of the user.
	 * @return A Vector of events that the user has accepted invitations for.
	 */
	public Vector<Event> viewAcceptedEvents(String email) {
	    Vector<Event> events = new Vector<Event>();
	    
	    String query = "SELECT events.event_id, " +
	                   "events.event_name, " +
	                   "events.event_date, " +
	                   "events.event_location, " +
	                   "events.event_description, " +
	                   "users.user_id " +
	                   "FROM invitation " +
	                   "JOIN events ON invitation.event_id = events.event_id " +
	                   "JOIN users ON invitation.user_id = users.user_id " + 
	                   "WHERE users.user_email = ? " +
	                   "AND invitation.invitation_status = 'Accepted'";
	    
	    try (PreparedStatement ps = db.prepareStatement(query)) {
	    	ps.setString(1, email);
	        
	        db.resultSet = ps.executeQuery();
	        
	        while (db.resultSet.next()) {
	            String eventId = db.resultSet.getString("events.event_id");
	            String eventName = db.resultSet.getString("events.event_name");
	            String eventDate = db.resultSet.getString("events.event_date");
	            String eventLocation = db.resultSet.getString("events.event_location");
	            String eventDescription = db.resultSet.getString("events.event_description");
	            String organizerId = db.resultSet.getString("users.user_id");
	            
	            events.add(new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return events;
	}

	/**
	 * Retrieves all events in the system.
	 *
	 * @return A Vector of all events in the system.
	 */
	public Vector<Event> getAllEvents(){
		Vector<Event> events = new Vector<Event>();
		
		String query = "SELECT * FROM events";
		db.resultSet = db.execQuery(query);
		
		try {
			while(db.resultSet.next()) {
				String eventId = db.resultSet.getString("event_id");
				String eventName = db.resultSet.getString("event_name");
				String eventDate = db.resultSet.getString("event_date");
				String eventLocation = db.resultSet.getString("event_location");
				String eventDescription = db.resultSet.getString("event_description");
				String organizerId = db.resultSet.getString("organizer_id");
				
				events.add(new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return events;
	}
	
	/**
	 * Retrieves events that are organized by a specific user based on their user ID.
	 *
	 * @param userID The user ID of the event organizer.
	 * @return A Vector of events organized by the user.
	 */
	public Vector<Event> getAllEventOrganizerEvents(String userID){
		Vector<Event> events = new Vector<Event>();
		
		String query = "SELECT * FROM events WHERE organizer_id = ?";
		
		try {
			PreparedStatement ps = db.prepareStatement(query);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
	            String eventId = rs.getString("event_id");
	            String eventName = rs.getString("event_name");
	            String eventDate = rs.getString("event_date");
	            String eventLocation = rs.getString("event_location");
	            String eventDescription = rs.getString("event_description");
	            String organizerId = rs.getString("organizer_id");
				
				events.add(new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return events;
	}
	
	/**
	 * Edits the name of an event based on the event ID.
	 *
	 * @param eventID The ID of the event to edit.
	 * @param eventName The new name of the event.
	 * @return A message indicating the success of the operation.
	 */
	public String editEventName(String eventID, String eventName) {
		String query = "UPDATE events SET event_name = ? WHERE event_id = ?";
		
		PreparedStatement ps = db.prepareStatement(query);
		
		try {
			ps.setString(1, eventName);
			ps.setString(2, eventID);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String message = "Edit Successful!";
		return message;
	}
	
	/**
	 * Retrieves an event by its event ID.
	 *
	 * @param eventId The ID of the event to retrieve.
	 * @return The Event object corresponding to the event ID.
	 */
	public Event getEventByEventId(String eventId) {
		String query = "SELECT * FROM events WHERE event_id = ?";
		Event event = null;
		
		PreparedStatement ps = db.prepareStatement(query);
		
		try {
			ps.setString(1, eventId);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
	            String eventID = rs.getString("event_id");
	            String eventName = rs.getString("event_name");
	            String eventDate = rs.getString("event_date");
	            String eventLocation = rs.getString("event_location");
	            String eventDescription = rs.getString("event_description");
	            String organizerId = rs.getString("organizer_id");
				
				event = new Event(eventID, eventName, eventDate, eventLocation, eventDescription, organizerId);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return event != null ? event : null;
	}
	
	/**
	 * Deletes an event from the system.
	 *
	 * @param eventID The ID of the event to delete.
	 * @return A message indicating the success or failure of the deletion operation.
	 */
	public String deleteEvent(String eventID) {
	    String deleteInvitationsQuery = "DELETE FROM invitation WHERE event_id = ?";
	    String deleteEventQuery = "DELETE FROM events WHERE event_id = ?";
	    String message = "";
	    
	    try {
	        try (PreparedStatement ps1 = db.prepareStatement(deleteInvitationsQuery)) {
	            ps1.setString(1, eventID);
	            ps1.executeUpdate();
	        }

	        try (PreparedStatement ps2 = db.prepareStatement(deleteEventQuery)) {
	            ps2.setString(1, eventID);
	            ps2.executeUpdate();
	        }
	        
	        message = "Delete event successful!";

	    } catch (SQLException e) {
	    	message = "There was an error, delete event unsuccessful!";
	        e.printStackTrace();
	    }
	    
	    return message;
	}

	// Getters and Setters
	public String getEvent_id() {
		return event_id;
	}
	
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	
	public String getEvent_name() {
		return event_name;
	}
	
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	
	public String getEvent_date() {
		return event_date;
	}
	
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	
	public String getEvent_location() {
		return event_location;
	}
	
	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}
	
	public String getEvent_description() {
		return event_description;
	}
	
	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}
	
	public String getOrganizer_id() {
		return organizer_id;
	}
	
	public void setOrganizer_id(String organizer_id) {
		this.organizer_id = organizer_id;
	}
	
	public void setGuests(Vector<Guest> guests) {
		this.guests = guests;
	}
	
	public void setVendors(Vector<Vendor> vendors) {
		this.vendors = vendors;
	}

	public Vector<Vendor> getVendors() {
		return vendors;
	}

	public Vector<Guest> getGuests() {
		return guests;
	}
}
