package controller;

import java.util.Vector;

import model.Event;
import model.Guest;
import model.User;
import model.Vendor;

public class AdminController {
	private Event event = new Event();
	private Guest guest = new Guest();
	private Vendor vendor = new Vendor();
	private User user = new User();
	Vector<Event> events;
	
	public Vector<Event> viewAllEvents() {
		getAllEvents();
		return events;
	}
	
	public void viewEventDetails(String eventID) {
		getGuestsByTransactionID(eventID);
		getVendorsByTransactionID(eventID);
	}
	
	public String deleteEvent(String eventID) {
		if(eventID == null || eventID.isEmpty()) {
			return "Event is not filled!";
		} else {
			event.deleteEvent(eventID);
			return "Event successfully deleted!";
		}
	}

	public String deleteUser(String userID) {
		if(userID == null || userID.isEmpty()) {
			return "User is not filled!";
		} else {
			user.deleteUser(userID);
			return "User successfully deleted!";
		}
	}
	
	public Vector<User> getAllUsers() {
		Vector<User> users = user.getAllUsers();
		return users;
	}
	
	public void getAllEvents() {
		events = event.getAllEvents();
	}
	
	public Vector<Guest> getGuestsByTransactionID(String eventID) {
		if(event.getEventByEventId(eventID) != null) {
			return guest.getGuestsByTransactionID(eventID);
		}
		
		return null;
	}
	
	public Vector<Vendor> getVendorsByTransactionID(String eventID) {
		if(event.getEventByEventId(eventID) != null) {
			return vendor.getVendorsByTransactionID(eventID);
		}
		
		return null;
	}
}
