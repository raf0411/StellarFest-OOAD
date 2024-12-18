package main;

// Import necessary JavaFX classes for creating the application window
import javafx.application.Application;
import javafx.stage.Stage;

// Import the UserView that will be used in the application as the starting point
import view.UserView;

public class Main extends Application {

    /**
     * The start method is overridden from the Application class.
     * It is called when the JavaFX application is launched.
     * 
     * This method sets up the initial user interface view and displays it.
     * 
     * @param primaryStage The main window (stage) of the application.
     * @throws Exception If any exception occurs during initialization or stage setup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an instance of the UserView, which will be shown in the primary stage
        UserView userView = new UserView();
        
        // Set the title of the primary stage (window) to "StellarFest"
        primaryStage.setTitle("StellarFest");
        
        // Start the user view on the primary stage, showing the UI to the user
        userView.start(primaryStage);
        
        
    }

    /**
     * The main method is the entry point of the JavaFX application.
     * It launches the JavaFX application by calling the launch method.
     * 
     * @param args Command-line arguments, which are passed to the launch method.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
