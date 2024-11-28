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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Event;
import model.Invitation;

public class VendorView extends Application implements EventHandler<ActionEvent>{
	private VendorController vendorController;
	
	Scene scene;
	BorderPane borderContainer;
	Button invitationBtn, eventBtn, acceptBtn;
	TableView<Invitation> invitationTable;
	TableView<Event> eventTable;
	Vector<Invitation> invitations;
	Vector<Event> events;
	HBox navbar;
	
	@Override
	public void start(Stage s) throws Exception {
		init();
		arrangeComponent();
		
		s.setTitle("Vendor");
		s.setScene(scene);
		s.show();
	}
	
	@Override
	public void handle(ActionEvent event) {
		
	}
	
	public void init() {
		borderContainer = new BorderPane();
		navbar = new HBox();
		invitationTable = new TableView<Invitation>();
		eventTable = new TableView<Event>();
		invitations = new Vector<Invitation>();
		events = new Vector<Event>();
		scene = new Scene(borderContainer, 1280, 720);
		
		invitationBtn = new Button("Invitation");
		invitationBtn.setOnAction(this);
		
		eventBtn = new Button("Event");
		eventBtn.setOnAction(this);
		
		acceptBtn = new Button("Accept");
		acceptBtn.setOnAction(this);
		
		TableColumn<Invitation, String> invitationIdCol = new TableColumn("ID");
		invitationIdCol.setCellValueFactory(new PropertyValueFactory<>("invitation_id"));
		invitationIdCol.setMinWidth(100);
		TableColumn<Invitation, String> eventCol = new TableColumn("Event");
		eventCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		eventCol.setMinWidth (100);
		TableColumn<Invitation, String> userCol = new TableColumn("User");
		userCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		userCol.setMinWidth(100);
		TableColumn<Invitation, String> statusCol = new TableColumn("Status");
		statusCol.setCellValueFactory (new PropertyValueFactory<>("invitation_status"));
		statusCol.setMinWidth(100);
		TableColumn<Invitation, String> roleCol = new TableColumn("Role");
		roleCol.setCellValueFactory (new PropertyValueFactory<>("invitation_role"));
		roleCol.setMinWidth(100);
		
		invitationTable.getColumns().addAll(invitationIdCol, eventCol, userCol, statusCol, roleCol);
		invitationTable.setMaxWidth(500);
		invitationTable.setMaxHeight(500);
//		invitationTable.setOnMouseClicked(tableMouseEvent());
		
		TableColumn<Event, String> eventIdCol = new TableColumn("ID");
		invitationIdCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		invitationIdCol.setMinWidth(100);
		TableColumn<Event, String> eventNameCol = new TableColumn("Event Name");
		eventNameCol.setCellValueFactory(new PropertyValueFactory<>("event_name"));
		eventNameCol.setMinWidth (100);
		TableColumn<Event, String> eventDateCol = new TableColumn("Event Date");
		eventDateCol.setCellValueFactory(new PropertyValueFactory<>("event_date"));
		eventDateCol.setMinWidth(100);
		TableColumn<Event, String> eventLocationCol = new TableColumn("Event Location");
		eventLocationCol.setCellValueFactory (new PropertyValueFactory<>("event_location"));
		eventLocationCol.setMinWidth(100);
		TableColumn<Event, String> organizerCol = new TableColumn("Organizer");
		roleCol.setCellValueFactory (new PropertyValueFactory<>("organizer_id"));
		roleCol.setMinWidth(100);
		
		eventTable.getColumns().addAll(eventIdCol, eventNameCol, eventDateCol, eventLocationCol, organizerCol);
		eventTable.setMaxWidth(500);
		eventTable.setMaxHeight(500);
//		eventTable.setOnMouseClicked(tableMouseEvent());
		
		refreshTable();
	}
	
	public void arrangeComponent() {
	    navbar.getChildren().addAll(invitationBtn, eventBtn);
	    navbar.setMargin(invitationBtn, new Insets(10));
	    navbar.setMargin(eventBtn, new Insets(10));
	    navbar.setAlignment(Pos.CENTER);

	    HBox bottomBox = new HBox();
	    bottomBox.getChildren().add(acceptBtn);
	    bottomBox.setAlignment(Pos.CENTER); 
	    bottomBox.setPadding(new Insets(10));

	    borderContainer.setTop(navbar);
	    borderContainer.setCenter(invitationTable);
	    borderContainer.setBottom(bottomBox);
	}

	
	public void refreshTable() {
	    ObservableList<Invitation> regInvObs = FXCollections.observableArrayList(invitations);
	    invitationTable.setItems(regInvObs);
	    
	    ObservableList<Event> regEvObs = FXCollections.observableArrayList(events);
	    eventTable.setItems(regEvObs);
	}
	
	public void acceptInvitation(String eventID) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public void manageVendor(String description, String product) {
		
	}
	
	public void checkManageVendorInput(String description, String product) {
		
	}
}
