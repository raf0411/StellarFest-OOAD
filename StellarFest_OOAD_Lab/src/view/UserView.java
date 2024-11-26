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

public class UserView extends Application implements EventHandler<ActionEvent>{
	private UserController userController = new UserController();
	
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
	
	public void init() {
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
		toRegisBtn = new Button("register");
		
		rolesCB.getItems().add("Event Organizer");
		rolesCB.getItems().add("Vendor");
		rolesCB.getItems().add("Guest");
		rolesCB.getSelectionModel();
		
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
		
		// Top
		gp.setAlignment(Pos.CENTER);
		logoLbl.setFont(new Font("Verdana", 32));
		logoLbl.setStyle("-fx-font-weight: bold;");
		titleLbl.setFont(new Font("Verdana", 24));
		GridPane.setHalignment(titleLbl, javafx.geometry.HPos.CENTER);
	    GridPane.setValignment(titleLbl, javafx.geometry.VPos.CENTER);
		gp.setVgap(20);
		gp.setHgap(20);
	
		// Center
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
		
		// Bottom
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
		
		if(userController.checkRegisterInput(email, username, password, role)) {
			userController.register(email, username, password, role);
			message = "Register Successful! Please login now.";
			infoLbl.setTextFill(Color.GREEN);
			
		} else {
			message = "Register Failed! Please input valid details.";
			infoLbl.setTextFill(Color.RED);
		}
		
		infoLbl.setText(message);
	}
	
	private void loginUser() {
		String message;
		String email = emailTF.getText();
		String password = passwordPF.getText();
		String role = userController.getUserByEmail(email).getUser_role();
		String userId = userController.getUserByEmail(email).getUser_id();
		
		if(userController.login(email, password)) {
			message = "Login Successful! Redirecting...";
			infoLbl.setTextFill(Color.GREEN);
			
			Stage s = (Stage) infoLbl.getScene().getWindow();
			
			// Auth Role Based
			switch(role) {
				case "Event Organizer":
					switchToEventOrganizerView(s, userId);
					break;
				case "Vendor":
					break;
				case "Guest":
					break;
					
				default:
					break;
			}
		} else {
			message = "Login Failed! Please input correct details.";
			infoLbl.setTextFill(Color.RED);
		}
		
		infoLbl.setText(message);
	}

	@Override
	public void start(Stage s) throws Exception {
		init();
		initRegisForm();
		arrangeForm();
		
		s.setTitle("User");
		s.setScene(scene);
		s.setResizable(false);
		s.show();
		
		toLoginBtn.setOnAction(event -> {
			swtichToLoginPage(s);
		});
		
		toRegisBtn.setOnAction(event -> {
			swtichToRegisPage(s);
		});
	}
	
	private void clearForm() {
	    gp.getChildren().clear();
	    formContainer.getChildren().clear();
	    btnContainer.getChildren().clear();
	    fp.getChildren().clear();
	}
	
	private void swtichToRegisPage(Stage s) {
		clearForm();
        initRegisForm();
        arrangeForm();
        s.setScene(scene);
	}
	
	private void swtichToLoginPage(Stage s) {
		clearForm();
        initLoginForm();
        arrangeForm();
        s.setScene(scene);
	}
	
	private void switchToEventOrganizerView(Stage s, String userId) {
		EventOrganizerView eventOrganizerView = new EventOrganizerView();
		
		try {
			eventOrganizerView.setTempUserId(userId);
			eventOrganizerView.start(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == regisBtn) {
			registerUser();
		}else if(e.getSource() == loginBtn) {
			loginUser();
		}
	}
}