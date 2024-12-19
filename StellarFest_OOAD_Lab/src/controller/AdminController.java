package controller;

import java.util.Vector;

import model.Event;
import model.Guest;
import model.User;
import model.Vendor;

/**
 * The AdminController class handles administrative functionalities
 * for managing events, users, guests, and vendors in the Event Management App.
 * It serves as the Controller in the MVC architecture.
 */
public class AdminController {

	// Model objects to handle data operations
	private Event event = new Event(); // Event model object for event-related operations
	private Guest guest = new Guest(); // Guest model object for guest-related operations
	private Vendor vendor = new Vendor(); // Vendor model object for vendor-related operations
	private User user = new User(); // User model object for user-related operations
	Vector<Event> events; // A collection to store all events

	/**
	 * Retrieves a list of all events in the system.
	 * 
	 * @return A Vector containing all Event objects.
	 */
	public Vector<Event> viewAllEvents() {
		getAllEvents();
		return events;
	}
	
	/**
	 * Retrieves the details of a specific event, including its associated guests and vendors.
	 * 
	 * @param eventID The ID of the event to fetch details for.
	 */
	public void viewEventDetails(String eventID) {
		getGuestsByTransactionID(eventID);
		getVendorsByTransactionID(eventID);
	}
	
	/**
	 * Deletes an event by its ID.
	 * 
	 * @param eventID The ID of the event to delete.
	 * @return A message indicating success or an error if the event ID is invalid.
	 */
	public String deleteEvent(String eventID) {
		if(eventID == null || eventID.isEmpty()) {
			return "Select an event!";
		} else {
			return event.deleteEvent(eventID);
		}
	}

	/**
	 * Deletes a user by their ID.
	 * 
	 * @param userID The ID of the user to delete.
	 * @return A message indicating success or an error if the user ID is invalid.
	 */
	public String deleteUser(String userID) {	
		
		
		if(userID == null || userID.isEmpty()) {
			return "Select a user!";
		} else if(user.getUserByID(userID).getUser_role().equals("Admin")) {
			return "Admin cannot be deleted!"; // Ensuring admin won't be deleted
		} else {
			return user.deleteUser(userID);
		}
	}
	
	/**
	 * Retrieves a list of all users in the system.
	 * 
	 * @return A Vector containing all User objects.
	 */
	public Vector<User> getAllUsers() {
		Vector<User> users = user.getAllUsers();
		return users;
	}
	
	/**
	 * Fetches all events from the Event model and stores them in the events field.
	 */
	public void getAllEvents() {
		events = event.getAllEvents();
	}
	
	/**
	 * Retrieves all guests associated with a specific event by its ID.
	 * 
	 * @param eventID The ID of the event to fetch guests for.
	 * @return A Vector containing all Guest objects associated with the event, or null if the event does not exist.
	 */
	public Vector<Guest> getGuestsByTransactionID(String eventID) {
		if(event.getEventByEventId(eventID) != null) {
			return guest.getGuestsByTransactionID(eventID);
		}
		
		return null;
	}
	
	/**
	 * Retrieves all vendors associated with a specific event by its ID.
	 * 
	 * @param eventID The ID of the event to fetch vendors for.
	 * @return A Vector containing all Vendor objects associated with the event, or null if the event does not exist.
	 */
	public Vector<Vendor> getVendorsByTransactionID(String eventID) {
		if(event.getEventByEventId(eventID) != null) {
			return vendor.getVendorsByTransactionID(eventID);
		}
		
		return null;
	}
}
