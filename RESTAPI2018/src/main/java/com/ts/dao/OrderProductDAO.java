package com.ts.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.rest.dto.BuyerDetails;
import com.rest.dto.OrderProduct;
import com.rest.dto.ProductDetails;
import com.ts.db.HibernateTemplate;

public class OrderProductDAO {

	private SessionFactory factory = null;
	
	
	public int addorderproduct(OrderProduct orderproduct) { 
		return HibernateTemplate.addObject(orderproduct);
	}
	
	public OrderProduct getOrderProduct(int id) {
		return (OrderProduct)HibernateTemplate.getObject(OrderProduct.class,id);
	}
	
	
	public List<OrderProduct> getOrderListByBuyer(BuyerDetails buyer) {
		List<OrderProduct> orderList = (List)HibernateTemplate.getOrderListByQuery(buyer);
		return orderList;
	}
	
	public List<OrderProduct> getAllOrderProduct() {
		List<OrderProduct> orderproduct=(List)HibernateTemplate.getObjectListByQuery("From OrderProduct");
		System.out.println("Inside All OrderProduct ..."+ orderproduct);
		return orderproduct;	
	}
	
	
	public List<OrderProduct> getOrderIdListByQuery(BuyerDetails buyer) {
		List<OrderProduct> orderIdList = (List)HibernateTemplate.getOrderIdListByQuery(buyer);
		return orderIdList;
	}
	
	/*public int updateOrder(int orderId, String orderStatus){
		return HibernateTemplate.updateOrderProduct(orderId, orderStatus);
	}*/
	
//	public ProductDetails getProductDetailsByType(String type) {
	//	return (ProductDetails)HibernateTemplate.getObject(ProductDetails.class,type);
//	}
	
//	public ProductDetails getProductDetailsByBrand(String brand) {
	//	return (ProductDetails)HibernateTemplate.getObject(ProductDetails.class,brand);
	//}
	
	/*
	 
	public Employee getEmpByEmail(String email) {
		return (Employee)HibernateTemplate.getObjectByEmail(email);
	}
	 */
	
	
}