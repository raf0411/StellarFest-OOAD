package controller;

import java.util.Vector;

import model.Event;
import model.Invitation;
import model.Vendor;

public class VendorController {
	Invitation invitation = new Invitation();
	Event event = new Event();
	Vendor vendor = new Vendor();
	
	public String acceptInvitation(String eventID, String userID) {
		return invitation.acceptInvitation(eventID, userID);
	}
	
	public Vector<Event> viewAcceptedEvents(String email) {
		Vector<Event> events = event.viewAcceptedEvents(email);
		return events;
	}
	
	public String manageVendor(String description, String product) {
		String message = vendor.manageVendor(description, product);
		
		return message;
	}
	
	public Boolean checkManageVendorInput(String description, String product) {
		if(description.isEmpty() || product.isEmpty()) {
			return false;
		} else if(description.length() > 200) {
			return false;
		}

		return true;
	}
	
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = invitation.getInvitations(email);
		
		return invitations;
	}
}
