package view;

import java.util.Vector;

import controller.GuestController;
import controller.InvitationController;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Event;

public class GuestView extends Application implements EventHandler<ActionEvent>{
	private GuestController guestController = new GuestController();
	private InvitationController invitationController = new InvitationController();
	private String email;
	private String userID;
	private String eventId;
	private String message;
	
	Stage s;
	Scene scene;
	BorderPane borderContainer;
	GridPane eventDetailContainer;
	Button invitationBtn, eventBtn, acceptBtn;
	TableView<Event> invitationTable;
	TableView<Event> eventTable;
	Vector<Event> invitations;
	Vector<Event> events;
	Label messageLbl, eventNameLbl, eventDateLbl, eventLocationLbl, eventDescLbl, eventDetailTitle,
		  eventName, eventDate, eventLocation, eventDesc,
		  productNameLbl, productDescLbl, manageVendorTitle;
    VBox invitationBottomBox, eventBottomBox, eventDetailBox, manageVendorBox;
	Menu navMenu;
	MenuItem invitationNav;
	MenuItem eventNav;
	MenuBar navBar;
	
	@Override
	public void start(Stage s) throws Exception {
		init();
		arrangeComponent();
		
		this.s = s;
		this.s.setTitle("Home Page");
		this.s.setResizable(false);
		this.s.setScene(scene);
		this.s.show();
	}
	
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == invitationNav) {
			initInvitation();
			arrangeInvitation();
		} else if(e.getSource() == eventNav) {
			initEvent();
			arrangeEvent();
			viewAcceptedEvents(email);
		} else if(e.getSource() == acceptBtn) {
			if(eventId == null) {
				message = "Please select an event!";
				messageLbl.setText(message);
				messageLbl.setTextFill(Color.RED);
				return;
			}
			
			acceptInvitation(getEventId(), getUserID(), "Guest");
		}
	}
	
	private EventHandler<MouseEvent> invitationTableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Event> invitationTbm = invitationTable.getSelectionModel();
				invitationTbm.setSelectionMode(SelectionMode.SINGLE);
				Event selectedEvent = invitationTbm.getSelectedItem();
				
	            if (selectedEvent != null) {
	                setEventId(selectedEvent.getEvent_id());
	            } else {
	                message = "Please select a valid event!";
	                messageLbl.setText(message);
	                messageLbl.setTextFill(Color.RED);
	            }
			}
		};
	}
	
	private EventHandler<MouseEvent> eventTableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Event> eventTableTbm = eventTable.getSelectionModel();
				eventTableTbm.setSelectionMode(SelectionMode.SINGLE);
				Event selectedEvent = eventTableTbm.getSelectedItem();
				
				if(selectedEvent == null) {
					return;
				}
				
				eventName.setText(selectedEvent.getEvent_name());
				eventDate.setText(selectedEvent.getEvent_date());
				eventLocation.setText(selectedEvent.getEvent_location());
				eventDesc.setText(selectedEvent.getEvent_description());
				
				initDetail();
			}
		};
	}
	
	public void init() {
		borderContainer = new BorderPane();
		scene = new Scene(borderContainer, 1280, 720);
		navMenu = new Menu("Menu");
		invitationNav = new MenuItem("Invitation");
		invitationNav.setOnAction(this);
		eventNav = new MenuItem("Event");
		eventNav.setOnAction(this);
		navBar = new MenuBar();
		
		borderContainer = new BorderPane();
		eventDetailContainer = new GridPane();
		invitationTable = new TableView<Event>();
		eventTable = new TableView<Event>();
		invitations = new Vector<Event>();
		events = new Vector<Event>();
		messageLbl = new Label("");
		scene = new Scene(borderContainer, 1280, 720);
		
		acceptBtn = new Button("Accept");
		acceptBtn.setOnAction(this);
		
		productNameLbl = new Label("Product Name: ");
		productDescLbl = new Label("Product Description: ");
		
		invitationBottomBox = new VBox();
		eventBottomBox = new VBox();
	}
	
	public void initInvitation() {
		invitationTable.getColumns().clear();
		
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
		invitationTable.setOnMouseClicked(invitationTableMouseEvent());
		refreshTable();
	}
	
	public void initEvent() {
		eventTable.getColumns().clear();
		 
		TableColumn<Event, String> acceptedEventIdCol = new TableColumn("ID");
		acceptedEventIdCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		acceptedEventIdCol.setMinWidth(100);
		TableColumn<Event, String> acceptedEventName = new TableColumn("Event Name");
		acceptedEventName.setCellValueFactory(new PropertyValueFactory<>("event_name"));
		acceptedEventName.setMinWidth (200);
		TableColumn<Event, String> acceptedEventDateCol = new TableColumn("Event Date");
		acceptedEventDateCol.setCellValueFactory(new PropertyValueFactory<>("event_date"));
		acceptedEventDateCol.setMinWidth(200);
		TableColumn<Event, String> acceptedEventLocationCol = new TableColumn("Event Location");
		acceptedEventLocationCol.setCellValueFactory (new PropertyValueFactory<>("event_location"));
		acceptedEventLocationCol.setMinWidth(300);
		TableColumn<Event, String> acceptedDescCol = new TableColumn("Description");
		acceptedDescCol.setCellValueFactory (new PropertyValueFactory<>("event_description"));
		acceptedDescCol.setMinWidth(500);
		TableColumn<Event, String> acceptedOrganizerCol = new TableColumn("Organizer");
		acceptedOrganizerCol.setCellValueFactory (new PropertyValueFactory<>("organizer_id"));
		acceptedOrganizerCol.setMinWidth(100);
		
		eventTable.getColumns().addAll(acceptedEventIdCol, acceptedEventName, acceptedEventDateCol, acceptedEventLocationCol, acceptedDescCol, acceptedOrganizerCol);
		eventTable.setOnMouseClicked(eventTableMouseEvent());
		refreshTable();
	}
	
	public void initDetail() {
		eventDetailContainer = new GridPane();
		eventDetailTitle = new Label("Event Detail");
		eventNameLbl = new Label("Event Name: ");
		eventName = new Label("");
		eventDateLbl = new Label("Event Date: ");
		eventDate = new Label("");
		eventLocationLbl = new Label("Event Location: ");
		eventLocation = new Label("");
		eventDescLbl = new Label("Event Description: ");
		eventDesc = new Label("");
		eventDetailBox = new VBox();
		
		borderContainer.setCenter(eventDetailBox);
		
	    eventDetailTitle.setFont(new Font("Verdana", 24));
	    eventDetailTitle.setStyle("-fx-font-weight: bold");
	    
	    eventNameLbl.setFont(new Font("Verdana", 16));
	    eventNameLbl.setStyle("-fx-font-weight: bold");
	    
	    eventDateLbl.setFont(new Font("Verdana", 16));
	    eventDateLbl.setStyle("-fx-font-weight: bold");
	    
	    eventLocationLbl.setFont(new Font("Verdana", 6));
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
	    
		eventDetailContainer.setAlignment(Pos.CENTER);
	    eventDetailBox.getChildren().add(eventDetailTitle);
	    eventDetailBox.setMargin(eventDetailTitle, new Insets(10, 10, 10, 10));
	    
	    eventDetailBox.getChildren().add(eventDetailContainer);
	    
	    eventDetailBox.setAlignment(Pos.CENTER);
	}
	
	public void arrangeComponent() {
		navMenu.getItems().add(invitationNav);
		navMenu.getItems().add(eventNav);
		
		navBar.getMenus().add(navMenu);
		
		borderContainer.setTop(navBar);
	}
	
	public void arrangeInvitation() {
		borderContainer.setCenter(invitationTable);

		invitationBottomBox.getChildren().add(messageLbl);
		invitationBottomBox.getChildren().add(acceptBtn);
		invitationBottomBox.setAlignment(Pos.CENTER);
		borderContainer.setBottom(invitationBottomBox);
	}
	
	public void arrangeEvent() {
		borderContainer.setCenter(eventTable);
		
		eventBottomBox.setAlignment(Pos.CENTER);
		borderContainer.setBottom(eventBottomBox);
	}
	
	private void refreshTable() {
		getInvitations(email);
	    ObservableList<Event> regInvObs = FXCollections.observableArrayList(invitations);
	    invitationTable.setItems(regInvObs);
	    
	    viewAcceptedEvents(email);
	    ObservableList<Event> regEvObs = FXCollections.observableArrayList(events);
	    eventTable.setItems(regEvObs);
	}
	
	public void acceptInvitation(String eventID, String userID, String invitationRole) {
		message = guestController.acceptInvitation(eventID, userID, invitationRole);
		messageLbl.setText(message);
		messageLbl.setTextFill(Color.GREEN);
		refreshTable();
	}
	
	public void getInvitations(String email) {
		invitations.removeAllElements();
		invitations = invitationController.getInvitations(email);
		
		borderContainer.setCenter(invitationTable);
		borderContainer.setBottom(invitationBottomBox);
	}
	
	public void viewAcceptedEvents(String email) {
		events.removeAllElements();
		events = guestController.viewAcceptedEvents(email);
		
		borderContainer.setCenter(eventTable);
		borderContainer.setBottom(eventBottomBox);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
}
