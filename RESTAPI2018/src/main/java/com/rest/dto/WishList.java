package com.rest.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@XmlRootElement
public class WishList {
	@Id @GeneratedValue	
	private int wishListId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="buyerId", nullable=false)
	@JsonIgnore
	private BuyerDetails buyerDetails;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="productId", nullable=false)
	@JsonIgnore
	private ProductDetails productdetails;
	
	public WishList() {
		super();
	}

	public WishList(int wishListId, BuyerDetails buyerDetails, ProductDetails productDetails) {
		super();
		this.wishListId = wishListId;
		this.buyerDetails = buyerDetails;
		this.productdetails = productDetails;
	}

	public int getWishListId() {
		return wishListId;
	}

	public void setWishListId(int wishListId) {
		this.wishListId = wishListId;
	}

	public BuyerDetails getBuyerDetails() {
		return buyerDetails;
	}

	public void setBuyerDetails(BuyerDetails buyerDetails) {
		this.buyerDetails = buyerDetails;
	}

	public ProductDetails getProductDetails() {
		return productdetails;
	}

	public void setProductDetails(ProductDetails productDetails) {
		this.productdetails = productDetails;
	}
	
	
	
	
	
	

}