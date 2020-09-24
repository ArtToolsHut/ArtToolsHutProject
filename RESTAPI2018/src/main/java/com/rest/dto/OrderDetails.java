package com.rest.dto;

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

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement
@Entity
public class OrderDetails {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orderId", nullable=false)
	@JsonIgnore
	private OrderProduct orderProduct;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name= "productId", nullable=false)
	@JsonIgnore
	private ProductDetails productdetails;

	private int quantity ;
	private double price;
	private double subTotal;
	
	
	public OrderDetails() {
		super();
	}
	

	public OrderDetails(int id, OrderProduct orderProduct, ProductDetails productdetails, int quantity, double price,
			double subTotal) {
		super();
		this.id = id;
		this.orderProduct = orderProduct;
		this.productdetails = productdetails;
		this.quantity = quantity;
		this.price = price;
		this.subTotal = subTotal;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public OrderProduct getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}

	/*public CartDetails getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(CartDetails cartDetails) {
		this.cartDetails = cartDetails;
	}
*/


	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}

	public ProductDetails getProductdetails() {
		return productdetails;
	}


	public void setProductdetails(ProductDetails productdetails) {
		this.productdetails = productdetails;
	}



	
	
	
	
}