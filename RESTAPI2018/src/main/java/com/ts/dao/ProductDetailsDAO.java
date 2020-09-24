package com.ts.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.rest.dto.Employee;
import com.rest.dto.OrderDetails;
import com.rest.dto.ProductDetails;
import com.rest.dto.SellerDetails;
import com.ts.db.HibernateTemplate;

public class ProductDetailsDAO {

	private SessionFactory factory = null;
	
	

	public int addproductdetails(ProductDetails productdetails) {
		//java.util.Date utilDate = new java.sql.Date(productdetails.getJoinDate().getTime()); 
		return HibernateTemplate.addObject(productdetails);
	}
	

	//ALL PRODUCTS
		
		//GET PRODUCTS BY NAME

	public ProductDetails getProductByName(String productName) {
		return (ProductDetails)HibernateTemplate.checkproductName(productName);
	}
		
		
		//GET PRODUCTS BY TYPE
		public List<ProductDetails> getAllProductsByType(String productType) {
			List<ProductDetails> productdetails=(List)HibernateTemplate.getObjectListByName(ProductDetails.class,"productType", productType);
			//System.out.println("Inside All ProductDetails ..."+ productdetails);
			return productdetails;	
		}
		public List<ProductDetails> getAllProductsByBrand(String productBrand) {
			List<ProductDetails> productdetails=(List)HibernateTemplate.getObjectListByName(ProductDetails.class,"productBrand", productBrand);
			//System.out.println("Inside All ProductDetails ..."+ productdetails);
			return productdetails;	
		}
		public List<ProductDetails> getAllProductDetails() {
			List<ProductDetails> productdetails=(List)HibernateTemplate.getObjectListByQuery("From ProductDetails");
			System.out.println("Inside All ProductDetails ..."+ productdetails);
			return productdetails;	
		}


		public ProductDetails getProductById(int productId) {
			return (ProductDetails)HibernateTemplate.getObject(ProductDetails.class,productId);
		}
		
		public List<ProductDetails> productsBySellerId(SellerDetails seller) {
			List<ProductDetails> productdetails = (List)HibernateTemplate.getProductListByQuery(seller);
			return productdetails;	
		}
		
		public List<ProductDetails> getAllPendingProducts() {
			List<ProductDetails> productdetails=(List)HibernateTemplate.getObjectListByName(ProductDetails.class,"status", "Pending");
			//System.out.println("Inside All ProductDetails ..."+ productdetails);
			return productdetails;	
		}


		public int updateProductStatus(ProductDetails product) {
			return HibernateTemplate.updateObject(product);
		}


		public int updateStatus(int productId, String productStatus) {
			return HibernateTemplate.updateProductStatus(productId, productStatus);
		}
		
		public List<ProductDetails> getAllProductNames() {
			List<ProductDetails> productdetails=(List)HibernateTemplate.getObjectListByQuery("select productName From ProductDetails");
			System.out.println("Inside All ProductDetails ..."+ productdetails);
			return productdetails;	
		}
		
		public int updateQuantity(int productId, int quantity){
			return HibernateTemplate.updateQuantity(productId, quantity);
		}
		
}