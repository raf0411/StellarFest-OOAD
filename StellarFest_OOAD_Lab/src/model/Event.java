package model;

import java.util.Vector;

import database.Database;

public class Event {
	private String event_id;
	private String event_name;
	private String event_date;
	private String event_location;
	private String event_description;
	private String organizer_id;
	
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
		
	}
	
	public void viewEventDetails(String eventID) {
		
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
}
