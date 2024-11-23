package view;

import controller.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

public class UserView extends Application{
	private UserController userController;
	
	Scene scene;
	BorderPane bp;
	GridPane gp;
	GridPane formContainer;
	FlowPane fp;
	Label logoLbl , titleLbl , emailLbl, usernameLbl, passwordLbl, roleLbl;
	TextField emailTF, usernameTF;
	PasswordField passwordPF;
	ComboBox<String> rolesCB;
	Button regisBtn, loginBtn, changeBtn;
	
	public void init() {
		bp = new BorderPane();
		gp = new GridPane();
		formContainer = new GridPane();
		fp = new FlowPane();
		
		logoLbl = new Label("🎟️ StellarFest");
		emailLbl = new Label("Email");
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		roleLbl = new Label("Role");
		emailTF = new TextField();
		usernameTF = new TextField();
		passwordPF = new PasswordField();
		rolesCB = new ComboBox<>();
		regisBtn = new Button("REGISTER");
		loginBtn = new Button("LOGIN");
		changeBtn = new Button("CHANGE");
		
		rolesCB.getItems().add("Event Organizer");
		rolesCB.getItems().add("Vendor");
		rolesCB.getItems().add("Guest");
		rolesCB.getSelectionModel();
		
		scene = new Scene(bp, 500, 250);
	}
	
	public void initRegisForm() {
		titleLbl = new Label("Register");
		
		bp.setTop(gp);
		bp.setCenter(formContainer);
		bp.setBottom(regisBtn);
		
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
	}
	
	public void initLoginForm() {
		titleLbl = new Label("Login");
		
		bp.setTop(gp);
		bp.setCenter(formContainer);
		bp.setBottom(loginBtn);
		
		gp.add(logoLbl, 0, 0);
		gp.add(titleLbl, 0, 1);
		
		formContainer.add(emailLbl, 0, 0);
		formContainer.add(emailTF, 1, 0);
		
		formContainer.add(passwordLbl, 0, 2);
		formContainer.add(passwordPF, 1, 2);
	}
	
	public void initChangeProfileForm() {
		titleLbl = new Label("Change Profile");
		
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
	
	public void register(String email, String name, String password, String role) {
	}
	
	public void login(String email, String password) {
		
	}
	
	public void changeProfile(String email, String name, String oldPassword, String newPassword) {
		
	}
	
	public void getUserByEmail(String email) {
		
	}
	
	public void getUserByUsername(String name) {
		
	}
	
	public void checkRegisterInput(String email, String name, String password) {
		
	}
	
	public void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		
	}

	@Override
	public void start(Stage s) throws Exception {
		init();
		s.setTitle("User View");
		initChangeProfileForm();
		s.setScene(scene);
		s.show();
	}
}
