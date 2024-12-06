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
	
	public String changeProfile(String oldEmail, String email, String name, String oldPassword, String newPassword) {
	    String message = "";

	    User currentUser = getUserByEmail(oldEmail); 
	    if (currentUser == null) {
	        return "Current user not found!";
	    }

	    // Check if the new email already exists in the database
	    if (!email.isEmpty() && !email.equals(currentUser.getUser_email()) && getUserByEmail(email) != null) {
	        return "Email already exists!";
	    }

	    // Check if the new username already exists in the database
	    if (!name.isEmpty() && !name.equals(currentUser.getUser_name()) && getUserByUsername(name) != null) {
	        return "Username already exists!";
	    }

	    // Validate input (optional)
	    message = checkChangeProfileInput(email, name, oldPassword, newPassword);
	    if (!message.equals("Change Profile successful!")) {
	        return message;
	    }

	    // Perform the update in the database
	    try {
	        String userID = currentUser.getUser_id();
	        user.changeProfile(
	            userID,
	            email.isEmpty() ? currentUser.getUser_email() : email,
	            name.isEmpty() ? currentUser.getUser_name() : name,
	            oldPassword,
	            newPassword.isEmpty() ? currentUser.getUser_password() : newPassword
	        );
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "An error occurred while updating the profile!";
	    }

	    return "Profile updated successfully!";
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
	
	public String checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		String message = "";	
		
		if(!newPassword.isEmpty() && oldPassword.equals(newPassword)) {
			message = "Password cannot be the same as old password!";
			return message;
		} 
		else if(!newPassword.isEmpty() && newPassword.length() < 5) {
			message = "Password must at least be 5 characters long!";
			return message;
		}
		else {
			message = "Change Profile successful!";
			return message;
		}
	}
}
