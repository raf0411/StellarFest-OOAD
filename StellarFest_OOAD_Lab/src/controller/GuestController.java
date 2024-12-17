package controller;

import java.util.Vector;

import model.Event;
import model.Guest;
import model.Invitation;

public class GuestController {
	private Guest guest = new Guest();
	private Event event = new Event();
	private Invitation invitation = new Invitation();
	
	public String acceptInvitation(String eventID, String userID) {
		String message = invitation.acceptInvitation(eventID, userID);
		
		return message;
	}
	
	public Vector<Event> viewAcceptedEvents(String email) {
		Vector<Event> events = event.viewAcceptedEvents(email);
		return events;
	}
	
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = invitation.getInvitations(email);
		
		return invitations;
	}
}
