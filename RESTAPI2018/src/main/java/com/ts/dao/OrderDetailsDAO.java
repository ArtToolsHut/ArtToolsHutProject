package com.ts.dao;

import java.util.List;

import com.rest.dto.BuyerDetails;
import com.rest.dto.CartDetails;
import com.rest.dto.OrderDetails;
import com.rest.dto.OrderProduct;
import com.rest.dto.ProductDetails;
import com.rest.dto.WishList;
import com.ts.db.HibernateTemplate;

public class OrderDetailsDAO {
	
	public int addOrderDetails(OrderDetails orderdetails) {
		return HibernateTemplate.addObject(orderdetails);
	}
	
	
	public OrderDetails getOrderDetailsByBuyerId(BuyerDetails buyer) {
		return (OrderDetails)HibernateTemplate.getObject(OrderDetails.class,buyer.getBuyerId());
	}
	public List<OrderDetails> getOrderIdListByQuery(OrderProduct order) {
		List<OrderDetails> productIdList = (List)HibernateTemplate.getProductIdListByQuery(order);
		return productIdList;
	}

	public OrderDetails getOrderById(int id) {
		return (OrderDetails)HibernateTemplate.getObject(OrderDetails.class,id);
	}
}