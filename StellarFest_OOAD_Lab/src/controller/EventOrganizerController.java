package controller;

import java.util.Vector;

import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.Invitation;
import model.User;
import model.Vendor;

/**
 * The EventOrganizerController class handles functionalities for event organizers,
 * such as managing organized events, inviting guests and vendors, and editing event details.
 * It serves as the Controller in the MVC architecture.
 */
public class EventOrganizerController {

	// Model objects for handling data operations
	private EventOrganizer eventOrganizer = new EventOrganizer();
	private Event event = new Event();
	private Guest guest = new Guest();
	private Vendor vendor = new Vendor();
	private User user = new User();
	private Invitation invitation = new Invitation();
	
	/**
	 * Retrieves all events organized by a specific user.
	 * 
	 * @param userID The ID of the user (event organizer).
	 * @return A Vector containing all events organized by the user.
	 */
	public Vector<Event> viewOrganizedEvents(String userID) {
		Vector<Event> events = new Vector<Event>();
		events = event.getAllEventOrganizerEvents(userID);
		return events;
	}
	
	/**
	 * Retrieves the details of a specific event organized by the user.
	 * 
	 * @param eventID The ID of the event.
	 * @return The Event object containing detailed information about the event.
	 */
	public Event viewOrganizedEventDetails(String eventID) {
		Event detailEvent = eventOrganizer.viewOrganizedEventDetails(eventID);
		return detailEvent;
	}
	
	/**
	 * Retrieves all guests in the system.
	 * 
	 * @return A Vector containing all Guest objects.
	 */
	public Vector<Guest> getGuests() {
		Vector<Guest> guests = guest.getGuests();
		return guests;
	}
	
	/**
	 * Retrieves all vendors in the system.
	 * 
	 * @return A Vector containing all Vendor objects.
	 */
	public Vector<Vendor> getVendors() {
		Vector<Vendor> vendors = vendor.getVendors();
		return vendors;
	}
	
	/**
	 * Retrieves all guests associated with a specific event by its ID.
	 * 
	 * @param eventID The ID of the event.
	 */
	public void getGuestsByTransactionID(String eventID) {
		// Implementation for retrieving guests by transaction ID can be added here
	}
	
	/**
	 * Retrieves all vendors associated with a specific event by its ID.
	 * 
	 * @param eventID The ID of the event.
	 */
	public void getVendorsByTransactionID(String eventID) {
		// Implementation for retrieving vendors by transaction ID can be added here
	}
	
	/**
	 * Validates the input for adding a vendor to an event.
	 * 
	 * @param email   The email of the vendor.
	 * @param eventID The ID of the event.
	 * @return A message indicating success or the specific validation error.
	 */
	public String checkAddVendorInput(String email, String eventID) {
		if (event.getEventByEventId(eventID) == null) {
			return "Event is not selected/exists!";
		} else if (invitation.getInvitationByEventIdAndUserId(eventID, vendor.getUserByEmail(email).getUser_id()) != null) {
			return "Vendor already invited!";
		}
		return "Send Invitation Successful!";
	}
	
	/**
	 * Validates the input for adding a guest to an event.
	 * 
	 * @param email   The email of the guest.
	 * @param eventID The ID of the event.
	 * @return A message indicating success or the specific validation error.
	 */
	public String checkAddGuestInput(String email, String eventID) {
		if (event.getEventByEventId(eventID) == null) {
			return "Event is not selected/exists!";
		} else if (invitation.getInvitationByEventIdAndUserId(eventID, guest.getUserByEmail(email).getUser_id()) != null) {
			return "Guest already invited!";
		}
		return "Send Invitation Successful!";
	}
	
	/**
	 * Checks whether the new event name is valid and different from the previous name.
	 * 
	 * @param eventName       The new name of the event.
	 * @param eventNameBefore The current name of the event.
	 * @return True if the new name is valid, otherwise false.
	 */
	public Boolean checkEditEventName(String eventName, String eventNameBefore) {
		if (eventName.isEmpty()) {
			return false;
		} else if (eventName.equals(eventNameBefore)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Updates the name of an event.
	 * 
	 * @param eventID   The ID of the event to update.
	 * @param eventName The new name of the event.
	 * @return A message indicating success or failure of the operation.
	 */
	public String editEventName(String eventID, String eventName) {
		String message = event.editEventName(eventID, eventName);
		return message;
	}
	
	/**
	 * Sends an invitation to a user (guest or vendor) for a specific event.
	 * 
	 * @param email   The email of the user to invite.
	 * @param eventID The ID of the event.
	 * @return A message indicating success or the specific validation error.
	 */
	public String sendInvitation(String email, String eventID) {
		String role = "";
		String message = "User does not exists/selected!";
		
		// Get the user's role by their email
		if (user.getUserByEmail(email) != null) {
			role = user.getUserByEmail(email).getUser_role();
		}
		
		// Handle invitations for vendors
		if (role.equals("Vendor")) {
			message = checkAddVendorInput(email, eventID);
			if (message.equals("Send Invitation Successful!")) {
				invitation.sendInvitation(email, eventID);
				return message;
			}
		} 
		// Handle invitations for guests
		else if (role.equals("Guest")) {
			message = checkAddGuestInput(email, eventID);
			if (message.equals("Send Invitation Successful!")) {
				invitation.sendInvitation(email, eventID);
				return message;
			}
		}
		
		return message;
	}
}
