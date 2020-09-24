package com.rest.dto;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	private String street;
	private String city;
	private String state;
	private String country;
	private int pincode;
	
	public Address() {
		super();
		
	}
	
	
	public Address(String street, String city, String state, String country, int pincode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
	}


	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}
	

	public void setPinCode(int pincode) {
		this.pincode = pincode;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	

}