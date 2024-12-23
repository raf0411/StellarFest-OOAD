package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;

import controller.EventController;
import controller.EventOrganizerController;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Event;
import model.Guest;
import model.Vendor;

/**
 * The EventOrganizerView class represents the user interface (UI) for 
 * the Event Organizer
 */

public class EventOrganizerView extends Application implements EventHandler<ActionEvent>{
	private EventOrganizerController eventOrganizerController = new EventOrganizerController();
	private EventController eventController = new EventController();
	private String userID, tempInvitedUserEmail;
	private Vector<Event> events;
	private Vector<Vendor> vendors;
	private Vector<Guest> guests;
	private String tempEventID;
	private String tempEventName;
	private String message;
	private String email;
	private String oldPassword;
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	GridPane eventDetailContainer, editEventForm, createEventForm;
	VBox vb, btnEditDetailVB, btnCreateVB, vendorBtnBox, guestBtnBox;
	HBox eventDetailBtnBox;
	Menu navMenu, profileMenu;
	MenuItem registerItem, loginItem, changeProfileItem, eventItem, createEventItem;
	MenuBar navBar;
	TableView<Event> eventTable;
	TableView<Vendor> vendorTable;
	TableView<Guest> guestTable;
	Label eventNameLbl, eventDateLbl, eventLocationLbl, eventDescLbl, guestAttendeesLbl, vendorAttendeesLbl,
		  eventName, eventDate, eventLocation, eventDesc, guestAttendees, vendorAttendees, messageLbl, vendorMessageLbl, guestMessageLbl;
	Button addVendorBtn, addGuestsBtn,editEventBtn, confirmEditBtn, saveEventBtn, addBtn, addGuestBtn;
	TextField eventNameTF, eventLocationTF;
	TextArea eventDescTF;
	DatePicker eventDateDP;
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Override
	public void start(Stage stage) throws Exception {
		init();
		arrange();
		
		this.stage = stage;
		this.stage.setTitle("Event Organizer View");
		this.stage.setScene(scene);
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
			viewOrganizedEvents(userID);
		} else if(e.getSource() == addVendorBtn) {
			viewAddVendors();
		} else if(e.getSource() == editEventBtn) {
			eventNameTF.setText("");
			viewEditForm();
		} else if(e.getSource() == confirmEditBtn) {
			editEventName(tempEventID, tempEventName);
		} else if(e.getSource() == createEventItem) {
			viewCreateEventForm();
		} else if(e.getSource() == saveEventBtn) {
			createEvent();
		} else if(e.getSource() == changeProfileItem) {
	        ChangeProfileView changeProfileView = new ChangeProfileView();
	        
	        try {
	        	changeProfileView.setUserEmail(email);
	            changeProfileView.start(this.stage);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		} else if(e.getSource() == loginItem || e.getSource() == registerItem) {
			UserView userView = new UserView();
			
			try {
				userView.start(this.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == addBtn) {
			String message = eventOrganizerController.sendInvitation(tempInvitedUserEmail, tempEventID);
			vendorMessageLbl.setText(message);
		} else if(e.getSource() == addGuestsBtn) {
			viewAddGuests();
		} else if(e.getSource() == addGuestBtn) {
			String message = eventOrganizerController.sendInvitation(tempInvitedUserEmail, tempEventID);
			guestMessageLbl.setText(message);
		}
	}
	
	private EventHandler<MouseEvent> vendorTableMouseEvent() {		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Vendor> vendorTsm = vendorTable.getSelectionModel();
				vendorTsm.setSelectionMode(SelectionMode.SINGLE);
				Vendor selectedVendor = vendorTsm.getSelectedItem();
				
				tempInvitedUserEmail = "";
				
				if(selectedVendor == null) {
					return;
				}
				
				tempInvitedUserEmail = selectedVendor.getUser_email();
			}
		};
	}
	
	private EventHandler<MouseEvent> guestTableMouseEvent(){
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Guest> guestTsm = guestTable.getSelectionModel();
				guestTsm.setSelectionMode(SelectionMode.SINGLE);
				Guest selectedGuest = guestTsm.getSelectedItem();
				
				tempInvitedUserEmail = "";
				
				if(selectedGuest == null) {
					return;
				}
				
				tempInvitedUserEmail = selectedGuest.getUser_email();
			}
		};
	}
	
	private EventHandler<MouseEvent> eventTableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				TableSelectionModel<Event> eventTsm = eventTable.getSelectionModel();
				eventTsm.setSelectionMode(SelectionMode.SINGLE);
				Event selectedEvent = eventTsm.getSelectedItem();
				
				if(selectedEvent == null) {
					return;
				}
				
				viewOrganizedEventDetails(selectedEvent.getEvent_id());
				
				tempEventID = selectedEvent.getEvent_id();
				tempEventName = selectedEvent.getEvent_name();
				eventName.setText(selectedEvent.getEvent_name());
				eventDate.setText(selectedEvent.getEvent_date());
				eventLocation.setText(selectedEvent.getEvent_location());
				eventDesc.setText(selectedEvent.getEvent_description());
			}
		};
	}
	
	public void init() {
		eventDetailBtnBox = new HBox();
		vendorBtnBox = new VBox();
		btnEditDetailVB = new VBox();
		btnCreateVB = new VBox();
		vendors = new Vector<Vendor>();
		vendorTable = new TableView<Vendor>();
		editEventForm = new GridPane();
		eventDetailContainer = new GridPane();
		guestAttendeesLbl = new Label("Guests: ");
		guestAttendees = new Label();
		vendorAttendeesLbl = new Label("Vendors: ");
		vendorAttendees = new Label();
		eventNameLbl = new Label("Event Name: ");
		eventName = new Label();
		eventDateLbl = new Label("Event Date: ");
		eventDate = new Label();
		eventLocationLbl = new Label("Event Location: ");
		eventLocation = new Label();
		eventDescLbl = new Label("Event Description: ");
		eventDesc = new Label();
		editEventBtn = new Button("Edit Event Name");
		editEventBtn.setOnAction(this);
		eventNameTF = new TextField();
		confirmEditBtn = new Button("Confirm");
		confirmEditBtn.setOnAction(this);
		saveEventBtn = new Button("Save Event");
		saveEventBtn.setOnAction(this);
		messageLbl = new Label();
		vendorMessageLbl = new Label();
		addBtn = new Button("Add");
		addBtn.setOnAction(this);
		guestBtnBox = new VBox();
		
		guestTable = new TableView<Guest>();
		guests = new Vector<Guest>();
		
		addGuestsBtn = new Button("Add Guest");
		addGuestsBtn.setOnAction(this);
		
		addGuestBtn = new Button("Add");
		addGuestBtn.setOnAction(this);
		
		guestMessageLbl = new Label();
		
		events = new Vector<Event>();
		stage = new Stage();
		borderContainer = new BorderPane();
		vb = new VBox();
	
		scene = new Scene(borderContainer, 1280, 720);
		
		navMenu = new Menu("Menu");
		profileMenu = new Menu("Profile");
		registerItem = new MenuItem("Register");
		registerItem.setOnAction(this);
		loginItem = new MenuItem("Login");
		loginItem.setOnAction(this);
		changeProfileItem = new MenuItem("Change Profile");
		changeProfileItem.setOnAction(this);
		eventItem = new MenuItem("Event");
		eventItem.setOnAction(this);
		createEventItem = new MenuItem("Create Event");
		createEventItem.setOnAction(this);
		navBar = new MenuBar();
		eventTable = new TableView<Event>();
		
		createEventForm = new GridPane();
		eventDateDP = new DatePicker();
		eventLocationTF = new TextField();
		eventDescTF = new TextArea();
		
		addVendorBtn = new Button("Add Vendor");
		addVendorBtn.setOnAction(this);
		
		TableColumn<Event, String> eventIdCol = new TableColumn<Event, String>("Event ID");
		eventIdCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		eventIdCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		
		TableColumn<Event, String> eventNameCol = new TableColumn<Event, String>("Event Name");
		eventNameCol.setCellValueFactory(new PropertyValueFactory<>("event_name"));
		eventNameCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
	
		TableColumn<Event, String> eventDateCol = new TableColumn<Event, String>("Event Date");
		eventDateCol.setCellValueFactory(new PropertyValueFactory<>("event_date"));
		eventDateCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		
		TableColumn<Event, String> eventLocationCol = new TableColumn<Event, String>("Event Location");
		eventLocationCol.setCellValueFactory(new PropertyValueFactory<>("event_location"));
		eventLocationCol.prefWidthProperty().bind(eventTable.widthProperty().multiply(0.34));
		
		eventTable.getColumns().addAll(eventIdCol, eventNameCol, eventDateCol, eventLocationCol);
		eventTable.setOnMouseClicked(eventTableMouseEvent());
		
		TableColumn<Vendor, String> vendorIdCol = new TableColumn<>("Vendor ID");
		vendorIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		vendorIdCol.prefWidthProperty().bind(vendorTable.widthProperty().multiply(0.34));

		TableColumn<Vendor, String> vendorNameCol = new TableColumn<>("Vendor Name");
		vendorNameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
		vendorNameCol.prefWidthProperty().bind(vendorTable.widthProperty().multiply(0.34));

		TableColumn<Vendor, String> vendorEmailCol = new TableColumn<>("Vendor Email");
		vendorEmailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));
		vendorEmailCol.prefWidthProperty().bind(vendorTable.widthProperty().multiply(0.34));

		vendorTable.getColumns().addAll(vendorIdCol, vendorNameCol, vendorEmailCol);
		vendorTable.setOnMouseClicked(vendorTableMouseEvent());
		
		TableColumn<Guest, String> guestIdCol = new TableColumn<>("Guest ID");
		guestIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		guestIdCol.prefWidthProperty().bind(guestTable.widthProperty().multiply(0.34));

		TableColumn<Guest, String> guestNameCol = new TableColumn<>("Guest Name");
		guestNameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
		guestNameCol.prefWidthProperty().bind(guestTable.widthProperty().multiply(0.34));

		TableColumn<Guest, String> guestEmailCol = new TableColumn<>("Guest Email");
		guestEmailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));
		guestEmailCol.prefWidthProperty().bind(guestTable.widthProperty().multiply(0.34));

		guestTable.getColumns().addAll(guestIdCol, guestNameCol, guestEmailCol);
		guestTable.setOnMouseClicked(guestTableMouseEvent());
	}

	public void arrange() {
		navMenu.getItems().add(eventItem);
		navMenu.getItems().add(createEventItem);
		profileMenu.getItems().add(registerItem);
		profileMenu.getItems().add(loginItem);
		profileMenu.getItems().add(changeProfileItem);
		navBar.getMenus().add(navMenu);
		navBar.getMenus().add(profileMenu);
		
		btnEditDetailVB.setAlignment(Pos.CENTER);
		btnCreateVB.setAlignment(Pos.CENTER);
		
		vendorBtnBox.getChildren().add(vendorMessageLbl);
		vendorBtnBox.getChildren().add(addBtn);
		vendorBtnBox.setAlignment(Pos.CENTER);
		
		guestBtnBox.getChildren().add(guestMessageLbl);
		guestBtnBox.getChildren().add(addGuestBtn);
		guestBtnBox.setAlignment(Pos.CENTER);
		
		eventDetailBtnBox.getChildren().addAll(editEventBtn, addVendorBtn, addGuestsBtn);
		eventDetailBtnBox.setAlignment(Pos.CENTER);
		
		borderContainer.setMargin(eventDetailBtnBox, new Insets(10));
		
		borderContainer.setTop(navBar);
		borderContainer.setBottom(vb);
	}
	
	public void viewOrganizedEvents(String userID) {
		refreshEventTable();
	    borderContainer.setCenter(eventTable);
	    borderContainer.setBottom(vb);
	}
	
	public void refreshEventTable() {
		getAllEvents();
	    ObservableList<Event> eventRegObs = FXCollections.observableArrayList(events);
	    eventTable.setItems(eventRegObs);
	}
	
	public void getAllEvents() {
		events.removeAllElements();
		events = eventOrganizerController.viewOrganizedEvents(userID);
	}
	
	public void viewOrganizedEventDetails(String eventID) {
		ArrayList<String> guestAttendeesDetail = new ArrayList<>();
		ArrayList<String> vendorAttendeesDetail = new ArrayList<>();
		
		Event tempEvent = eventOrganizerController.viewOrganizedEventDetails(eventID);
		
		eventDetailContainer.getChildren().clear();
		
		eventDetailContainer.add(eventNameLbl, 0, 0);
		eventDetailContainer.add(eventName, 1, 0);
		
		eventDetailContainer.add(eventDateLbl, 0, 2);
		eventDetailContainer.add(eventDate, 1, 2);
		
		eventDetailContainer.add(eventLocationLbl, 0, 3);
		eventDetailContainer.add(eventLocation, 1, 3);
		
		eventDetailContainer.add(eventDescLbl, 0, 4);
		eventDetailContainer.add(eventDesc, 1, 4);
		
		eventDetailContainer.add(guestAttendeesLbl, 0, 5);
		eventDetailContainer.add(guestAttendees, 1, 5);
		
		eventDetailContainer.add(vendorAttendeesLbl, 0, 6);
		eventDetailContainer.add(vendorAttendees, 1, 6);
		
		eventDetailContainer.setAlignment(Pos.CENTER);
	
		for (Guest g : tempEvent.getGuests()) {
			guestAttendeesDetail.add(g.getUser_name());
		}
		
		for (Vendor v : tempEvent.getVendors()) {
			vendorAttendeesDetail.add(v.getUser_name());
		}
		
		guestAttendees.setText(guestAttendeesDetail.toString());
		vendorAttendees.setText(vendorAttendeesDetail.toString());
		
		borderContainer.setCenter(eventDetailContainer);
		borderContainer.setBottom(eventDetailBtnBox);
	}
	
	public void viewAddVendors() {
		vendorMessageLbl.setText("");
		vendors.removeAllElements();
		vendors = eventOrganizerController.getVendors();
		
	    ObservableList<Vendor> vendorRegObs = FXCollections.observableArrayList(vendors);
	    vendorTable.setItems(vendorRegObs);
	    
	    borderContainer.setCenter(vendorTable);
	    borderContainer.setBottom(vendorBtnBox);
	}
	
	public void viewAddGuests() {
		guestMessageLbl.setText("");
		guests.removeAllElements();
		guests = eventOrganizerController.getGuests();
		
	    ObservableList<Guest> guestRegObs = FXCollections.observableArrayList(guests);
	    guestTable.setItems(guestRegObs);
	    
	    borderContainer.setCenter(guestTable);
	    borderContainer.setBottom(guestBtnBox);
	}
	
	public void viewEditForm() {
		editEventForm.getChildren().clear();
		
		editEventForm.add(eventNameLbl, 0, 0);
		editEventForm.add(eventNameTF, 1, 0);
		
		editEventForm.setAlignment(Pos.CENTER);
		
		messageLbl.setText("");
		
		btnEditDetailVB.getChildren().clear();
		btnEditDetailVB.getChildren().add(messageLbl);
		btnEditDetailVB.getChildren().add(confirmEditBtn);
		
		borderContainer.setCenter(editEventForm);
		borderContainer.setBottom(btnEditDetailVB);
	}
	
	public void viewCreateEventForm() {
		createEventForm.getChildren().clear();
		
		createEventForm.add(eventNameLbl, 0, 0);
		createEventForm.add(eventNameTF, 1, 0);
		
		createEventForm.add(eventDateLbl, 0, 2);
		createEventForm.add(eventDateDP, 1, 2);
		
		createEventForm.add(eventLocationLbl, 0, 3);
		createEventForm.add(eventLocationTF, 1, 3);
		
		createEventForm.add(eventDescLbl, 0, 4);
		createEventForm.add(eventDescTF, 1, 4);
		
		createEventForm.setAlignment(Pos.CENTER);
		
		messageLbl.setText("");
		
		btnCreateVB.getChildren().clear();
		btnCreateVB.getChildren().add(messageLbl);
		btnCreateVB.getChildren().add(saveEventBtn);
		
		borderContainer.setCenter(createEventForm);
		borderContainer.setBottom(btnCreateVB);
	}
	
	public void createEvent() {
	    String eventName = eventNameTF.getText();
	    LocalDate dateValue = eventDateDP.getValue();
	    String eventLocation = eventLocationTF.getText();
	    String eventDesc = eventDescTF.getText();
	    
	    if (dateValue == null) {
	        messageLbl.setText("Please select a valid date.");
	        return;
	    }
	    
	    String eventDate = dateValue.toString();

	    message = eventController.createEvent(eventName, eventDate, eventLocation, eventDesc, userID);
	    messageLbl.setText(message);
	    
	    if(!message.equals("Event created successfully!")) {
	    	return;
	    }
	    
	    refreshCreateEventTF();
	}
	
	public void editEventName(String eventID, String eventName) {
		String newEventName = eventNameTF.getText();

		if(eventOrganizerController.checkEditEventName(newEventName, tempEventName)) {
			message = eventOrganizerController.editEventName(eventID, newEventName);
			messageLbl.setTextFill(Color.GREEN);
			messageLbl.setText(message);
		} else {
			message = "Input is invalid!";
			messageLbl.setTextFill(Color.RED);
			messageLbl.setText(message);
		}
	}
	
	public void refreshCreateEventTF() {
		eventNameTF.setText("");
		eventDateDP.setValue(null);
		eventLocationTF.setText("");
		eventDescTF.setText("");
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}