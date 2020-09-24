package com.rest.dto;
import java.util.ArrayList;
import java.util.List;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "sellerId")
@Entity
public class SellerDetails {
	
	@Id @GeneratedValue
	private int sellerId;
	private String sellerName;
	private String sellerEmail;
	private String mobileNo;
	private String userName;
	private String password;
	private Address address;
	private String sellerImage;
	
	@OneToMany(mappedBy="sellerDetails", fetch=FetchType.LAZY)
	private List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
			
	public SellerDetails() {
		super();
	}
	
	

	public SellerDetails(int sellerId, String sellerName, String sellerEmail, String mobileNo, String userName,
			String password, Address address, String sellerImage) {
		super();
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerEmail = sellerEmail;
		this.mobileNo = mobileNo;
		this.userName = userName;
		this.password = password;
		this.address = address;
		this.sellerImage = sellerImage;
	}
	
	


	public String getSellerImage() {
		return sellerImage;
	}



	public void setSellerImage(String sellerImage) {
		this.sellerImage = sellerImage;
	}



	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
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


	public List<ProductDetails> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetails> productDetails) {
		this.productDetails = productDetails;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
	
	
	
	
}