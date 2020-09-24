package com.ts.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.rest.dto.BuyerDetails;
import com.rest.dto.SellerDetails;
//import com.rest.dto.Employee;
import com.ts.db.HibernateTemplate;

public class BuyerDetailsDAO {

	private SessionFactory factory = null;

	
	public BuyerDetails getBuyerById(int buyerId) {
		return (BuyerDetails)HibernateTemplate.getObject(BuyerDetails.class,buyerId);
	}
	public BuyerDetails getBuyerDetails(String name) {
		return (BuyerDetails)HibernateTemplate.getObject(BuyerDetails.class,name);
	}
	public BuyerDetails getBuyerDetailsById(int id) {
		return (BuyerDetails)HibernateTemplate.getObject(BuyerDetails.class,id);
	}
	public int setBuyerDetailsById(BuyerDetails buyerdetails) {
		return HibernateTemplate.updateObject(buyerdetails);
	}
	public int updateBuyerDetails(BuyerDetails buyerdetails) {
		return HibernateTemplate.updateObject(buyerdetails);
	}

	public int register(BuyerDetails buyerdetails) {
		return HibernateTemplate.addObject(buyerdetails);
	}
	public BuyerDetails getBuyerByUserPass(String emailId, String password) {
		return (BuyerDetails)HibernateTemplate.getObjectByUserPass(emailId, password);
}
	public List<BuyerDetails> getAllBuyerDetails() {
		List<BuyerDetails> buyerdetails=(List)HibernateTemplate.getObjectListByQuery("From BuyerDetails");
		System.out.println("Inside All BuyerDetails ..."+ buyerdetails);
		return buyerdetails;
	}
	
	public BuyerDetails checkBuyerEmail(String buyerEmail) {
		return (BuyerDetails)HibernateTemplate.checkEmail(buyerEmail);
	}
	public BuyerDetails checkBuyerNum(String mobileNo) {
		return (BuyerDetails)HibernateTemplate.checkNumber(mobileNo);
	}
}