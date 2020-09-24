package com.rest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@XmlRootElement
@Entity
public class ProductDetails {
	@Id @GeneratedValue 
	private int productId;
	private String productName;
	private String imageName;
	private String productType;
	private String productBrand;
	private double productPrice;
	private int availability;
	private String description;
	//
	private String status;
	//
		
	

	@ManyToOne 
	@JoinColumn(name="sellerId")
	private SellerDetails sellerDetails;
	
	@OneToMany(mappedBy="productDetails")
	private List<CartDetails> cartDetails;
	
	@OneToMany(mappedBy="productdetails")
	private List<OrderDetails> orderDetails;
	
	
	public ProductDetails() {
		super();
	}


	public ProductDetails(int productId, String productName, String imageName, String productType, String productBrand,
			double productPrice, int availability, String description, SellerDetails sellerDetails, String status,
			List<CartDetails> cartDetails) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.imageName = imageName;
		this.productType = productType;
		this.productBrand = productBrand;
		this.productPrice = productPrice;
		this.availability = availability;
		this.description = description;
		this.sellerDetails = sellerDetails;
		this.cartDetails = cartDetails;
		this.status = status;
	}


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public SellerDetails getSellerDetails() {
		return sellerDetails;
	}

	public void setSellerDetails(SellerDetails sellerDetails) {
		this.sellerDetails = sellerDetails;
	}

	public List<CartDetails> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<CartDetails> cartDetails) {
		this.cartDetails = cartDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public String getImageName() {
		return imageName;
	}


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}