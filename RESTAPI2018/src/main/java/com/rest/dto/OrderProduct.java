package com.rest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

//import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "orderId")

@Entity
public class OrderProduct {
	@Id @GeneratedValue
	private int orderId;
	private Address address;
	private Date orderDate;
	private double amount;
	//private String status;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="buyerId")
	@JsonIgnore
	private BuyerDetails buyerDetails;
	
	@OneToMany(mappedBy="orderProduct", fetch=FetchType.LAZY)
	private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
	
	
	public OrderProduct() {
		super();

	}

	

	public OrderProduct(int orderId, Address address, Date orderDate, double amount,
			BuyerDetails buyerDetails, List<OrderDetails> orderDetails) {
		super();
		this.orderId = orderId;
		this.address = address;
		this.orderDate = orderDate;
		this.amount = amount;
		//this.status = status;
		this.buyerDetails = buyerDetails;
		this.orderDetails = orderDetails;
	}



	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BuyerDetails getBuyerDetails() {
		return buyerDetails;
	}

	public void setBuyerDetails(BuyerDetails buyerDetails) {
		this.buyerDetails = buyerDetails;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}
	
	
	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}



	/*public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	*/
	
	
	
	
	
	
	
	
		
}