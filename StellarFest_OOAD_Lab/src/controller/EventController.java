package controller;

import java.time.LocalDate;

import model.Event;

public class EventController {
	private Event event = new Event();
	
	public String createEvent(String eventName, String date, String location, String description, String organizerID) {
		String message = checkCreateEventInput(eventName, date, location, description);
		
		if(!message.equals("Event created successfully!")) {
		 	return message;
		} else {
			event.createEvent(eventName, date, location, description, organizerID);
		}
		
		return message;
	}
	
	public String checkCreateEventInput(String eventName, String date, String location, String description) {
		LocalDate selectedDate = LocalDate.parse(date);
		LocalDate currentDate = LocalDate.now();
		String message = "";
		
		if(eventName.isEmpty()) {
			message = "Event Name is empty!";
			return message;
		} else if(date.isEmpty() || selectedDate.equals(null)) {
			message = "Event Date is empty!";
			return message;
		} else if(selectedDate.isBefore(currentDate)) {
			message = "Date must be at the future!";
			return message;
		} else if(location.isEmpty()) {
			message = "Event Location is empty!";
			return message;
		} else if(location.length() < 5) {
			message = "Event Location must be at least 5 characters!";
			return message;
		} else if(description.isEmpty()) {
			message = "Event Description is empty!";
			return message;
		} else if(description.length() > 200) {
			message = "Event Description must be maximum of 200 characters!";
			return message;
		} else {
			message = "Event created successfully!";
		}
		
		return message;
	}
	
	public void viewEventDetails(String eventID) {
		
	}
}
