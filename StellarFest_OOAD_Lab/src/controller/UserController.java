package controller;

import model.User;

public class UserController {
	private User user = new User();
	
	public void register(String email, String name, String password, String role) {
		user.register(email, name, password, role);
	}
	
	public Boolean login(String email, String password) {
		// Validate Login
		if(!email.isEmpty() && !password.isEmpty() && user.login(email, password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void changeProfile(String email, String name, String oldPassword, String newPassword) {
		
	}
	
	public User getUserByEmail(String email) {
		if(user.getUserByEmail(email) != null) {
			user = user.getUserByEmail(email);
			return user;
		}
		
		return null;
	}
	
	public User getUserByUsername(String name) {
		if(user.getUserByUsername(name) != null) {
			user = user.getUserByUsername(name);
			return user;
		}
		
		return null;
	}
	
	public Boolean checkRegisterInput(String email, String name, String password, String role) {
		// Validate Registration
		if(email.isEmpty() || getUserByEmail(email) != null) {
			return false;
		} else if(name.isEmpty() || getUserByUsername(name) != null) {
			return false;
		} else if(password.isEmpty() || password.length() < 5) {
			return false;
		} else if(role.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		
	}
}
