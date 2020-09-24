package com.ts.dao;

import java.util.List;

import com.rest.dto.BuyerDetails;
import com.rest.dto.CartDetails;
import com.rest.dto.ProductDetails;
import com.rest.dto.WishList;
import com.ts.db.HibernateTemplate;

public class WishListDAO {
	public int register(WishList wishlist) {
		return HibernateTemplate.addObject(wishlist);
	}
	
	public List<WishList> getWishListByBuyer(BuyerDetails buyer) {
		List<WishList> wishList = (List)HibernateTemplate.getWishListByQuery(buyer);
		return wishList;
	}
	
	
	public int deleteWishListItem(int wishListId){
		return HibernateTemplate.deleteObject(WishList.class, wishListId);
	}
	public List<WishList> checkWishList(BuyerDetails buyer, ProductDetails product){
		List<WishList> wishlist = (List)HibernateTemplate.checkWishListByQuery(buyer, product);
		return wishlist;
	}
}