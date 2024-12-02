package controller;

import java.util.Vector;

import model.Event;
import model.User;

public class AdminController {
	private Event event = new Event();
	Vector<Event> events;
	
	public Vector<Event> viewAllEvents() {
		getAllEvents();
		return events;
	}
	
	public void viewEventDetails(String eventID) {
		getGuestsByTransactionID(eventID);
		getVendorsByTransactionID(eventID);
	}
	
	public void deleteEvent(String eventID) {
		
	}

	public void deleteUser(String userID) {
		
	}
	
	public Vector<User> getAllUsers() {
		Vector<User> users = new Vector<>();
		return users;
	}
	
	public void getAllEvents() {
		events = event.getAllEvents();
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
}
