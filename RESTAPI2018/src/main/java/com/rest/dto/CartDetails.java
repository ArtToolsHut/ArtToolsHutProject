package com.rest.dto;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
@Entity
public class CartDetails {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cartId;
	private int quantity;
	private String status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="buyerId", nullable=false)
	@JsonIgnore
	private BuyerDetails buyerDetails;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="productId",  nullable=false)
	@JsonIgnore
	private ProductDetails productDetails;

	
	public CartDetails() {
		super();
	}


	public CartDetails(int cartId, int quantity, BuyerDetails buyerDetails, String status,
			ProductDetails productDetails) {
		super();
		this.cartId = cartId;
		this.quantity = quantity;
		this.status = status;
		this.buyerDetails = buyerDetails;
		this.productDetails = productDetails;
		//this.orderDetails = orderDetails;
	}






	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public ProductDetails getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(ProductDetails productDetails) {
		this.productDetails = productDetails;
	}
	
	
	public BuyerDetails getBuyerDetails() {
		return buyerDetails;
	}

	public void setBuyerDetails(BuyerDetails buyerDetails) {
		this.buyerDetails = buyerDetails;
	}



	public int getCartId() {
		return cartId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	



/*	public OrderDetails getOrderDetails() {
		return orderDetails;
	}



	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	*/
	
}