package view;

import java.util.Vector;

import controller.EventOrganizerController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import model.Event;
import model.Guest;
import model.Vendor;

public class EventOrganizerView extends Application implements EventHandler<ActionEvent>{
	private EventOrganizerController eventOrganizerController = new EventOrganizerController();
	private String userID;
	private Vector<Event> events;
	private Vector<Vendor> vendors;
	private String tempEventID;
	private String tempEventName;
	private String message;
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	GridPane eventDetailContainer, editEventForm;
	VBox vb, btnEditDetailVB;
	Menu navMenu;
	Menu profileMenu;
	MenuItem registerItem;
	MenuItem loginItem;
	MenuItem changeProfileItem;
	MenuItem eventItem;
	MenuBar navBar;
	TableView<Event> eventTable;
	TableView<Vendor> vendorTable;
	Label eventNameLbl, eventDateLbl, eventLocationLbl, eventDescLbl, guestAttendeesLbl, vendorAttendeesLbl,
		  eventName, eventDate, eventLocation, eventDesc, guestAttendees, vendorAttendees, messageLbl;
	Button addVendorBtn, editEventBtn, confirmEditBtn;
	TextField eventNameTF;
	
	@Override
	public void start(Stage stage) throws Exception {
		init();
		arrange();
		
		this.stage = stage;
		this.stage.setTitle("Event Organizer");
		this.stage.setScene(scene);
		this.stage.show();
	}

	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == eventItem) {
			viewOrganizedEvents(userID);
		} else if(e.getSource() == addVendorBtn) {
			viewAddVendors();
		} else if(e.getSource() == editEventBtn) {
			viewEditForm();
		} else if(e.getSource() == confirmEditBtn) {
			
			editEventName(tempEventID, tempEventName);
		}
	}
	
	private EventHandler<MouseEvent> vendorTableMouseEvent() {
		
		return null;
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
		btnEditDetailVB = new VBox();
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
		messageLbl = new Label();
		
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
		navBar = new MenuBar();
		eventTable = new TableView<Event>();
		
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
	}

	public void arrange() {
		navMenu.getItems().add(eventItem);
		profileMenu.getItems().add(registerItem);
		profileMenu.getItems().add(loginItem);
		profileMenu.getItems().add(changeProfileItem);
		navBar.getMenus().add(navMenu);
		navBar.getMenus().add(profileMenu);
		
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
		
		editEventForm.add(eventNameLbl, 0, 0);
		editEventForm.add(eventNameTF, 1, 0);
		
		editEventForm.setAlignment(Pos.CENTER);
		
		btnEditDetailVB.getChildren().clear();
		btnEditDetailVB.getChildren().add(addVendorBtn);
		btnEditDetailVB.setAlignment(Pos.CENTER);
		
		borderContainer.setTop(navBar);
		borderContainer.setBottom(btnEditDetailVB);
	}
	
	public void viewOrganizedEvents(String userID) {
		events.removeAllElements();
		events = eventOrganizerController.viewOrganizedEvents(userID);
		
	    ObservableList<Event> eventRegObs = FXCollections.observableArrayList(events);
	    eventTable.setItems(eventRegObs);
	    
	    borderContainer.setCenter(eventTable);
	}
	
	public void viewOrganizedEventDetails(String eventID) {
		Event tempEvent = eventOrganizerController.viewOrganizedEventDetails(eventID);
		
		guestAttendees.setText(tempEvent.getGuests().toString());
		vendorAttendees.setText(tempEvent.getVendors().toString());
		
		btnEditDetailVB.getChildren().clear();
		btnEditDetailVB.getChildren().add(editEventBtn);
		
		borderContainer.setCenter(eventDetailContainer);
		borderContainer.setBottom(btnEditDetailVB);
	}
	
	public void viewAddVendors() {
		vendors.removeAllElements();
		vendors = eventOrganizerController.getVendors();
		
	    ObservableList<Vendor> vendorRegObs = FXCollections.observableArrayList(vendors);
	    vendorTable.setItems(vendorRegObs);
	    
	    borderContainer.setCenter(vendorTable);
	}
	
	public void viewEditForm() {
		message = "";
		btnEditDetailVB.getChildren().clear();
		btnEditDetailVB.getChildren().add(messageLbl);
		btnEditDetailVB.getChildren().add(confirmEditBtn);
		
		borderContainer.setCenter(editEventForm);
		borderContainer.setBottom(btnEditDetailVB);
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

	public void setUserID(String userID) {
		this.userID = userID;
	}
}