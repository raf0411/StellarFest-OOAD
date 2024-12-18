package controller;

import java.util.Vector;

import model.Event;
import model.Invitation;
import model.Vendor;

/**
 * The VendorController class handles operations related to vendors,
 * such as managing invitations, viewing accepted events, and managing vendor details.
 * It acts as the Controller in the MVC architecture for vendor-related features.
 */
public class VendorController {

	// Model objects for handling data operations
	Invitation invitation = new Invitation();
	Event event = new Event();
	Vendor vendor = new Vendor();

	/**
	 * Accepts an invitation to participate in an event.
	 * 
	 * @param eventID The ID of the event for which the invitation is accepted.
	 * @param userID  The ID of the vendor accepting the invitation.
	 * @return A message indicating the success or failure of the operation.
	 */
	public String acceptInvitation(String eventID, String userID) {
		return invitation.acceptInvitation(eventID, userID);
	}
	
	/**
	 * Retrieves all events the vendor has accepted invitations for.
	 * 
	 * @param email The email of the vendor.
	 * @return A Vector containing Event objects representing the accepted events.
	 */
	public Vector<Event> viewAcceptedEvents(String email) {
		Vector<Event> events = event.viewAcceptedEvents(email);
		return events;
	}
	
	/**
	 * Manages the vendor's details, such as product and description.
	 * 
	 * @param description A description of the vendor's offerings.
	 * @param product     The product or service provided by the vendor.
	 * @return A message indicating the result of the management operation.
	 */
	public String manageVendor(String description, String product) {
		String message = vendor.manageVendor(description, product);
		return message;
	}
	
	/**
	 * Validates the input for managing vendor details.
	 * 
	 * @param description A description of the vendor's offerings.
	 * @param product     The product or service provided by the vendor.
	 * @return True if the input is valid, otherwise false.
	 */
	public Boolean checkManageVendorInput(String description, String product) {
		if (description.isEmpty() || product.isEmpty()) {
			return false; // Input fields cannot be empty
		} else if (description.length() > 200) {
			return false; // Description must not exceed 200 characters
		}
		return true;
	}
	
	/**
	 * Retrieves all event invitations for a specific vendor.
	 * 
	 * @param email The email of the vendor.
	 * @return A Vector containing Event objects representing the invitations.
	 */
	public Vector<Event> getInvitations(String email) {
		Vector<Event> invitations = invitation.getInvitations(email);
		return invitations;
	}
}
