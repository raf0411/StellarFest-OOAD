package controller;

import java.util.Vector;

import model.Event;
import model.Invitation;

public class VendorController {
	Invitation invitation = new Invitation();
	
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = invitation.getInvitations(email);;
		
		return invitations;
	}
	
	public void acceptInvitation(String eventID) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public void manageVendor(String description, String product) {
		
	}
	
	public void checkManageVendorInput(String description, String product) {
		
	}
}
