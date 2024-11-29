package controller;

import java.util.Vector;

import model.Event;
import model.Invitation;

public class VendorController {
	Invitation invitation = new Invitation();
	Event event = new Event();
	
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = invitation.getInvitations(email);;
		
		return invitations;
	}
	
	public String acceptInvitation(String eventID, String userID, String invitationRole) {
		return invitation.acceptInvitation(eventID, userID, invitationRole);
	}
	
	public Vector<Event> viewAcceptedEvents(String email) {
		Vector<Event> events = event.viewAcceptedEvents(email);
		return events;
	}
	
	public void manageVendor(String description, String product) {
		
	}
	
	public void checkManageVendorInput(String description, String product) {
		
	}
}
