package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Database;
import util.RandomIDGenerator;

/**
 * The Vendor class represents a user with the role of 'vendor' in the system.
 * It extends the User class and includes methods for managing vendor-specific functionalities
 * such as accepting event invitations, viewing accepted events, and managing vendor products.
 */
public class Vendor extends User {
    // Vector to store accepted event invitations by the vendor.
    private Vector<Event> accepted_invitations;
    
    // Singleton instance of the Database class to interact with the database.
    private static Database db = Database.getInstance();

    /**
     * Constructor for creating a Vendor object with the provided user details.
     * 
     * @param user_id       The unique ID of the user.
     * @param user_email    The email address of the user.
     * @param user_name     The name of the user.
     * @param user_password The password of the user.
     * @param user_role     The role of the user (vendor).
     */
    public Vendor(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role);
    }

    /**
     * Default constructor for Vendor. Initializes an empty Vendor object.
     */
    public Vendor() {
    }

    /**
     * This method accepts an event invitation for the vendor.
     * Currently, it is a placeholder method and needs implementation.
     * 
     * @param eventID The ID of the event to be accepted.
     */
    public void acceptInvitation(String eventID) {
        // Placeholder for accepting event invitation.
    }

    /**
     * This method allows the vendor to view the list of events they have accepted.
     * Currently, it is a placeholder method and needs implementation.
     * 
     * @param email The email of the vendor to retrieve their accepted events.
     */
    public void viewAcceptedEvents(String email) {
        // Placeholder for viewing accepted events by the vendor.
    }

    /**
     * This method allows the vendor to manage their products, including adding a new product 
     * to the database with a unique product ID.
     * 
     * @param description A description of the product.
     * @param product     The name of the product.
     * @return A message indicating the success or failure of managing the product.
     */
    public String manageVendor(String description, String product) {
        // Generate a unique product ID using the RandomIDGenerator.
        String productID = RandomIDGenerator.generateUniqueID();
        String message = "Manage Success!";  // Default success message.
        
        // SQL query to insert a new product into the product table.
        String query = "INSERT INTO product (product_id, product_name, product_description) VALUES (?, ?, ?)";
        
        // Prepare the SQL statement to interact with the database.
        PreparedStatement ps = db.prepareStatement(query);
        
        try { 
            ps.setString(1, productID);  // Set the generated product ID.
            ps.setString(2, product);    // Set the product name.
            ps.setString(3, description); // Set the product description.
            
            // Execute the query to insert the product into the database.
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error manage product!";  // In case of error, set failure message.
        }
        
        return message;  // Return the result message.
    }

    /**
     * This method checks the vendor's input for managing their product.
     * Currently, it is a placeholder method and needs implementation.
     * 
     * @param description A description of the product.
     * @param product     The name of the product.
     */
    public void checkManageVendorInput(String description, String product) {
        // Placeholder for checking vendor input before managing product.
    }

    /**
     * This method retrieves a list of vendors associated with a specific event ID.
     * It joins the attendees and users tables to get vendors who are attending the event.
     * 
     * @param eventID The ID of the event.
     * @return A vector of Vendor objects that are associated with the event.
     */
    public static Vector<Vendor> getVendorsByTransactionID(String eventID) {
        Vector<Vendor> vendors = new Vector<Vendor>();

        try {
            // SQL query to select vendors attending the specified event.
            String query = "SELECT u.user_id, u.user_email, u.user_name, u.user_role " +
                           "FROM attendees ea " +
                           "JOIN users u ON ea.attendee_id = u.user_id " +
                           "WHERE ea.event_id = ? AND u.user_role = 'vendor'";
            PreparedStatement ps = db.prepareStatement(query);
            ps.setString(1, eventID);  // Set the event ID parameter.
            ResultSet rs = ps.executeQuery();  // Execute the query to get vendors.

            // Process the result set and create Vendor objects.
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String email = rs.getString("user_email");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String role = rs.getString("user_role");

                vendors.add(new Vendor(userId, email, name, password, role));  // Add vendor to the list.
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exception.
        }
        
        return vendors;  // Return the list of vendors.
    }

    /**
     * This method retrieves all vendors from the database.
     * 
     * @return A vector of all Vendor objects in the system.
     */
    public Vector<Vendor> getVendors() {
        Vector<Vendor> vendors = new Vector<Vendor>();
        
        // SQL query to retrieve all vendors based on the 'vendor' role.
        String query = "SELECT * FROM users WHERE user_role = ?";
        PreparedStatement ps = db.prepareStatement(query);
        
        try {
            ps.setString(1, "Vendor");  // Set the user role parameter as 'Vendor'.
            ResultSet rs = ps.executeQuery();  // Execute the query to get all vendors.

            // Process the result set and create Vendor objects.
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String email = rs.getString("user_email");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String role = rs.getString("user_role");

                vendors.add(new Vendor(userId, email, name, password, role));  // Add vendor to the list.
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exception.
        }
        
        return vendors;  // Return the list of vendors.
    }
}
