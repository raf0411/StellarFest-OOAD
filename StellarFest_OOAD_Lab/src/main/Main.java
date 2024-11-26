package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.EventOrganizerView;
import view.UserView;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		UserView userView = new UserView();
	
		primaryStage.setTitle("StellarFest");
		userView.start(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
