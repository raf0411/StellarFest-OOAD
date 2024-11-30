package controller;

import java.util.Vector;

import model.Event;
import model.Invitation;
import model.Vendor;

public class VendorController {
	Invitation invitation = new Invitation();
	Event event = new Event();
	Vendor vendor = new Vendor();
	
	public String acceptInvitation(String eventID, String userID, String invitationRole) {
		return invitation.acceptInvitation(eventID, userID, invitationRole);
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
}
