package controller;

import java.util.Vector;

import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.Invitation;
import model.User;
import model.Vendor;

public class EventOrganizerController {
	private EventOrganizer eventOrganizer = new EventOrganizer();
	private Event event = new Event();
	private Guest guest = new Guest();
	private Vendor vendor = new Vendor();
	private User user = new User();
	private Invitation invitation = new Invitation();
	
	public Vector<Event> viewOrganizedEvents(String userID) {
		Vector<Event> events = new Vector<Event>();
		events = event.getAllEventOrganizerEvents(userID);
	
		return events;
	}
	
	public Event viewOrganizedEventDetails(String eventID) {
		Event detailEvent = eventOrganizer.viewOrganizedEventDetails(eventID);
	
		return detailEvent;
	}
	
	public Vector<Guest> getGuests() {
		Vector<Guest> guests = guest.getGuests();
		
		return guests;
	}
	
	public Vector<Vendor> getVendors() {
		Vector<Vendor> vendors = vendor.getVendors();
		
		return vendors;
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
	
	public String checkAddVendorInput(String email, String eventID) {
		if(event.getEventByEventId(eventID) == null) {
			return "Event is not selected/exists!";
		} else if(invitation.getInvitationByEventIdAndUserId(eventID, vendor.getUserByEmail(email).getUser_id()) != null) {
			return "Vendor already invited!";
		}
		
		return "Send Invitation Successful!";
	}
	
	public String checkAddGuestInput(String email, String eventID) {
		if(event.getEventByEventId(eventID) == null) {
			return "Event is not selected/exists!";
		} else if(invitation.getInvitationByEventIdAndUserId(eventID, guest.getUserByEmail(email).getUser_id()) != null) {
			return "Guest already invited!";
		}
		
		return "Send Invitation Successful!";
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
	
	public String sendInvitation(String email, String eventID) {
		String role = "";
		String message = "User does not exists/selected!";
		
		if(user.getUserByEmail(email) != null) {
			role = user.getUserByEmail(email).getUser_role();
		}
		
		if(role.equals("Vendor")) {
			message = checkAddVendorInput(email, eventID);
			
			if(message.equals("Send Invitation Successful!")) {
				invitation.sendInvitation(email, eventID);
				return message;
			}
		} else if(role.equals("Guest")) {
			message = checkAddGuestInput(email, eventID);
			
			if(message.equals("Send Invitation Successful!")) {
				invitation.sendInvitation(email, eventID);
				return message;
			}
		}
		
		return message;
	}
}
