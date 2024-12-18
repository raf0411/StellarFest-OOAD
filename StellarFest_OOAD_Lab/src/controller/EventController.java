package controller;

import java.time.LocalDate;

import model.Event;

/**
 * The EventController class handles event-related operations
 * such as creating events and validating event input.
 * It serves as the Controller in the MVC architecture.
 */
public class EventController {

	// Model object to handle event-related operations
	private Event event = new Event();
	
	/**
	 * Creates a new event after validating the input.
	 * 
	 * @param eventName   The name of the event.
	 * @param date        The date of the event in the format "YYYY-MM-DD".
	 * @param location    The location of the event.
	 * @param description The description of the event.
	 * @param organizerID The ID of the organizer creating the event.
	 * @return A message indicating the success of the operation or a specific validation error.
	 */
	public String createEvent(String eventName, String date, String location, String description, String organizerID) {
		// Validate the input for creating an event
		String message = checkCreateEventInput(eventName, date, location, description);
		
		// If validation fails, return the error message
		if (!message.equals("Event created successfully!")) {
		 	return message;
		} else {
			// Call the createEvent method from the Event model to save the event
			event.createEvent(eventName, date, location, description, organizerID);
		}
		
		// Return success message
		return message;
	}
	
	/**
	 * Validates the input for creating an event.
	 * 
	 * @param eventName   The name of the event.
	 * @param date        The date of the event in the format "YYYY-MM-DD".
	 * @param location    The location of the event.
	 * @param description The description of the event.
	 * @return A validation message indicating success or the specific input error.
	 */
	public String checkCreateEventInput(String eventName, String date, String location, String description) {
		// Parse the provided date and get the current date
		LocalDate selectedDate = LocalDate.parse(date);
		LocalDate currentDate = LocalDate.now();
		String message = "";
		
		// Validate event name
		if (eventName.isEmpty()) {
			message = "Event Name is empty!";
			return message;
		} 
		// Validate event date
		else if (date.isEmpty() || selectedDate.equals(null)) {
			message = "Event Date is empty!";
			return message;
		} else if (selectedDate.isBefore(currentDate)) {
			message = "Date must be at the future!";
			return message;
		} 
		// Validate event location
		else if (location.isEmpty()) {
			message = "Event Location is empty!";
			return message;
		} else if (location.length() < 5) {
			message = "Event Location must be at least 5 characters!";
			return message;
		} 
		// Validate event description
		else if (description.isEmpty()) {
			message = "Event Description is empty!";
			return message;
		} else if (description.length() > 200) {
			message = "Event Description must be maximum of 200 characters!";
			return message;
		} 
		// If all validations pass, return success message
		else {
			message = "Event created successfully!";
			return message;
		}
	}
	
	/**
	 * Retrieves the details of a specific event by its ID.
	 * 
	 * @param eventID The ID of the event to view details for.
	 */
	public void viewEventDetails(String eventID) {
		// Implementation for viewing event details can be added here
	}
}
