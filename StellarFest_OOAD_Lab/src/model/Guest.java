package model;

public class Guest extends User{
	private String accepted_invitations;

	public Guest(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		// TODO Auto-generated constructor stub
	}

	public Guest() {
		// TODO Auto-generated constructor stub
	}

	public void acceptInvitation(String eventID) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
}
