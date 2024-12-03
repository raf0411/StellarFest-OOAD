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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Event;
import model.Guest;
import model.Vendor;

public class EventOrganizerView extends Application implements EventHandler<ActionEvent>{
	private EventOrganizerController eventOrganizerController = new EventOrganizerController();
	private String userID;
	private Vector<Event> events;
	
	Stage stage;
	Scene scene;
	BorderPane borderContainer;
	GridPane eventDetailContainer;
	VBox vb;
	Menu navMenu;
	MenuItem registerItem;
	MenuItem loginItem;
	MenuItem changeProfileItem;
	MenuItem eventItem;
	MenuBar navBar;
	TableView<Event> eventTable;
	Label eventNameLbl, eventDateLbl, eventLocationLbl, eventDescLbl, guestAttendeesLbl, vendorAttendeesLbl,
		  eventName, eventDate, eventLocation, eventDesc, guestAttendees, vendorAttendees;
	
	
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
		}
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
				
				eventName.setText(selectedEvent.getEvent_name());
				eventDate.setText(selectedEvent.getEvent_date());
				eventLocation.setText(selectedEvent.getEvent_location());
				eventDesc.setText(selectedEvent.getEvent_description());
			}
		};
	}
	
	public void init() {
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
		
		events = new Vector<Event>();
		stage = new Stage();
		borderContainer = new BorderPane();
		vb = new VBox();
		scene = new Scene(borderContainer, 1280, 720);
		
		navMenu = new Menu("Menu");
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
	}

	public void arrange() {
		navMenu.getItems().add(registerItem);
		navMenu.getItems().add(loginItem);
		navMenu.getItems().add(changeProfileItem);
		navMenu.getItems().add(eventItem);
		navBar.getMenus().add(navMenu);
		
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
		
		borderContainer.setTop(navBar);
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
		
		borderContainer.setCenter(eventDetailContainer);
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}