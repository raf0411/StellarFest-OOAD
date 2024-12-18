package controller;

import java.util.Vector;

import model.Event;
import model.Guest;
import model.Invitation;

/**
 * The GuestController class handles operations related to guests,
 * such as managing invitations, viewing accepted events, and retrieving event invitations.
 * It acts as the Controller in the MVC architecture for guest-related features.
 */
public class GuestController {

	// Model objects for handling guest-related data operations
	private Event event = new Event();
	private Invitation invitation = new Invitation();

	/**
	 * Accepts an invitation to participate in an event.
	 * 
	 * @param eventID The ID of the event for which the invitation is accepted.
	 * @param userID  The ID of the guest accepting the invitation.
	 * @return A message indicating the success or failure of the operation.
	 */
	public String acceptInvitation(String eventID, String userID) {
		String message = invitation.acceptInvitation(eventID, userID);
		return message;
	}

	/**
	 * Retrieves all events the guest has accepted invitations for.
	 * 
	 * @param email The email of the guest.
	 * @return A Vector containing Event objects representing the accepted events.
	 */
	public Vector<Event> viewAcceptedEvents(String email) {
		Vector<Event> events = event.viewAcceptedEvents(email);
		return events;
	}

	/**
	 * Retrieves all event invitations sent to a specific guest.
	 * 
	 * @param email The email of the guest.
	 * @return A Vector containing Event objects representing the invitations.
	 */
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = invitation.getInvitations(email);
		return invitations;
	}
}
