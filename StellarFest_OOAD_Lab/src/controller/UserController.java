package controller;

import model.User;

public class UserController {
	private User user = new User();
	
	public String register(String email, String name, String password, String role) {
		String message = "";
		
		if(getUserByEmail(email) != null) {
			message = "User email already exists!";
			return message;
		} else if(getUserByUsername(name) != null) {
			message = "User username already exists!";
			return message;
		} else {
			message = checkRegisterInput(email, name, password, role);
		}
		
		if(!message.equals("Registration Successful!")) {
			return message;
		} else {
			user.register(email, name, password, role);
			return message;
		}
	}
	
	public String login(String email, String password) {
		String message = "";
		
		if(email.isEmpty()){
			message = "Email cannot be empty!";
			return message;
		} else if(password.isEmpty()) {
			message = "Password cannot be empty!";
			return message;
		} else if(getUserByEmail(email) == null) {
			message = "Email is not registered!";
			return message;
		} else if(!getUserByEmail(email).getUser_password().equals(password)) {
			message = "Password is incorrect!";
			return message;
		} else {
			message = "Login Successful!";
			return message;
		}
	}
	
	public String changeProfile(String userID,String email, String name, String oldPassword, String newPassword) {
		String message = checkChangeProfileInput(userID, email, name, oldPassword, newPassword);
		return message;
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
	
	public String checkRegisterInput(String email, String name, String password, String role) {
		String message = "";
		
		if(email.isEmpty()) {
			message = "Email cannot be empty!";
			return message;
		} else if(name.isEmpty()) {
			message = "Username cannot be empty!";
			return message;
		} else if(password.isEmpty()) {
			message = "Password cannot be empty!";
			return message;
		} else if(password.length() < 5) {
			message = "Password must at least be 5 characters long!";
			return message;
		} else if(role.isEmpty()) {
			message = "Role must pick using ComboBox!";
			return message;
		} else {
			message = "Registration Successful!";
			return message;
		}
	}
	
	public String checkChangeProfileInput(String userID, String email, String name, String oldPassword, String newPassword) {
		String message = "";
		
		User checkUserByEmail = user.getUserByEmail(email);
		User checkUserByName = user.getUserByUsername(name);
		
		if(checkUserByEmail != null) {
			message = "Email already exists!";
			return message;
		} else if(checkUserByName != null) {
			message = "Name already in used!";
			return message;
		} else if(oldPassword.equals(newPassword)) {
			message = "Old password same as current password!";
			return message;
		} else if(!newPassword.isEmpty() && newPassword.length() < 5) {
			message = "New password must at least be 5 characters long.!";
			return message;
		} else {
			message = user.changeProfile(userID, email, name, oldPassword, newPassword);
			return message;
		}
	}
}
