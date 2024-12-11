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
