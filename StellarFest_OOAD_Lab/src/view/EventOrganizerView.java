package view;

import java.util.Vector;

import controller.EventOrganizerController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Event;

public class EventOrganizerView extends Application{
	private EventOrganizerController eventOrganizerController = new EventOrganizerController();
	
	private Scene scene;
	private BorderPane bp;
	private GridPane gp;
	private FlowPane fp;
	private TableView<Event> table;
	private Label titleLbl, eventIdLbl, eventNameLbl, eventDateLbl, eventLocationLbl, eventDescriptionLbl;
	private Vector<Event> events;
	private String tempUserId;
	
	@Override
	public void start(Stage s) throws Exception {
		init();
		initAllEvents();
        arrangeComponent();
        
		s.setTitle("EventOrganizer");
		s.setScene(scene);
		s.show();
	}
	
	public void initEventDetail() {
		
	}
	
	public void initAllEvents() {
		TableColumn<Event, String> eventIdCol = new TableColumn("Event ID");
		eventIdCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		eventIdCol.setMinWidth(100);
		
		TableColumn<Event, String> eventNameCol = new TableColumn("Event Name");
		eventNameCol.setCellValueFactory(new PropertyValueFactory<>("event_name"));
		eventNameCol.setMinWidth(300);
		
		TableColumn<Event, String> eventDateCol = new TableColumn("Event Date");
		eventDateCol.setCellValueFactory(new PropertyValueFactory<>("event_date"));
		eventDateCol.setMinWidth(300);
		
		TableColumn<Event, String> eventLocationCol = new TableColumn("Event Location");
		eventLocationCol.setCellValueFactory(new PropertyValueFactory<>("event_location"));
		eventLocationCol.setMinWidth(300);
		
		table.getColumns().addAll(eventIdCol, eventNameCol, eventDateCol, eventLocationCol);
	    
		table.setOnMouseClicked(tableMouseEvent());
		
		refreshTable();
	}
	
	public void init() {
		bp = new BorderPane();
		gp = new GridPane();
		fp = new FlowPane();
		scene = new Scene(bp, 1280, 720);
		table = new TableView<>();
		events = new Vector<Event>();
		titleLbl = new Label("Organized Events");
		eventIdLbl = new Label("Event ID");
		eventNameLbl = new Label("Event Name");
		eventDateLbl = new Label("Event Date");
		eventLocationLbl = new Label("Event Location");
		eventDescriptionLbl = new Label("Event Description");
	}
	
	public void arrangeComponent() {
		bp.setTop(table);
	}
	
	public EventHandler<MouseEvent> tableMouseEvent() {
		return null;
	}
	
	public void refreshTable() {
		viewOrganizedEvents(getTempUserId());
		ObservableList<Event> regObs = FXCollections.observableArrayList(events);
		table.setItems(regObs);
	}
	
	public void createEvent(String eventName, String date, String location, String description, String organizerID) {
		
	}
	
	public void viewOrganizedEvents(String userID) {
//		events.removeAllElements();
		if(events == null) {
			events = new Vector<Event>();
		}
		
		events = eventOrganizerController.viewOrganizedEvents(userID);
	}
	
	public void viewOrganizedEventDetails(String eventID) {
		
	}
	
	public void getGuests() {
		
	}
	
	public void getVendors() {
		
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
	
	public void checkCreateEventInput(String eventName, String date, String location, String description) {
		
	}
	
	public void checkAddVendorInput(String vendorID) {
		
	}
	
	public void checkAddGuestInput(String vendorID) {
		
	}
	
	public void editEventName(String eventID, String eventName) {
		
	}

	public String getTempUserId() {
		return tempUserId;
	}

	public void setTempUserId(String tempUserId) {
		this.tempUserId = tempUserId;
	}
}
