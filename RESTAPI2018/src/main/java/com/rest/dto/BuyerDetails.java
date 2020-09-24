package com.rest.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@XmlRootElement
@Entity

public class BuyerDetails {
	@Id @GeneratedValue	
	private int buyerId;
	private String buyerName;
	private String buyerEmail;
	private String mobileNo;
	private String userName;
	private String password;
	private String buyerImage;
	//private Address address;
	
	public BuyerDetails() {
		super();
	}
	
	
	public BuyerDetails(int buyerId, String buyerName, String buyerEmail, String mobileNo, String userName,
			String password, Address address,String buyerImage) {
		super();
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.buyerEmail = buyerEmail;
		this.mobileNo = mobileNo;
		this.userName = userName;
		this.password = password;
		this.buyerImage = buyerImage;
		//this.address = address;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public String getBuyerImage() {
		return buyerImage;
	}


	public void setBuyerImage(String buyerImage) {
		this.buyerImage = buyerImage;
	}



}