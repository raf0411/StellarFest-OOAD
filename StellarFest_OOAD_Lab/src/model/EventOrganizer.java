package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import database.Database;

public class EventOrganizer extends User{
	private String events_created;
	private Vector<Event> events;
	private Database db;
	
	public EventOrganizer(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
	}
	
	public EventOrganizer() {
		
	}

	public void createEvent(String eventName, String date, String location, String description, String organizerID) {
		
	}
	
	public Vector<Event> viewOrganizedEvents(String userID) {
	    String query = "SELECT * FROM events WHERE organizer_id = ?";

	    if (events == null) {
	        events = new Vector<Event>();
	    }

	    try (PreparedStatement stmt = db.getInstance().prepareStatement(query)) { // Ensure db.getConnection() is valid
	        stmt.setString(1, userID);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                String eventID = rs.getString("event_id");
	                String eventName = rs.getString("event_name");
	                String eventDate = rs.getString("event_date");
	                String eventLocation = rs.getString("event_location");
	                String eventDescription = rs.getString("event_description");
	                
	                events.add(new Event(eventID, eventName, eventDate, eventLocation, eventDescription, userID));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return events;
	}
	
	public void viewOrganizedEventDetails(String eventID) {
		
	}
	
	public void getGuests() {
		
	}
	
	public void getVendors() {
		
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
	
	public void checkCreateEventInput(String eventName, String date, String location, String description) {
		
	}
	
	public void checkAddVendorInput(String vendorID) {
		
	}
	
	public void checkAddGuestInput(String vendorID) {
		
	}
	
	public void editEventName(String eventID, String eventName) {
		
	}

	public Vector<Event> getEvents() {
		return events;
	}

	public void setEvents(Vector<Event> events) {
		this.events = events;
	}
}
