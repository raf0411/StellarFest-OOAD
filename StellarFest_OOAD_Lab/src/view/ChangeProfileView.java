package view;

import controller.UserController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangeProfileView extends Application implements EventHandler<ActionEvent>{
	private UserController userController = new UserController();
	private String role;
	private String email;
	private String oldPassword;
	private String oldEmail;
	private String userID;
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	GridPane changeProfileForm;
	TextField newNameTF;
	TextField newEmailTF;
	TextField newPasswordTF;
	Button submitBtn, backBtn;
	VBox vb;
	Label messageLbl, newNameLbl, newEmailLbl, newPasswordLbl;
	
	@Override
	public void start(Stage stage) throws Exception {
		init();
		arrange();
		
		this.stage = stage;
		this.stage.setScene(scene);
		this.stage.setTitle("Change Profile");
		this.stage.show();
		
		backBtn.setOnAction(event -> {
		    try {
		        if (getRole().equals("Event Organizer")) {
		            EventOrganizerView eventOrganizerView = new EventOrganizerView();
		            eventOrganizerView.setEmail(this.email);
		            eventOrganizerView.setUserID(getUserID());
		            eventOrganizerView.start(this.stage);
		            eventOrganizerView.refreshEventTable();
		        } else if (getRole().equals("Guest")) {
		            GuestView guestView = new GuestView();
		            guestView.setEmail(this.email);
		            guestView.setUserID(userID);
		            guestView.start(this.stage);
		            guestView.refreshTable();
		        } else if (getRole().equals("Vendor")) {
		            VendorView vendorView = new VendorView();
		            vendorView.setEmail(this.email);
		            vendorView.setUserId(userID);
		            vendorView.start(this.stage);  
		            vendorView.refreshTable();     
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		});

	}

	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == submitBtn) {
			String email = newEmailTF.getText();
			String name = newNameTF.getText();
			String password = newPasswordTF.getText();
			String message = "";
			
			message = changeProfile(getOldEmail(), email, name, getOldPassword(), password);
			
			messageLbl.setText(message);
		}
	}
	
	public void init() {
		stage = new Stage();
		borderContainer = new BorderPane();
		changeProfileForm = new GridPane();
		newNameTF = new TextField();
		newEmailTF = new TextField();
		newPasswordTF = new TextField();
		submitBtn = new Button("Submit");
		submitBtn.setOnAction(this);
		backBtn = new Button("Back");
		messageLbl = new Label();
		newNameLbl = new Label("New Name: ");
		newEmailLbl = new Label("New Email: ");
		newPasswordLbl = new Label("New Password: ");
		vb = new VBox();
		
		scene = new Scene(borderContainer, 1280, 720);
	}
	
	public void arrange() {
	    changeProfileForm.add(newNameLbl, 0, 0);
	    changeProfileForm.add(newNameTF, 1, 0);
	    changeProfileForm.add(newEmailLbl, 0, 1);
	    changeProfileForm.add(newEmailTF, 1, 1);
	    changeProfileForm.add(newPasswordLbl, 0, 2);
	    changeProfileForm.add(newPasswordTF, 1, 2);
	    
	    changeProfileForm.setAlignment(Pos.CENTER);
	    
	    vb.getChildren().add(messageLbl);
	    vb.getChildren().add(submitBtn);
	    vb.getChildren().add(backBtn);
	    
	    vb.setAlignment(Pos.CENTER);

	    changeProfileForm.setHgap(10);
	    changeProfileForm.setVgap(10);
	    
	    borderContainer.setCenter(changeProfileForm);
	    borderContainer.setBottom(vb);
	}
	
	public String changeProfile(String oldEmail, String email, String name, String oldPassword, String newPassword) {
		String message = userController.changeProfile(oldEmail, email, name, oldPassword, newPassword);
		return message;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
