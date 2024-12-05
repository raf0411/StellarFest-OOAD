package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Database;
import util.RandomIDGenerator;

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
	
	public Event() {
		
	}
	
	public void createEvent(String eventName, String date, String location, String description, String organizerID) {
		String eventID = RandomIDGenerator.generateUniqueID();
		String query = "INSERT INTO events\r\n" +
				   	   "VALUES (?, ?, ?, ?, ?, ?)";
		
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
	
	public Event viewEventDetails(String eventID) {
		return null;
	}
	
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
	               "WHERE users.user_email = '"+ email +"' " +
	               "AND invitation.invitation_status = 'Accepted'";
		
		db.resultSet = db.execQuery(query);
		
		try {
			while(db.resultSet.next()) {
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
	
	public Vector<Event> getAllEventOrganizerEvents(String userID){
		Vector<Event> events = new Vector<Event>();
		
		String query = "SELECT * FROM events\r\n"
					 + "WHERE organizer_id = ?";
		
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
			
			rs.close();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return events;
	}
	
	public String editEventName(String eventID, String eventName) {
		String query = "UPDATE events\r\n"
					 + "SET event_name = ?\r\n"
					 + "WHERE event_id = ?";
		
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
	
	// SETTER AND GETTER
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
