package view;

import java.util.Vector;

import controller.VendorController;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Event;
import model.Invitation;

public class VendorView extends Application implements EventHandler<ActionEvent>{
	private VendorController vendorController = new VendorController();
	private String email;
	private String eventId;
	
	Scene scene;
	BorderPane borderContainer;
	Button invitationBtn, eventBtn, acceptBtn;
	TableView<Event> invitationTable;
	TableView<Event> eventTable;
	Vector<Event> invitations;
	Vector<Event> events;
	HBox navbar;
	Label successMsg;
	
	@Override
	public void start(Stage s) throws Exception {
		init();
		arrangeComponent();
		
		s.setTitle("Vendor");
		s.setScene(scene);
		s.show();
	}
	
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == acceptBtn) {
			System.out.println(eventId);
			if(eventId == null) {
				System.out.println("Please select an event!");
				return;
			}
			
			acceptInvitation(getEventId());
		}
	}
	
	private EventHandler<MouseEvent> invitationTableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Event> invitationTbm = invitationTable.getSelectionModel();
				invitationTbm.setSelectionMode(SelectionMode.SINGLE);
				Event selectedEvent = invitationTbm.getSelectedItem();
				
				setEventId(selectedEvent.getEvent_id());
			}
		};
	}
	
	public void init() {
		borderContainer = new BorderPane();
		navbar = new HBox();
		invitationTable = new TableView<Event>();
		eventTable = new TableView<Event>();
		invitations = new Vector<Event>();
		events = new Vector<Event>();
		successMsg = new Label("");
		scene = new Scene(borderContainer, 1280, 720);
		
		invitationBtn = new Button("Invitation");
		invitationBtn.setOnAction(this);
		
		eventBtn = new Button("Event");
		eventBtn.setOnAction(this);
		
		acceptBtn = new Button("Accept");
		acceptBtn.setOnAction(this);
		
		TableColumn<Event, String> id = new TableColumn("Event ID");
		id.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		id.setMinWidth(50);
		TableColumn<Event, String> eventCol = new TableColumn("Event");
		eventCol.setCellValueFactory(new PropertyValueFactory<>("event_name"));
		eventCol.setMinWidth (200);
		TableColumn<Event, String> dateCol = new TableColumn("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("event_date"));
		dateCol.setMinWidth(200);
		TableColumn<Event, String> locationCol = new TableColumn("Location");
		locationCol.setCellValueFactory (new PropertyValueFactory<>("event_location"));
		locationCol.setMinWidth(200);
		TableColumn<Event, String> descCol = new TableColumn("Description");
		descCol.setCellValueFactory (new PropertyValueFactory<>("event_description"));
		descCol.setMinWidth(500);
		TableColumn<Event, String> organizerCol = new TableColumn("Organizer ID");
		organizerCol.setCellValueFactory (new PropertyValueFactory<>("organizer_id"));
		organizerCol.setMinWidth(50);
		
		invitationTable.getColumns().addAll(id, eventCol, dateCol, locationCol, descCol, organizerCol);
//		invitationTable.setOnMouseClicked(tableMouseEvent());
		
//		TableColumn<Event, String> eventIdCol = new TableColumn("ID");
//		eventIdCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));
//		eventIdCol.setMinWidth(300);
//		TableColumn<Event, String> eventNameCol = new TableColumn("Event Name");
//		eventNameCol.setCellValueFactory(new PropertyValueFactory<>("event_name"));
//		eventNameCol.setMinWidth (300);
//		TableColumn<Event, String> eventDateCol = new TableColumn("Event Date");
//		eventDateCol.setCellValueFactory(new PropertyValueFactory<>("event_date"));
//		eventDateCol.setMinWidth(300);
//		TableColumn<Event, String> eventLocationCol = new TableColumn("Event Location");
//		eventLocationCol.setCellValueFactory (new PropertyValueFactory<>("event_location"));
//		eventLocationCol.setMinWidth(300);
//		TableColumn<Event, String> organizerCol = new TableColumn("Organizer");
//		organizerCol.setCellValueFactory (new PropertyValueFactory<>("organizer_id"));
//		organizerCol.setMinWidth(300);
//		
//		eventTable.getColumns().addAll(eventIdCol, eventNameCol, eventDateCol, eventLocationCol, organizerCol);
//		eventTable.setOnMouseClicked(tableMouseEvent());
		
		invitationTable.setOnMouseClicked(invitationTableMouseEvent());
		refreshTable();
	}
	
	public void arrangeComponent() {
	    navbar.getChildren().addAll(invitationBtn, eventBtn);
	    navbar.setMargin(invitationBtn, new Insets(10));
	    navbar.setMargin(eventBtn, new Insets(10));
	    navbar.setAlignment(Pos.CENTER_LEFT);

	    HBox bottomBox = new HBox();
	    bottomBox.getChildren().add(acceptBtn);
	    bottomBox.setAlignment(Pos.CENTER); 
	    bottomBox.setPadding(new Insets(10));

	    borderContainer.setTop(navbar);
	    borderContainer.setCenter(invitationTable);
	    borderContainer.setBottom(bottomBox);
	}
	
	public void refreshTable() {
		getInvitations(email);
	    ObservableList<Event> regInvObs = FXCollections.observableArrayList(invitations);
	    invitationTable.setItems(regInvObs);
	    
	    ObservableList<Event> regEvObs = FXCollections.observableArrayList(events);
	    eventTable.setItems(regEvObs);
	}
	
	public void getInvitations(String email) {
		invitations.removeAllElements();
		invitations = vendorController.getInvitations(email);
	}
	
	public void acceptInvitation(String eventID) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public void manageVendor(String description, String product) {
		
	}
	
	public void checkManageVendorInput(String description, String product) {
		
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
}
