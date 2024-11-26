package controller;

import java.util.Vector;

import model.Event;
import model.EventOrganizer;

public class EventOrganizerController {
	private EventOrganizer eventOrganizer = new EventOrganizer();
	private Vector<Event> events;
	
	public void createEvent(String eventName, String date, String location, String description, String organizerID) {
		
	}
	
	public Vector<Event> viewOrganizedEvents(String userID) {
	    if (events == null) {
	        events = new Vector<>(); // Fallback to an empty vector if null
	    }
	    
		events = eventOrganizer.viewOrganizedEvents(userID);
	
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
}
