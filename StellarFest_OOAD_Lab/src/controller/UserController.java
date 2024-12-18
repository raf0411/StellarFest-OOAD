package controller;

import model.User;

/**
 * The UserController class handles user-related operations such as registration, login,
 * and profile management. It serves as the Controller in the MVC architecture for
 * user functionalities.
 */
public class UserController {

	// Model object to interact with user-related data operations
	private User user = new User();

	/**
	 * Registers a new user with the provided details.
	 * 
	 * @param email    The email of the new user.
	 * @param name     The username of the new user.
	 * @param password The password of the new user.
	 * @param role     The role of the new user.
	 * @return A message indicating the success or failure of the registration.
	 */
	public String register(String email, String name, String password, String role) {
		String message = "";

		// Check if the email or username already exists
		if (getUserByEmail(email) != null) {
			message = "User email already exists!";
			return message;
		} else if (getUserByUsername(name) != null) {
			message = "User username already exists!";
			return message;
		} else {
			message = checkRegisterInput(email, name, password, role);
		}

		// Return error messages or proceed with registration
		if (!message.equals("Registration Successful!")) {
			return message;
		} else {
			return user.register(email, name, password, role);
		}
	}

	/**
	 * Handles user login by verifying the provided credentials.
	 * 
	 * @param email    The email of the user.
	 * @param password The password of the user.
	 * @return A message indicating the success or failure of the login attempt.
	 */
	public String login(String email, String password) {
		String message = "";

		// Validate input and check user credentials
		if (email.isEmpty()) {
			message = "Email cannot be empty!";
			return message;
		} else if (password.isEmpty()) {
			message = "Password cannot be empty!";
			return message;
		} else if (getUserByEmail(email) == null) {
			message = "Email is not registered!";
			return message;
		} else if (!getUserByEmail(email).getUser_password().equals(password)) {
			message = "Password is incorrect!";
			return message;
		} else {
			message = "Login Successful!";
			return message;
		}
	}

	/**
	 * Allows a user to update their profile details.
	 * 
	 * @param userID      The ID of the user.
	 * @param email       The new email address.
	 * @param name        The new username.
	 * @param oldPassword The current password.
	 * @param newPassword The new password.
	 * @return A message indicating the success or failure of the operation.
	 */
	public String changeProfile(String userID, String email, String name, String oldPassword, String newPassword) {
		String message = checkChangeProfileInput(userID, email, name, oldPassword, newPassword);
		return message;
	}

	/**
	 * Retrieves a User object by their email address.
	 * 
	 * @param email The email of the user to retrieve.
	 * @return The User object if found, otherwise null.
	 */
	public User getUserByEmail(String email) {
		if (email == null || email.isEmpty()) {
			return null;
		}
		if (user.getUserByEmail(email) != null) {
			user = user.getUserByEmail(email);
			return user;
		}

		return null;
	}

	/**
	 * Retrieves a User object by their username.
	 * 
	 * @param name The username of the user to retrieve.
	 * @return The User object if found, otherwise null.
	 */
	public User getUserByUsername(String name) {
		if (user.getUserByUsername(name) != null) {
			user = user.getUserByUsername(name);
			return user;
		}

		return null;
	}

	/**
	 * Validates the input fields for user registration.
	 * 
	 * @param email    The email address.
	 * @param name     The username.
	 * @param password The password.
	 * @param role     The user role.
	 * @return A message indicating whether the input is valid or what needs to be corrected.
	 */
	public String checkRegisterInput(String email, String name, String password, String role) {
		String message = "";

		// Check for empty fields and enforce validation rules
		if (email.isEmpty()) {
			message = "Email cannot be empty!";
			return message;
		} else if (name.isEmpty()) {
			message = "Username cannot be empty!";
			return message;
		} else if (password.isEmpty()) {
			message = "Password cannot be empty!";
			return message;
		} else if (password.length() < 5) {
			message = "Password must at least be 5 characters long!";
			return message;
		} else if (role.isEmpty() || role == null || role.equals("-")) {
			message = "Role must pick using ComboBox!";
			return message;
		} else {
			message = "Registration Successful!";
			return message;
		}
	}

	/**
	 * Validates the input fields for changing a user's profile.
	 * 
	 * @param userID      The ID of the user.
	 * @param email       The new email address.
	 * @param name        The new username.
	 * @param oldPassword The current password.
	 * @param newPassword The new password.
	 * @return A message indicating whether the input is valid or what needs to be corrected.
	 */
	public String checkChangeProfileInput(String userID, String email, String name, String oldPassword, String newPassword) {
		String message = "";

		// Check if the new email or username already exists
		User checkUserByEmail = user.getUserByEmail(email);
		User checkUserByName = user.getUserByUsername(name);

		if (checkUserByEmail != null) {
			message = "Email already exists!";
			return message;
		} else if (checkUserByName != null) {
			message = "Name already in used!";
			return message;
		} else if (!newPassword.isEmpty()) {
			String currentPassword = user.getUserByID(userID).getUser_password();

			// Validate old and new password
			if (oldPassword.isEmpty() || !currentPassword.equals(oldPassword)) {
				message = "Old password must match the current password!";
				return message;
			}

			if (newPassword.length() < 5) {
				message = "New password must be at least 5 characters long!";
				return message;
			}
		} else if (!oldPassword.isEmpty() && newPassword.isEmpty()) {
			message = "New Password must be filled!";
			return message;
		}

		// Call the model to update the profile
		message = user.changeProfile(userID, email, name, oldPassword, newPassword);
		return message;
	}
}
