package view;

import java.util.Vector;

import controller.InvitationController;
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
import model.Invitation;

public class VendorView extends Application implements EventHandler<ActionEvent>{
	private VendorController vendorController = new VendorController();
	private InvitationController invitationController = new InvitationController();
	
	private String email;
	private String userId;
	private String eventId;
	private String message;
	private String oldPassword;
	
	Menu navMenu, profileMenu;
	MenuItem invitationNav, eventNav, manageVendorNav,
			 registerNav, loginNav, changeProfileNav;
	MenuBar navBar;
	
	Scene scene;
	BorderPane borderContainer;
	GridPane eventDetailContainer, manageVendorContainer;
	Button acceptBtn, manageVendorBtn, saveBtn;
	TableView<Event> invitationTable;
	TableView<Event> eventTable;
	Vector<Event> invitations;
	Vector<Event> events;
	Label messageLbl, eventNameLbl, eventDateLbl, eventLocationLbl, eventDescLbl, eventDetailTitle,
		  eventName, eventDate, eventLocation, eventDesc,
		  productNameLbl, productDescLbl, manageVendorTitle;
    VBox invitationBottomBox, eventBottomBox, eventDetailBox, manageVendorBox;
    TextField productNameTF, productDescTF;
    
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

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
			if(eventId == null) {
				message = "Please select an event!";
				messageLbl.setText(message);
				messageLbl.setTextFill(Color.RED);
				return;
			}
			
			acceptInvitation(getEventId(), getUserId(), "Vendor");
			
		} else if(e.getSource() == invitationNav) {
			getInvitations(email);
			
		} else if(e.getSource() == eventNav) {
			viewAcceptedEvents(email);
			
		} else if(e.getSource() == manageVendorNav) {
			initManageVendor();
		} else if(e.getSource() == saveBtn) {
			productNameTF.setText("");
			productDescTF.setText("");
			
			String productName = productNameTF.getText();
			String productDesc = productDescTF.getText();
			manageVendor(productDesc, productName);
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
				
				borderContainer.setCenter(eventDetailBox);
			}
		};
	}
	
	public void init() {
		navMenu = new Menu("Menu");
		invitationNav = new MenuItem("Invitation");
		invitationNav.setOnAction(this);
		eventNav = new MenuItem("Event");
		eventNav.setOnAction(this);
		manageVendorNav = new MenuItem("Manage Vendor");
		manageVendorNav.setOnAction(this);
        profileMenu = new Menu("Profile");
        registerNav = new MenuItem("Register");
        registerNav.setOnAction(this);
        loginNav = new MenuItem("Login");
        loginNav.setOnAction(this);
        changeProfileNav = new MenuItem("Change Profile");
        changeProfileNav.setOnAction(this);
		navBar = new MenuBar();
		
		borderContainer = new BorderPane();
		eventDetailContainer = new GridPane();
		invitationTable = new TableView<Event>();
		eventTable = new TableView<Event>();
		invitations = new Vector<Event>();
		events = new Vector<Event>();
		messageLbl = new Label("");
		scene = new Scene(borderContainer, 1280, 720);
		eventDetailBox = new VBox();
		
		acceptBtn = new Button("Accept");
		acceptBtn.setOnAction(this);
		
		manageVendorBtn = new Button("Manage Vendor");
		manageVendorBtn.setOnAction(this);
		
		productNameTF = new TextField();
		productDescTF = new TextField();
		productNameLbl = new Label("Product Name: ");
		productDescLbl = new Label("Product Description: ");
		
		invitationBottomBox = new VBox();
		eventBottomBox = new VBox();
		
		eventDetailTitle = new Label("Event Detail");
		eventNameLbl = new Label("Event Name: ");
		eventName = new Label("");
		eventDateLbl = new Label("Event Date: ");
		eventDate = new Label("");
		eventLocationLbl = new Label("Event Location: ");
		eventLocation = new Label("");
		eventDescLbl = new Label("Event Description: ");
		eventDesc = new Label("");
		
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
		
		invitationTable.setOnMouseClicked(invitationTableMouseEvent());
		eventTable.setOnMouseClicked(eventTableMouseEvent());
		refreshTable();
	}
	
	public void initManageVendor() {
	    messageLbl.setText("");

	    manageVendorContainer = new GridPane();
	    manageVendorTitle = new Label("Manage Product");
	    manageVendorTitle.setFont(new Font("Verdana", 24));
	    manageVendorTitle.setStyle("-fx-font-weight: bold");

	    saveBtn = new Button("Save Changes");
	    saveBtn.setOnAction(this);
	    manageVendorBox = new VBox();

	    manageVendorContainer.add(productNameLbl, 0, 0);
	    manageVendorContainer.add(productNameTF, 1, 0);

	    manageVendorContainer.add(productDescLbl, 0, 1);
	    manageVendorContainer.add(productDescTF, 1, 1);

	    manageVendorContainer.setAlignment(Pos.CENTER);

	    manageVendorBox.getChildren().add(manageVendorTitle);
	    manageVendorBox.getChildren().add(manageVendorContainer);
	    manageVendorBox.setMargin(manageVendorTitle, new Insets(10, 10, 10, 10));

	    manageVendorBox.setAlignment(Pos.CENTER);

	    VBox bottomBox = new VBox();

	    bottomBox.getChildren().add(messageLbl);
	    bottomBox.getChildren().add(saveBtn);
	    bottomBox.setAlignment(Pos.CENTER);

	    bottomBox.setMargin(saveBtn, new Insets(10, 10, 10, 10));

	    borderContainer.setCenter(manageVendorBox);
	    borderContainer.setBottom(bottomBox);
	}
	
	public void arrangeComponent() {
		navMenu.getItems().add(invitationNav);
		navMenu.getItems().add(eventNav);
		navMenu.getItems().add(manageVendorNav);
		profileMenu.getItems().add(registerNav);
		profileMenu.getItems().add(loginNav);
		profileMenu.getItems().add(changeProfileNav);
		
		navBar.getMenus().add(navMenu);
		navBar.getMenus().add(profileMenu);
		
	    invitationBottomBox.getChildren().add(messageLbl);
	    invitationBottomBox.getChildren().add(acceptBtn);
	    invitationBottomBox.setAlignment(Pos.CENTER);
	    invitationBottomBox.setPadding(new Insets(10));

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
	    
		eventDetailContainer.setAlignment(Pos.CENTER);
	    eventDetailBox.getChildren().add(eventDetailTitle);
	    eventDetailBox.setMargin(eventDetailTitle, new Insets(10, 10, 10, 10));
	    
	    eventDetailBox.getChildren().add(eventDetailContainer);
	    eventDetailBox.setAlignment(Pos.CENTER);
	}
	
	public void refreshTable() {
		getInvitations(email);
	    ObservableList<Event> regInvObs = FXCollections.observableArrayList(invitations);
	    invitationTable.setItems(regInvObs);
	    
	    viewAcceptedEvents(email);
	    ObservableList<Event> regEvObs = FXCollections.observableArrayList(events);
	    eventTable.setItems(regEvObs);
	}
	
	public void getInvitations(String email) {
		invitations.removeAllElements();
		invitations = invitationController.getInvitations(email);
		
		borderContainer.setCenter(invitationTable);
		borderContainer.setBottom(invitationBottomBox);
	}
	
	public void acceptInvitation(String eventID, String userID, String invitationRole) {
		message = vendorController.acceptInvitation(eventID, userID, invitationRole);
		messageLbl.setText(message);
		messageLbl.setTextFill(Color.GREEN);
		refreshTable();
	}
	
	public void viewAcceptedEvents(String email) {
		events.removeAllElements();
		events = vendorController.viewAcceptedEvents(email);
		
		borderContainer.setCenter(eventTable);
		borderContainer.setBottom(eventBottomBox);
	}
	
	public void manageVendor(String description, String product) {
		if(vendorController.checkManageVendorInput(description, product)) { 
			message = vendorController.manageVendor(description, product);
			messageLbl.setTextFill(Color.GREEN);
			messageLbl.setText(message);
			return;
		}
		
		message = "Input product invalid!";
		messageLbl.setTextFill(Color.RED);
		messageLbl.setText(message);
		return;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
