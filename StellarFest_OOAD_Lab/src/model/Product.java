package model;

public class Product {
	String productName;
	String productDescription;
	
	public Product(String productName, String productDescription) {
		super();
		this.productName = productName;
		this.productDescription = productDescription;
	}
	
	public Product() {
		
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
}
