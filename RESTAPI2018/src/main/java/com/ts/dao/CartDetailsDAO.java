package com.ts.dao;


import java.util.List;

import com.rest.dto.BuyerDetails;
import com.rest.dto.CartDetails;
import com.rest.dto.OrderProduct;
import com.rest.dto.ProductDetails;
import com.ts.db.HibernateTemplate;



public class CartDetailsDAO {
	
	public int addCartDetails(CartDetails cartDetails) {
		return HibernateTemplate.addObject(cartDetails);
	}
	
	public CartDetails getCartDetails(int cartId) {
		return (CartDetails)HibernateTemplate.getObject(CartDetails.class,cartId);
	}
	
	
	public List<CartDetails> getCartListByBuyer(BuyerDetails buyer) {
		List<CartDetails> cartList = (List)HibernateTemplate.getCartListByQuery(buyer);
		return cartList;
	}
	
	public int deleteCartItem(int cartId){
		return HibernateTemplate.deleteObject(CartDetails.class, cartId);
	}
	
	public int updateCart(int cartId, String cartStatus){
		return HibernateTemplate.updateCartDetails(cartId, cartStatus);
	}
	public List<CartDetails> checkCartItems(BuyerDetails buyer, ProductDetails product){
		List<CartDetails> cartList = (List)HibernateTemplate.checkCartListByQuery(buyer, product);
		return cartList;
	}

	
	
}