package controller;

import java.util.Vector;

import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.Vendor;

public class EventOrganizerController {
	private EventOrganizer eventOrganizer = new EventOrganizer();
	private Event event = new Event();
	private Guest guest = new Guest();
	private Vendor vendor = new Vendor();
	
	public Vector<Event> viewOrganizedEvents(String userID) {
		Vector<Event> events = new Vector<Event>();
		events = event.getAllEventOrganizerEvents(userID);
	
		return events;
	}
	
	public Event viewOrganizedEventDetails(String eventID) {
		Event detailEvent = eventOrganizer.viewOrganizedEventDetails(eventID);
	
		return detailEvent;
	}
	
	public void getGuests() {
		
	}
	
	public Vector<Vendor> getVendors() {
		Vector<Vendor> vendors = vendor.getVendors();
		
		return vendors;
	}
	
//	public void getGuestsByTransactionID(String eventID) {
//		
//	}
//	
//	public void getVendorsByTransactionID(String eventID) {
//		
//	}
	
	public void checkCreateEventInput(String eventName, String date, String location, String description) {
		
	}
	
	public void checkAddVendorInput(String vendorID) {
		
	}
	
	public void checkAddGuestInput(String vendorID) {
		
	}
	
	public Boolean checkEditEventName(String eventName, String eventNameBefore) {
		if(eventName.isEmpty()) {
			return false;
		} else if(eventName.equals(eventNameBefore)) {
			return false;
		}
		
		return true;
	}
	
	public String editEventName(String eventID, String eventName) {
		String message = event.editEventName(eventID, eventName);
		
		return message;
	}
}
