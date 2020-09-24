package com.ts.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.rest.dto.BuyerDetails;
import com.rest.dto.ProductDetails;
import com.rest.dto.SellerDetails;
import com.ts.db.HibernateTemplate;

public class SellerDetailsDAO {

	private SessionFactory factory = null;

	public SellerDetails getEmpByUserPass(String emailId,String password) {
		return (SellerDetails)HibernateTemplate.getSellerByUserPass(emailId,password);
	}

	public int register(SellerDetails sellerdetails) {
		return HibernateTemplate.addObject(sellerdetails);
	}
	public SellerDetails getSellerDetails(int id) {
		return (SellerDetails)HibernateTemplate.getObject(SellerDetails.class,id);
	}

	public List<SellerDetails> getAllSellerDetails() {
		List<SellerDetails> sellerdetails=(List)HibernateTemplate.getObjectListByQuery("From SellerDetails");
		System.out.println("Inside All SellerDetails ..."+ sellerdetails);
		return sellerdetails;	
	}
	public SellerDetails getSellerById(int sellerId) {
		return (SellerDetails)HibernateTemplate.getObject(SellerDetails.class,sellerId);
	}
	
	public int setSellerDetailsById(SellerDetails sellerdetails) {
		return HibernateTemplate.updateObject(sellerdetails);
	}
	
	public SellerDetails getSellerDetailsById(int id) {
		return (SellerDetails)HibernateTemplate.getObject(SellerDetails.class,id);
	}
	
	public int updateSellerDetails(SellerDetails sellerdetails) {
		return HibernateTemplate.updateObject(sellerdetails);
	}

}