package view;

import controller.UserController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChangeProfileView extends Application implements EventHandler<ActionEvent>{
	private UserController userController = new UserController();
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	GridPane changeProfileForm;
	TextField newNameTF;
	TextField newEmailTF;
	TextField newPasswordTF;
	Button submitBtn;
	Label messageLbl;
	
	@Override
	public void start(Stage stage) throws Exception {
		init();
		arrange();
		
		this.stage = stage;
		this.stage.setScene(scene);
		this.stage.setTitle("Change Profile");
		this.stage.show();
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void init() {
		stage = new Stage();
		borderContainer = new BorderPane();
		changeProfileForm = new GridPane();
		newNameTF = new TextField();
		newEmailTF = new TextField();
		newPasswordTF = new TextField();
		submitBtn = new Button("Submit");
		messageLbl = new Label();
		
		scene = new Scene(borderContainer, 1280, 720);
	}
	
	public void arrange() {
		
	}
}
