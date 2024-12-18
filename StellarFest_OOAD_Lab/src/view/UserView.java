package view;

import controller.UserController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The UserView class represents the user interface (UI) 
 * for users when they want to Register or Login.
 */

public class UserView extends Application implements EventHandler<ActionEvent>{
	private UserController userController = new UserController();
	
	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane gp;
	GridPane formContainer;
	GridPane btnContainer;
	FlowPane fp;
	Label logoLbl, titleLbl, emailLbl, usernameLbl, passwordLbl, roleLbl, existsAccountLbl, infoLbl;
	TextField emailTF, usernameTF;
	PasswordField passwordPF;
	ComboBox<String> rolesCB;
	Button regisBtn, loginBtn, changeBtn, toLoginBtn, toRegisBtn;
	
	@Override
	public void start(Stage stage) throws Exception {
		init();
		initRegisForm();
		arrangeForm();
		
		this.stage = stage;
		this.stage.setTitle("User View");
		this.stage.setScene(scene);
		this.stage.setResizable(false);
		this.stage.show();
	}
	
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == regisBtn) {
			registerUser();
		} else if(e.getSource() == loginBtn) {
			loginUser();
		} else if(e.getSource() == toLoginBtn) {
			switchToLoginPage(stage);
			clearFormField();
		} else if(e.getSource() == toRegisBtn) {
			switchToRegisPage(stage);
			clearFormField();
		}
	}
	
	public void init() {
		stage = new Stage();
		bp = new BorderPane();
		gp = new GridPane();
		formContainer = new GridPane();
		btnContainer = new GridPane();
		fp = new FlowPane();
		
		logoLbl = new Label("StellarFest");
		emailLbl = new Label("Email");
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		roleLbl = new Label("Role");
		infoLbl = new Label("Please input valid info!");
		emailTF = new TextField();
		usernameTF = new TextField();
		passwordPF = new PasswordField();
		rolesCB = new ComboBox<>();
		
		regisBtn = new Button("Register");
		regisBtn.setOnAction(this);
		
		loginBtn = new Button("Login");
		loginBtn.setOnAction(this);
		
		changeBtn = new Button("Change");
		loginBtn.setOnAction(this);
		
		toLoginBtn = new Button("login");
		toLoginBtn.setOnAction(this);
		
		toRegisBtn = new Button("register");
		toRegisBtn.setOnAction(this);
		
		rolesCB.getItems().add("-");
		rolesCB.getItems().add("Event Organizer");
		rolesCB.getItems().add("Vendor");
		rolesCB.getItems().add("Guest");
		rolesCB.getSelectionModel().select("-");
		
		scene = new Scene(bp, 1280, 720);
	}
	
	public void initRegisForm() {
		titleLbl = new Label("Register");
		existsAccountLbl = new Label("Already have an account? ");
		titleLbl.setTextAlignment(TextAlignment.JUSTIFY);
		bp.setTop(gp);
		bp.setCenter(formContainer);
		bp.setBottom(btnContainer);
		
		gp.add(logoLbl, 0, 0);
		gp.add(titleLbl, 0, 1);
		
		formContainer.add(emailLbl, 0, 0);
		formContainer.add(emailTF, 1, 0);
		
		formContainer.add(usernameLbl, 0, 1);
		formContainer.add(usernameTF, 1, 1);
		
		formContainer.add(passwordLbl, 0, 2);
		formContainer.add(passwordPF, 1, 2);	
		
		formContainer.add(roleLbl, 0, 3);
		formContainer.add(rolesCB, 1, 3);
		
		fp.getChildren().add(existsAccountLbl);
		fp.getChildren().add(toLoginBtn);
		
		btnContainer.add(infoLbl, 0, 0);
		btnContainer.add(regisBtn, 0, 1);
		btnContainer.add(fp, 0, 2);
	}
	
	public void initLoginForm() {
		titleLbl = new Label("Login");
		existsAccountLbl = new Label("Don't have an account? ");
		titleLbl.setTextAlignment(TextAlignment.JUSTIFY);
		bp.setTop(gp);
		bp.setCenter(formContainer);
		bp.setBottom(btnContainer);
		
		gp.add(logoLbl, 0, 0);
		gp.add(titleLbl, 0, 1);
		
		formContainer.add(emailLbl, 0, 0);
		formContainer.add(emailTF, 1, 0);
		
		formContainer.add(passwordLbl, 0, 2);
		formContainer.add(passwordPF, 1, 2);
		
		fp.getChildren().add(existsAccountLbl);
		fp.getChildren().add(toRegisBtn);
		
		btnContainer.add(infoLbl, 0, 0);
		btnContainer.add(loginBtn, 0, 1);
		btnContainer.add(fp, 0, 2);
	}
	
	public void initChangeProfileForm() {
		titleLbl = new Label("Change Profile");
		titleLbl.setTextAlignment(TextAlignment.JUSTIFY);
		bp.setTop(gp);
		bp.setCenter(formContainer);
		bp.setBottom(changeBtn);
		
		gp.add(logoLbl, 0, 0);
		gp.add(titleLbl, 0, 1);
		
		formContainer.add(emailLbl, 0, 0);
		formContainer.add(emailTF, 1, 0);
		
		formContainer.add(usernameLbl, 0, 1);
		formContainer.add(usernameTF, 1, 1);
		
		formContainer.add(passwordLbl, 0, 2);
		formContainer.add(passwordPF, 1, 2);
	}
	
	public void arrangeForm() {
		bp.setPadding(new Insets(50));
		
		gp.setAlignment(Pos.CENTER);
		logoLbl.setFont(new Font("Verdana", 32));
		logoLbl.setStyle("-fx-font-weight: bold;");
		titleLbl.setFont(new Font("Verdana", 24));
		GridPane.setHalignment(titleLbl, javafx.geometry.HPos.CENTER);
	    GridPane.setValignment(titleLbl, javafx.geometry.VPos.CENTER);
		gp.setVgap(20);
		gp.setHgap(20);

		formContainer.setAlignment(Pos.CENTER);
		formContainer.setPadding(new Insets(50));
		usernameTF.setMinWidth(200);
		passwordPF.setMinWidth(200);
		emailTF.setMinWidth(200);
		rolesCB.setMinWidth(200);
		
		usernameLbl.setMinWidth(100);
		passwordLbl.setMinWidth(100);
		emailLbl.setMinWidth(100);
		roleLbl.setMinWidth(100);
		
		formContainer.setVgap(20);
		formContainer.setHgap(20);
		
		usernameLbl.setFont(new Font("Verdana", 16));
		passwordLbl.setFont(new Font("Verdana", 16));
		emailLbl.setFont(new Font("Verdana", 16));
		roleLbl.setFont(new Font("Verdana", 16));
		
		btnContainer.setVgap(20);
		btnContainer.setAlignment(Pos.CENTER);
		fp.setAlignment(Pos.CENTER);
		infoLbl.setFont(new Font("Verdana", 16));
		GridPane.setHalignment(infoLbl, javafx.geometry.HPos.CENTER);
	    GridPane.setValignment(infoLbl, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(regisBtn, javafx.geometry.HPos.CENTER);
	    GridPane.setValignment(regisBtn, javafx.geometry.VPos.CENTER);
	    GridPane.setHalignment(loginBtn, javafx.geometry.HPos.CENTER);
	    GridPane.setValignment(loginBtn, javafx.geometry.VPos.CENTER);
	}
	
	private void registerUser() {
		String message;
		String username = usernameTF.getText();
		String password = passwordPF.getText();
		String email = emailTF.getText();
		String role = rolesCB.getSelectionModel().getSelectedItem();

		message = userController.register(email, username, password, role);
		
		infoLbl.setText(message);
	}
	
	private void loginUser() {
	    String email = emailTF.getText();
	    String password = passwordPF.getText();
	    String role = "";
	    String userId = "";
	    String message = "";
	    
	    message = userController.login(email, password);
	    infoLbl.setText(message);
	    
	    if(message.equals("Login Successful!")) {
		    role = userController.getUserByEmail(email).getUser_role();
		    userId = userController.getUserByEmail(email).getUser_id();
	        authUser(role, userId, email, password);
	    }
	}

	// Auth Role Based
	public void authUser(String userRole, String userId, String email, String password) {
		switch(userRole) {
			case "Event Organizer":
				switchToEventOrganizerView(stage, userId, email, password);
				break;
			case "Vendor":
				switchToVendorView(stage, email, userId, password);
				break;
			case "Guest":
				switchToGuestView(stage, email, userId, password);
				break;
			case "Admin":
				switchToAdminView(stage);
			default:
				break;
		}
	}
	
	private void clearForm() {
	    gp.getChildren().clear();
	    formContainer.getChildren().clear();
	    btnContainer.getChildren().clear();
	    fp.getChildren().clear();
	}
	
	private void clearFormField() {
		emailTF.setText("");
		usernameTF.setText("");
		passwordPF.setText("");
		rolesCB.setValue(null);
	}
	
	private void switchToRegisPage(Stage s) {
		clearForm();
        initRegisForm();
        arrangeForm();
        s.setScene(scene);
	}
	
	private void switchToLoginPage(Stage s) {
		clearForm();
        initLoginForm();
        arrangeForm();
        s.setScene(scene);
	}
	
	private void switchToEventOrganizerView(Stage s, String userId, String email, String password) {
		EventOrganizerView eventOrganizerView = new EventOrganizerView();
		
		try {
			eventOrganizerView.setOldPassword(password);
			eventOrganizerView.setEmail(email);
			eventOrganizerView.setUserID(userId);
			eventOrganizerView.start(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void switchToVendorView(Stage s, String email, String id, String password) {
		VendorView vendorView = new VendorView();
		
		try {
			vendorView.setOldPassword(password);
			vendorView.setUserId(id);
			vendorView.setEmail(email);
			vendorView.start(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void switchToGuestView(Stage s, String email, String id, String password) {
		GuestView guestView = new GuestView();
		
		try {
			guestView.setOldPassword(password);
			guestView.setUserId(id);
			guestView.setEmail(email);
			guestView.start(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void switchToAdminView(Stage s) {
		AdminView adminView = new AdminView();
		
		try {
			adminView.start(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}