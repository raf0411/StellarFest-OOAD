package view;

import controller.UserController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangeProfileView extends Application implements EventHandler<ActionEvent>{
	private UserController userController = new UserController();
	private String userEmail;
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	GridPane changeProfileForm;
	TextField emailTF, nameTF;
	PasswordField passwordPF;
	Label emailLbl, nameLbl, passwordLbl, messageLbl;
	Button submitBtn, backBtn;
	VBox btnBox;
	
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == backBtn) {
			String oldPassword = userController.getUserByEmail(userEmail).getUser_password();
			String userID = userController.getUserByEmail(userEmail).getUser_id();
			
			switch(userController.getUserByEmail(userEmail).getUser_role()) {
				case "Vendor":
					VendorView vendorView = new VendorView();
					try {
						vendorView.setOldPassword(oldPassword);
						vendorView.setUserId(userID);
						vendorView.setEmail(userEmail);
						vendorView.start(this.stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
					
				case "Guest":
					GuestView guestView = new GuestView();
					
					try {
						guestView.setOldPassword(oldPassword);
						guestView.setUserId(userID);
						guestView.setEmail(userEmail);
						guestView.start(this.stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
					
				case "Event Organizer":
					EventOrganizerView eventOrganizerView = new EventOrganizerView();
					try {
						eventOrganizerView.setOldPassword(oldPassword);
						eventOrganizerView.setEmail(userEmail);
						eventOrganizerView.setUserID(userID);
						eventOrganizerView.start(this.stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
					
				default:
					break;
			}
			
		} else if(e.getSource() == submitBtn) {
			String userID = userController.getUserByEmail(userEmail).getUser_id();
			String email = emailTF.getText();
			String name = nameTF.getText();
			String password = passwordPF.getText();
			String oldPassword = userController.getUserByEmail(userEmail).getUser_password();
			
			changeProfile(userID, email, name, oldPassword, password);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		init();
		arrange();
		
		this.stage = stage;
		this.stage.setTitle("Change Profile");
		this.stage.setScene(scene);
		this.stage.show();
	}

	public void init() {
		stage = new Stage();
		borderContainer = new BorderPane();
		scene = new Scene(borderContainer, 1280, 720);
		emailTF = new TextField();
		nameTF = new TextField();
		passwordPF = new PasswordField();
		emailLbl = new Label("Email: ");
		nameLbl = new Label("Name: ");
		passwordLbl = new Label("Password: ");
		changeProfileForm = new GridPane();
		btnBox = new VBox();
		
		submitBtn = new Button("Submit");
		submitBtn.setOnAction(this);
		
		backBtn = new Button("Back");
		backBtn.setOnAction(this);
		
		messageLbl = new Label();
	}
	
	public void arrange() {
		changeProfileForm.add(emailLbl, 0, 0);
		changeProfileForm.add(emailTF, 1, 0);
		
		changeProfileForm.add(nameLbl, 0, 2);
		changeProfileForm.add(nameTF, 1, 2);
		
		changeProfileForm.add(passwordLbl, 0, 3);
		changeProfileForm.add(passwordPF, 1, 3);
		
		btnBox.getChildren().add(messageLbl);
		btnBox.getChildren().add(submitBtn);
		btnBox.getChildren().add(backBtn);
		
		changeProfileForm.setAlignment(Pos.CENTER);
		btnBox.setAlignment(Pos.CENTER);
		
		borderContainer.setCenter(changeProfileForm);
		borderContainer.setBottom(btnBox);
	}
	
	public void changeProfile(String userID, String email, String name, String oldPassword, String newPassword) {
		String message = userController.changeProfile(userID, email, name, oldPassword, newPassword);
		messageLbl.setText(message);
		
		if(message.equals("Change Profile Successful!")) {
			setUserEmail(email);
		}
		
		clearForm();
	}
	
	public void clearForm() {
		emailTF.setText("");
		nameTF.setText("");
		passwordPF.setText("");
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userRole) {
		this.userEmail = userRole;
	}
}
