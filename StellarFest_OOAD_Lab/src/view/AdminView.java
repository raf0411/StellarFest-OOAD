package view;

import controller.AdminController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminView extends Application implements EventHandler<ActionEvent>{
	private AdminController adminController = new AdminController();
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		this.stage.setScene(scene);
		this.stage.setTitle("Admin");
		this.stage.setResizable(false);
		this.stage.show();
	}
	
	@Override
	public void handle(ActionEvent e) {
		
	}
	
	public void init() {
		borderContainer = new BorderPane();
		scene = new Scene(borderContainer, 1280, 720);
	}
	
	public void viewAllEvents() {
		
	}
	
	public void viewEventDetails(String eventID) {
		
	}
	
	public void deleteEvent(String eventID) {
		
	}

	public void deleteUser(String userID) {
		
	}
	
	public void getAllUsers() {
		
	}
	
	public void getAllEvents() {
		
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
}
