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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Event;
import model.Guest;
import model.Vendor;

public class EventOrganizerView extends Application{
	private EventOrganizerController eventOrganizerController = new EventOrganizerController();
	
	private Scene scene;
	private BorderPane bp, detailPage, vendorPage, guestPage, createEventPage, editEventPage;
	private GridPane gp, detailContainer;
	private HBox btnBox;
	private FlowPane fp;
	private TableView<Event> table;
	private TableView<Vendor> vendorTable;
	private TableView<Guest> guestTable;
	private Label titleLbl, detailTitleLbl, eventIdLbl, eventNameLbl, eventDateLbl, eventLocationLbl, eventDescriptionLbl, eventAttendeesLbl;
	private Label eventName, eventDate, eventLocation, eventDescription;
	
	private Vector<Event> events;
	private String tempUserId;
	private Button backBtn, addVendorBtn, addGuestBtn;
	
	@Override
	public void start(Stage s) throws Exception {
		initAllOrganizedEvents();
		arrangeOrganizedEvents();
        
		s.setTitle("EventOrganizer");
		s.setScene(scene);
		s.show();
		
		table.setOnMouseClicked(tableMouseEvent(s));
	
		backBtn.setOnAction(event -> {
			initAllOrganizedEvents();
			arrangeOrganizedEvents();
			scene = new Scene(bp, 1280, 720);
			s.setScene(scene);
		});
	}
	
	public void initOrganizedEventDetail() {
		backBtn = new Button("Back");
		detailContainer = new GridPane();
		detailPage = new BorderPane();
		detailTitleLbl = new Label("Event Detail");
		eventIdLbl = new Label("Event ID ");
		eventNameLbl = new Label("Event Name ");
		eventDateLbl = new Label("Event Date ");
		eventLocationLbl = new Label("Event Location:");
		eventDescriptionLbl = new Label("Event Description ");
		eventAttendeesLbl = new Label("Attendees ");
	}
	
	public void initAllOrganizedEvents() {
		backBtn = new Button("Back");
		addVendorBtn = new Button("Add Vendor");
		addGuestBtn = new Button("Add Guest");
		bp = new BorderPane();
		gp = new GridPane();
		fp = new FlowPane();
		scene = new Scene(bp, 1280, 720);
		table = new TableView<>();
		events = new Vector<Event>();
		titleLbl = new Label("Organized Events");
		btnBox = new HBox();
		
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
		
		refreshTable();
	}
	
	public void initVendor() {
		
	}
	
	public void arrangeOrganizedEvents() {
		btnBox.getChildren().add(addVendorBtn);
		btnBox.setMargin(addVendorBtn, new Insets(10, 10, 10 ,50));
		
		btnBox.getChildren().add(addGuestBtn);
		btnBox.setMargin(addGuestBtn, new Insets(10, 10, 10 , 50));
		
		btnBox.setAlignment(Pos.CENTER);
		
		bp.setTop(table);
		bp.setBottom(btnBox);
	} 
	
	public void arrangeOrganizedEventDetail() {
		detailPage.setPadding(new Insets(50));
		
		detailContainer.add(eventNameLbl, 0, 0);
		detailContainer.add(eventName, 1, 0);
		
		detailContainer.add(eventDateLbl, 0, 1);
		detailContainer.add(eventDate, 1, 1);
		
		detailContainer.add(eventLocationLbl, 0, 2);
		detailContainer.add(eventLocation, 1, 2);
		
		detailContainer.add(eventDescriptionLbl, 0, 3);
		detailContainer.add(eventDescription, 1, 3);
		
		detailContainer.add(eventAttendeesLbl, 0, 4);
	
		detailPage.setTop(detailTitleLbl);
		detailPage.setCenter(detailContainer);
		detailPage.setBottom(backBtn);
		
		// Top
		detailTitleLbl.setAlignment(Pos.CENTER);
		detailTitleLbl.setFont(new Font("Verdana", 24));
		detailTitleLbl.setStyle("-fx-font-weight: bold;");
		
		// Center
		detailContainer.setAlignment(Pos.CENTER);
		detailContainer.setPadding(new Insets(50));
		eventNameLbl.setMinWidth(100);
		eventDateLbl.setMinWidth(100);
		eventLocationLbl.setMinWidth(100);
		eventDescriptionLbl.setMinWidth(100);
		eventAttendeesLbl.setMinWidth(100);
		eventDescription.setMinWidth(50);
		
		detailContainer.setVgap(20);
		detailContainer.setHgap(20);
		
		eventNameLbl.setFont(new Font("Verdana", 16));
		eventDateLbl.setFont(new Font("Verdana", 16));
		eventLocationLbl.setFont(new Font("Verdana", 16));
		eventDescriptionLbl.setFont(new Font("Verdana", 16));
		eventAttendeesLbl.setFont(new Font("Verdana", 16));
		
		// Bottom
		backBtn.setAlignment(Pos.CENTER);
	}
	
	public EventHandler<MouseEvent> tableMouseEvent(Stage s) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Event> tbm = table.getSelectionModel();
				tbm.setSelectionMode(SelectionMode.SINGLE);
				Event ev = tbm.getSelectedItem();
				
			    if (ev == null) {
			    	return;
			    }
			    
			    eventName = new Label(ev.getEvent_name());
			    eventDate = new Label(ev.getEvent_date());
			    eventLocation = new Label(ev.getEvent_location());
			    eventDescription = new Label(ev.getEvent_description());

		        initOrganizedEventDetail();
		        arrangeOrganizedEventDetail();
		        
		        scene = new Scene(detailPage, 1280, 720);
		        		
		        s.setScene(scene);
			}
		};
	}
	
	public void refreshTable() {
		viewOrganizedEvents(getTempUserId());
		ObservableList<Event> regObs = FXCollections.observableArrayList(events);
		table.setItems(regObs);
	}
	
	public void createEvent(String eventName, String date, String location, String description, String organizerID) {
		
	}
	
	public void viewOrganizedEvents(String userID) {
		events.removeAllElements();
		
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
