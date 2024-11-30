package controller;

import java.util.Vector;

import model.Event;
import model.Invitation;

public class InvitationController {
	Invitation invitation = new Invitation();
	Event event = new Event();
	
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = invitation.getInvitations(email);;
		
		return invitations;
	}
	
	
	public void sendInvitation(String email) {
		
	}
	
	public void acceptInvitation(String eventID) {
		
	}
}
