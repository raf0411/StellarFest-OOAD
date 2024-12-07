package view;

import java.util.Vector;

import controller.AdminController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Event;
import model.User;

public class AdminView extends Application implements EventHandler<ActionEvent>{
	private AdminController adminController = new AdminController();
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	GridPane eventDetailContainer;
	VBox vb;
	TableView<Event> eventTable;
	TableColumn<Event, String> eventIdCol;
	TableColumn<Event, String> eventNameCol;
	TableColumn<Event, String> eventDateCol;
	TableColumn<Event, String> eventLocationCol;
	TableColumn<Event, String> eventDescCol;
	TableColumn<Event, String> eventOrganizerCol;
	Vector<Event> events;
	Vector<User> users;
	Menu navMenu, profileMenu;
	MenuItem eventItem, userItem, registerItem, loginItem;
	MenuBar navBar;
	Label messageLbl, eventNameLbl, eventDateLbl, eventLocationLbl, eventDescLbl, eventDetailTitle,
	  eventName, eventDate, eventLocation, eventDesc,
	  productNameLbl, productDescLbl, manageVendorTitle;
	VBox invitationBottomBox, eventBottomBox, eventDetailBox, manageVendorBox;
	
	@Override
	public void start(Stage stage) throws Exception {
		init();
		arrangeComponent();
		
		this.stage = stage;
		
		this.stage.setScene(scene);
		this.stage.setTitle("Admin");
		this.stage.setResizable(false);
		this.stage.show();
	}
	
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == eventItem) {
			viewAllEvents();
		} else if(e.getSource() == userItem) {
			
		} else if(e.getSource() == registerItem || e.getSource() == loginItem) {
			UserView userView = new UserView();
			
			try {
				userView.start(this.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private EventHandler<MouseEvent> eventTableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Event> eventTbm = eventTable.getSelectionModel();
				eventTbm.setSelectionMode(SelectionMode.SINGLE);
				Event selectedEvent = eventTbm.getSelectedItem();

	            if (selectedEvent != null) {
					viewEventDetails(selectedEvent.getEvent_id());
					
					eventName.setText(selectedEvent.getEvent_name());
					eventDate.setText(selectedEvent.getEvent_date());
					eventLocation.setText(selectedEvent.getEvent_location());
					eventDesc.setText(selectedEvent.getEvent_description());
					
					borderContainer.setCenter(eventDetailBox);
	            } else {
	            	return;
	            }
			}
		};
	}
	
	public void init() {
		profileMenu = new Menu("Profile");
		profileMenu.setOnAction(this);
		registerItem = new MenuItem("Register");
		registerItem.setOnAction(this);
		loginItem = new MenuItem("Login");
		events = new Vector<Event>();
		stage = new Stage();
		borderContainer = new BorderPane();
		eventDetailContainer = new GridPane();
		vb = new VBox();
		navMenu = new Menu("Menu");
		eventItem = new MenuItem("Events");
		eventItem.setOnAction(this);
		userItem = new MenuItem("Users");
		userItem.setOnAction(this);
		navBar = new MenuBar();
		scene = new Scene(borderContainer, 1280, 720);
		eventDetailBox = new VBox();
		
		eventTable = new TableView<Event>();
		eventIdCol = new TableColumn<Event, String>("ID");
		eventNameCol = new TableColumn<Event, String>("Event Name");
		eventDateCol = new TableColumn<Event, String>("Event Date");
		eventLocationCol = new TableColumn<Event, String>("Location");
		eventDescCol = new TableColumn<Event, String>("Description");
		eventOrganizerCol = new TableColumn<Event, String>("Organizer ID");
		
		eventDetailTitle = new Label("Event Detail");
		eventNameLbl = new Label("Event Name: ");
		eventName = new Label("");
		eventDateLbl = new Label("Event Date: ");
		eventDate = new Label("");
		eventLocationLbl = new Label("Event Location: ");
		eventLocation = new Label("");
		eventDescLbl = new Label("Event Description: ");
		eventDesc = new Label("");
		
		eventIdCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_id"));
		eventNameCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_name"));
		eventDateCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_date"));
		eventLocationCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_location"));
		eventDescCol.setCellValueFactory(new PropertyValueFactory<Event, String>("event_description"));
		eventOrganizerCol.setCellValueFactory(new PropertyValueFactory<Event, String>("organizer_id"));
	
		eventTable.getColumns().addAll(eventIdCol, eventNameCol, eventDateCol, eventLocationCol, eventDescCol, eventOrganizerCol);
		
		eventIdCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		eventNameCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		eventDateCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		eventLocationCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		eventDescCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		eventOrganizerCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		
		eventTable.setOnMouseClicked(eventTableMouseEvent());
		
		refreshTable();
	}
	
	public void initUser() {
		users = new Vector<User>();
	}
	
	public void arrangeComponent() {
		navMenu.getItems().add(eventItem);
		navMenu.getItems().add(userItem);
		profileMenu.getItems().add(registerItem);
		profileMenu.getItems().add(loginItem);
		navBar.getMenus().add(navMenu);
		navBar.getMenus().add(profileMenu);
		
		borderContainer.setTop(navBar);
		
	    eventDetailTitle.setFont(new Font("Verdana", 24));
	    eventDetailTitle.setStyle("-fx-font-weight: bold");
	    
	    eventNameLbl.setFont(new Font("Verdana", 16));
	    eventNameLbl.setStyle("-fx-font-weight: bold");
	    
	    eventDateLbl.setFont(new Font("Verdana", 16));
	    eventDateLbl.setStyle("-fx-font-weight: bold");
	    
	    eventLocationLbl.setFont(new Font("Verdana", 16));
	    eventLocationLbl.setStyle("-fx-font-weight: bold");
	    
	    eventDescLbl.setFont(new Font("Verdana", 16));
	    eventDescLbl.setStyle("-fx-font-weight: bold");
	    
		eventDetailContainer.add(eventNameLbl, 0, 0);
		eventDetailContainer.add(eventName, 1, 0);
		
		eventDetailContainer.add(eventDateLbl, 0, 1);
		eventDetailContainer.add(eventDate, 1, 1);
		
		eventDetailContainer.add(eventLocationLbl, 0, 2);
		eventDetailContainer.add(eventLocation, 1, 2);
		
		eventDetailContainer.add(eventDescLbl, 0, 3);
		eventDetailContainer.add(eventDesc, 1, 3);
		
		eventDetailContainer.setPadding (new Insets (20));
		eventDetailContainer.setVgap(10);
		eventDetailContainer.setHgap(10);
		eventDetailBox.getChildren().add(eventDetailTitle);
	    eventDetailBox.setMargin(eventDetailTitle, new Insets(10, 10, 10, 10));
	    eventDetailBox.getChildren().add(eventDetailContainer);
	    eventDetailBox.setAlignment(Pos.CENTER);
	}
	
	public void refreshTable() {	    
	    getAllEvents();
	    ObservableList<Event> regEventsObs = FXCollections.observableArrayList(events);
	    eventTable.setItems(regEventsObs);
	}
	
	public void viewAllEvents() {
		getAllEvents();
		borderContainer.setCenter(eventTable);
	}
	
	public void viewEventDetails(String eventID) {
		getGuestsByTransactionID(eventID);
		getVendorsByTransactionID(eventID);
	}
	
	public void getAllEvents() {
		events.removeAllElements();
	    events = adminController.viewAllEvents();
	}
	
	public void deleteEvent(String eventID) {
		
	}

	public void deleteUser(String userID) {
		
	}
	
	public void getAllUsers() {
		
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
}
