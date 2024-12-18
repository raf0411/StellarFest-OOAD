package model;

/**
 * The Product class represents a product with a name and description.
 * It provides methods to get and set the product's name and description.
 */
public class Product {
    private String productName; // Name of the product
    private String productDescription; // Description of the product
    
    /**
     * Constructor to create a Product object with the specified name and description.
     * 
     * @param productName The name of the product
     * @param productDescription The description of the product
     */
    public Product(String productName, String productDescription) {
        this.productName = productName;
        this.productDescription = productDescription;
    }
    
    /**
     * Default constructor for the Product class.
     * This allows creating an empty Product object that can be populated later.
     */
    public Product() {
        
    }

    /**
     * Gets the name of the product.
     * 
     * @return The name of the product
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product.
     * 
     * @param productName The name of the product
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the description of the product.
     * 
     * @return The description of the product
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the description of the product.
     * 
     * @param productDescription The description of the product
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
