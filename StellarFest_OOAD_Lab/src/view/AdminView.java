package view;

import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Event;
import model.Guest;
import model.User;
import model.Vendor;

/**
 * The AdminView class represents the user interface (UI) for 
 * the admin panel of the event management system.
 */

public class AdminView extends Application implements EventHandler<ActionEvent>{
	private AdminController adminController = new AdminController();
	private String eventID;
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	GridPane eventDetailContainer;
	VBox vb;
	TableView<Event> eventTable;
	TableView<User> userTable;
	TableColumn<Event, String> eventIdCol;
	TableColumn<Event, String> eventNameCol;
	TableColumn<Event, String> eventDateCol;
	TableColumn<Event, String> eventLocationCol;
	TableColumn<Event, String> eventDescCol;
	TableColumn<Event, String> eventOrganizerCol;
	TableColumn<User, String> userNameCol;
	TableColumn<User, String> userEmailCol;
	TableColumn<User, String> userRoleCol;
	Vector<Event> events, eventDetails;
	Vector<User> users;
	Vector<Guest> guestAttendeesDetail;
	Vector<Vendor> vendorAttendeesDetail;
	Menu navMenu, profileMenu;
	MenuItem eventItem, userItem, registerItem, loginItem;
	MenuBar navBar;
	Label messageLbl, userMessageLbl, eventNameLbl, eventDateLbl, eventLocationLbl, eventDescLbl, eventDetailTitle,
		  eventName, eventDate, eventLocation, eventDesc,
		  productNameLbl, productDescLbl, manageVendorTitle, guestsLbl, guestsList, vendorsLbl, vendorsList;
	VBox invitationBottomBox, eventBottomBox, eventDetailBox, 
		 manageVendorBox, btnBox, userBtnBox;
	Button deleteEventBtn, deleteUserBtn;
	String tempUserId = null;
	
	@Override
	public void start(Stage stage) throws Exception {
		init();
		arrangeComponent();
		
		this.stage = stage;
		
		this.stage.setScene(scene);
		this.stage.setTitle("Admin View");
		this.stage.setResizable(false);
		this.stage.show();
		
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Do you really want to exit StellarFest ? 😭");
				Optional<ButtonType> resAlert = alert.showAndWait();
				
				if(resAlert.get() == ButtonType.CANCEL) {
					event.consume();
				}
			}
		});
	}
	
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == eventItem) {
			viewAllEvents();
		} else if(e.getSource() == userItem) {
			viewAllUsers();
		} else if(e.getSource() == registerItem || e.getSource() == loginItem) {
			UserView userView = new UserView();
			
			try {
				userView.start(this.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		} else if(e.getSource() == deleteEventBtn) {
			deleteEvent(getEventID());
		} else if(e.getSource() == deleteUserBtn) {
			deleteUser(tempUserId);
		}
	}
	
	private EventHandler<MouseEvent> userTableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<User> userTbm = userTable.getSelectionModel();
				userTbm.setSelectionMode(SelectionMode.SINGLE);
				User selectedUser = userTbm.getSelectedItem();

	            if (selectedUser != null) {
					tempUserId = selectedUser.getUser_id();
	            } else {
	            	return;
	            }
			}
		};
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
					setEventID(selectedEvent.getEvent_id());
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
		loginItem.setOnAction(this);
		events = new Vector<Event>();
		users = new Vector<User>();
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
		guestAttendeesDetail = new Vector<Guest>();
		vendorAttendeesDetail = new Vector<Vendor>();
		guestsLbl = new Label("Guests: ");
		guestsList = new Label();
		vendorsLbl = new Label("Vendors: ");
		vendorsList = new Label();
		btnBox = new VBox();
		deleteEventBtn = new Button("Delete Event");
		deleteEventBtn.setOnAction(this);
		
		userMessageLbl = new Label();
		deleteUserBtn = new Button("Delete User");
		deleteUserBtn.setOnAction(this);
		userBtnBox = new VBox();
		
		eventTable = new TableView<Event>();
		eventIdCol = new TableColumn<Event, String>("ID");
		eventNameCol = new TableColumn<Event, String>("Event Name");
		eventDateCol = new TableColumn<Event, String>("Event Date");
		eventLocationCol = new TableColumn<Event, String>("Location");
		eventDescCol = new TableColumn<Event, String>("Description");
		eventOrganizerCol = new TableColumn<Event, String>("Organizer ID");
		
		userTable = new TableView<User>();
		userNameCol = new TableColumn<User, String>("Username");
		userEmailCol = new TableColumn<User, String>("Email");
		userRoleCol = new TableColumn<User, String>("Role");
		
		eventDetailTitle = new Label("Event Detail");
		eventNameLbl = new Label("Event Name: ");
		eventName = new Label("");
		eventDateLbl = new Label("Event Date: ");
		eventDate = new Label("");
		eventLocationLbl = new Label("Event Location: ");
		eventLocation = new Label("");
		eventDescLbl = new Label("Event Description: ");
		eventDesc = new Label("");
		messageLbl = new Label();
		
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
	
		userEmailCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_email"));
		userNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_name"));
		userRoleCol.setCellValueFactory(new PropertyValueFactory<User, String>("user_role"));
		
		userTable.getColumns().addAll(userEmailCol, userNameCol, userRoleCol);
		
		userNameCol.prefWidthProperty().bind(userTable.widthProperty().multiply(0.34));
		userEmailCol.prefWidthProperty().bind(userTable.widthProperty().multiply(0.34));
		userRoleCol.prefWidthProperty().bind(userTable.widthProperty().multiply(0.34));
		
		userTable.setOnMouseClicked(userTableMouseEvent());
		
		refreshEventTable();
		refreshUserTable();
	}
	
	public void arrangeComponent() {
		navMenu.getItems().add(eventItem);
		navMenu.getItems().add(userItem);
		profileMenu.getItems().add(registerItem);
		profileMenu.getItems().add(loginItem);
		navBar.getMenus().add(navMenu);
		navBar.getMenus().add(profileMenu);
		
		btnBox.getChildren().add(messageLbl);
		btnBox.getChildren().add(deleteEventBtn);
		userBtnBox.getChildren().add(userMessageLbl);
		userBtnBox.getChildren().add(deleteUserBtn);
		btnBox.setAlignment(Pos.CENTER);
		userBtnBox.setAlignment(Pos.CENTER);
		
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
	    
	    guestsLbl.setFont(new Font("Verdana", 16));
	    guestsLbl.setStyle("-fx-font-weight: bold");
	    
	    vendorsLbl.setFont(new Font("Verdana", 16));
	    vendorsLbl.setStyle("-fx-font-weight: bold");
	    
		eventDetailContainer.add(eventNameLbl, 0, 0);
		eventDetailContainer.add(eventName, 1, 0);
		
		eventDetailContainer.add(eventDateLbl, 0, 1);
		eventDetailContainer.add(eventDate, 1, 1);
		
		eventDetailContainer.add(eventLocationLbl, 0, 2);
		eventDetailContainer.add(eventLocation, 1, 2);
		
		eventDetailContainer.add(eventDescLbl, 0, 3);
		eventDetailContainer.add(eventDesc, 1, 3);
		
		eventDetailContainer.add(guestsLbl, 0, 4);
		eventDetailContainer.add(guestsList, 1, 4);
		
		eventDetailContainer.add(vendorsLbl, 0, 5);
		eventDetailContainer.add(vendorsList, 1, 5);
		
		eventDetailContainer.setPadding (new Insets (20));
		eventDetailContainer.setVgap(10);
		eventDetailContainer.setHgap(10);
		eventDetailContainer.setAlignment(Pos.CENTER);
		
		eventDetailBox.getChildren().add(eventDetailTitle);
	    eventDetailBox.setMargin(eventDetailTitle, new Insets(10, 10, 10, 10));
	    eventDetailBox.getChildren().add(eventDetailContainer);
	    eventDetailBox.setAlignment(Pos.CENTER);
	}
	
	public void refreshEventTable() {	    
	    getAllEvents();
	    ObservableList<Event> regEventsObs = FXCollections.observableArrayList(events);
	    eventTable.setItems(regEventsObs);
	}

	public void refreshUserTable() {	    
	    getAllUsers();
	    ObservableList<User> regUsersObs = FXCollections.observableArrayList(users);
	    userTable.setItems(regUsersObs);
	}
	
	public void viewAllEvents() {
		getAllEvents();
		borderContainer.setCenter(eventTable);
		borderContainer.setBottom(btnBox);
	}
	
	public void viewAllUsers() {
		getAllUsers();
		borderContainer.setCenter(userTable);
		borderContainer.setBottom(userBtnBox);
	}
	
	public void viewEventDetails(String eventID) {
		guestAttendeesDetail.removeAllElements();
		vendorAttendeesDetail.removeAllElements();
		
		if(adminController.getGuestsByTransactionID(eventID) != null) {
			guestAttendeesDetail = adminController.getGuestsByTransactionID(eventID);	
		}
		
		if(adminController.getVendorsByTransactionID(eventID) != null) {
			vendorAttendeesDetail = adminController.getVendorsByTransactionID(eventID);	
		}
		
		guestsList.setText(guestAttendeesDetail.toString());
		vendorsList.setText(vendorAttendeesDetail.toString());
		borderContainer.setBottom(null);
	}
	
	public void getAllEvents() {
		events.removeAllElements();
	    events = adminController.viewAllEvents();
	}
	
	public void deleteEvent(String eventID) {
		String message = adminController.deleteEvent(eventID);
		
		messageLbl.setText(message);
		
		setEventID(null);
		viewAllEvents();
		refreshEventTable();
	}

	public void deleteUser(String userID) {
		String message = adminController.deleteUser(userID);
		
		userMessageLbl.setText(message);
		
		tempUserId = null;
		viewAllUsers();
		refreshUserTable();
	}
	
	public void getAllUsers() {
		users.removeAllElements();
		users = adminController.getAllUsers();
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
}
