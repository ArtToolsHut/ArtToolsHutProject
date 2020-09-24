package com.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import com.rest.dto.Address;
import com.rest.dto.BuyerDetails;
import com.rest.dto.CartDetails;
import com.rest.dto.Department;
import com.rest.dto.OrderDetails;
import com.rest.dto.OrderProduct;
import com.rest.dto.ProductDetails;
import com.rest.dto.SellerDetails;
import com.rest.dto.WishList;
import com.ts.dao.BuyerDetailsDAO;
import com.ts.dao.CartDetailsDAO;
import com.ts.dao.DeptDAO;
import com.ts.dao.OrderDetailsDAO;
import com.ts.dao.OrderProductDAO;
import com.ts.dao.PasswordEncrypDecrpt;
import com.ts.dao.ProductDetailsDAO;
import com.ts.dao.SellerDetailsDAO;
import com.ts.dao.WishListDAO;

@Path("myresource")
public class MyResource {

	@GET
	@Produces
	public String getIt(){
		return "Got It!";
	}



	@Path("registerBuyer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void resisterBuyer(BuyerDetails buyerdetails) throws Exception {
		System.out.println("Data Recieved in Register : " + buyerdetails); 
		
		String password = buyerdetails.getPassword();
		PasswordEncrypDecrpt pswrdencrypt = new PasswordEncrypDecrpt();
		String encryptedPswrd = pswrdencrypt.encrypt(password);
		buyerdetails.setPassword(encryptedPswrd);
		System.out.println(encryptedPswrd);
		
		//buyerdetails.setPassword(null);
		BuyerDetailsDAO buyerdetailsDao = new BuyerDetailsDAO();
		buyerdetailsDao.register(buyerdetails); 
	}
	
	@Path("updateBuyerDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateBuyerDetails(BuyerDetails buyerdetails) throws Exception {
		System.out.println("Data Recieved in Register : " + buyerdetails); 
		BuyerDetailsDAO buyerdetailsDao = new BuyerDetailsDAO();
		
		String password = buyerdetails.getPassword();
		PasswordEncrypDecrpt pswrdencrypt = new PasswordEncrypDecrpt();
		String encryptedPswrd = pswrdencrypt.encrypt(password);
		buyerdetails.setPassword(encryptedPswrd);
		
		buyerdetailsDao.updateBuyerDetails(buyerdetails);
	}

	@Path("updateSellerDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateSellerDetails(SellerDetails sellerdetails) {
		System.out.println("Data Recieved in Register : " + sellerdetails);
		SellerDetailsDAO sellerdetailsDao = new SellerDetailsDAO();
		sellerdetailsDao.updateSellerDetails(sellerdetails);
	}



	@Path("registerSeller")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void resisterSeller(SellerDetails sellerdetails) {
		System.out.println("Data Recieved in Register : " + sellerdetails);
		SellerDetailsDAO sellerdetailsDao = new SellerDetailsDAO();
		sellerdetailsDao.register(sellerdetails);
	}

	@Path("getUserLogin/{emailId}/{password}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerDetails getUserLogin(@PathParam("emailId") String emailId, @PathParam("password") String password) throws Exception {
		System.out.println("Received Parameters : " + emailId + " " + password);
			
		PasswordEncrypDecrpt pswrdencrypt = new PasswordEncrypDecrpt();
		String encryptedPswrd = pswrdencrypt.encrypt(password);
		//String decryptedPswrd = pswrdencrypt.decrypt(encryptedPswrd);
		
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerByUserPass(emailId, encryptedPswrd);
		return buyer;
	} 

	@Path("getSellerLogin/{emailId}/{password}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SellerDetails getSellerLogin(@PathParam("emailId") String emailId, @PathParam("password") String password) {
		System.out.println("Received Parameters : " + emailId + " " + password);
		SellerDetailsDAO sellerDao = new SellerDetailsDAO();
		SellerDetails seller = sellerDao.getEmpByUserPass(emailId, password);
		return seller;
	}




	@Path("/addProducts")
	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public void addProducts(@FormDataParam("productImage") InputStream fileInputStream, @FormDataParam("productImage") FormDataContentDisposition formDataContentDisposition, @FormDataParam("productName") String productName, @FormDataParam("productType") String productType, @FormDataParam("productBrand") String productBrand, @FormDataParam("productPrice") int productPrice, @FormDataParam("availability") int availability, @FormDataParam("description") String description, @FormDataParam("sellerId") String sellerId ) throws IOException{
		int read = 0;
		byte[] bytes = new byte[1024];
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String pathArr[] = path.split("/WEB-INF/classes/");

		FileOutputStream out = new FileOutputStream(new File(pathArr[0]+"/Images/", formDataContentDisposition.getFileName()));
		while((read = fileInputStream.read(bytes)) != -1){
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();

		int sellerID = Integer.parseInt(sellerId);
		SellerDetailsDAO sellerDao = new SellerDetailsDAO();
		SellerDetails seller = sellerDao.getSellerDetails(sellerID);
		ProductDetails product = new ProductDetails();
		product.setProductName(productName);
		product.setProductType(productType);
		product.setProductBrand(productBrand);
		product.setProductPrice(productPrice);
		product.setAvailability(availability);
		product.setDescription(description);
		product.setSellerDetails(seller);
		product.setStatus("Pending");
		product.setImageName(formDataContentDisposition.getFileName());
		ProductDetailsDAO productDao = new ProductDetailsDAO();
		productDao.addproductdetails(product);
	}

	@Path("/addAdminProducts")
	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public void addAdminProducts(@FormDataParam("productImage") InputStream fileInputStream, @FormDataParam("productImage") FormDataContentDisposition formDataContentDisposition, @FormDataParam("productName") String productName, @FormDataParam("productType") String productType, @FormDataParam("productBrand") String productBrand, @FormDataParam("productPrice") int productPrice, @FormDataParam("availability") int availability, @FormDataParam("description") String description ) throws IOException{
		int read = 0;
		byte[] bytes = new byte[1024];
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String pathArr[] = path.split("/WEB-INF/classes/");

		FileOutputStream out = new FileOutputStream(new File(pathArr[0]+"/Images/", formDataContentDisposition.getFileName()));
		while((read = fileInputStream.read(bytes)) != -1){
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();


		ProductDetails product = new ProductDetails();
		product.setProductName(productName);
		product.setProductType(productType);
		product.setProductBrand(productBrand);
		product.setProductPrice(productPrice);
		product.setAvailability(availability);
		product.setDescription(description);
		product.setStatus("Accepted");
		product.setImageName(formDataContentDisposition.getFileName());
		ProductDetailsDAO productDao = new ProductDetailsDAO();
		productDao.addproductdetails(product);
	}


	@Path("/updateProfilePic")
	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public void updateProfilePic(@FormDataParam("buyerImage") InputStream fileInputStream, @FormDataParam("buyerImage") FormDataContentDisposition formDataContentDisposition, @FormDataParam("buyerId") String buyerId ) throws IOException{
		int read = 0;
		byte[] bytes = new byte[1024];
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String pathArr[] = path.split("/WEB-INF/classes/");

		FileOutputStream out = new FileOutputStream(new File(pathArr[0]+"/Images/", formDataContentDisposition.getFileName()));
		while((read = fileInputStream.read(bytes)) != -1){
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();

		int buyerID = Integer.parseInt(buyerId);
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerDetailsById(buyerID);

		buyer.setBuyerImage(formDataContentDisposition.getFileName());
		buyerDao.setBuyerDetailsById(buyer);
	}

	@Path("/updatePicSeller")
	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public void updatePicSeller(@FormDataParam("sellerImage") InputStream fileInputStream, @FormDataParam("sellerImage") FormDataContentDisposition formDataContentDisposition, @FormDataParam("sellerId") String sellerId ) throws IOException{
		int read = 0;
		byte[] bytes = new byte[1024];
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String pathArr[] = path.split("/WEB-INF/classes/");

		FileOutputStream out = new FileOutputStream(new File(pathArr[0]+"/Images/", formDataContentDisposition.getFileName()));
		while((read = fileInputStream.read(bytes)) != -1){
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();

		int sellerID = Integer.parseInt(sellerId);
		SellerDetailsDAO sellerDao = new SellerDetailsDAO();
		SellerDetails seller = sellerDao.getSellerDetailsById(sellerID);

		seller.setSellerImage(formDataContentDisposition.getFileName());
		sellerDao.setSellerDetailsById(seller);
	}







	@Path("getAllProductDetails")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductDetails> getAllProductDetails(){
		ProductDetailsDAO productdetailsdao = new ProductDetailsDAO();
		List<ProductDetails> productdetailsList = productdetailsdao.getAllProductDetails();
		System.out.println("returns Product List...");
		return productdetailsList;
	}

	@Path("getProductById/{productId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ProductDetails getOrderProduct(@PathParam("productId") int productId){
		ProductDetailsDAO productdao = new ProductDetailsDAO();
		ProductDetails productdetails = productdao.getProductById(productId);
		System.out.println(productdetails);
		return productdetails;
	}
	
	@Path("getProductByName/{productName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ProductDetails getProductByName(@PathParam("productName") String productName){
		ProductDetailsDAO productdetailsdao = new ProductDetailsDAO();
		ProductDetails productdetails = productdetailsdao.getProductByName(productName);
		System.out.println(productdetails);
		return productdetails;
	}
	
	

	@Path("getAllBuyerDetails")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BuyerDetails> getAllBuyerDetails(){
		System.out.println("Recieved in getAllProductDetails : " );
		BuyerDetailsDAO buyerdetailsdao = new BuyerDetailsDAO();
		List<BuyerDetails> buyerdetailsList = buyerdetailsdao.getAllBuyerDetails();
		return buyerdetailsList;
	}

	@Path("getAllSellerDetails")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SellerDetails> getAllSellerDetails(){
		//System.out.println("Recieved in getAllProductDetails : " );
		SellerDetailsDAO sellerdetailsdao = new SellerDetailsDAO();
		List<SellerDetails> sellerdetailsList = sellerdetailsdao.getAllSellerDetails();
		return sellerdetailsList;
	}

	@Path("/placeOrder")
	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public void placeOrder(@FormDataParam("street") String street, @FormDataParam("city") String city, @FormDataParam("state") String state, @FormDataParam("country") String country, @FormDataParam("pincode") String pincode, @FormDataParam("buyerId") int buyerId, @FormDataParam("amount") int amount ){


		Address address = new Address();
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setCountry(country);
		int pinCode = Integer.parseInt(pincode);
		address.setPinCode(pinCode);

		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerDetailsById(buyerId);

		OrderProduct placeOrder = new OrderProduct();
		placeOrder.setAddress(address);
		placeOrder.setAmount(amount);
		placeOrder.setOrderDate(new Date());
		placeOrder.setBuyerDetails(buyer);

		OrderProductDAO placeOrderDao = new OrderProductDAO();
		placeOrderDao.addorderproduct(placeOrder);
	}

	@Path("/addToWishList")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addToWishList(int wishListItem[]){
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerById(wishListItem[0]);

		ProductDetailsDAO productDao = new ProductDetailsDAO();
		ProductDetails product = productDao.getProductById(wishListItem[1]);

		WishList wishList = new WishList();
		wishList.setBuyerDetails(buyer);
		wishList.setProductDetails(product);

		WishListDAO wishListDao = new WishListDAO();
		wishListDao.register(wishList);
	}

	@Path("getWishList/{buyerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CartDetails> getWishList(@PathParam("buyerId") int buyerId){
		int productId;
		int wishListId;
		List wishlist = new ArrayList();
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerDetailsById(buyerId);

		WishListDAO wishDao = new WishListDAO();
		List<WishList> wishList = wishDao.getWishListByBuyer(buyer);
		for(int i = 0; i < wishList.size(); i++){
			List lis = new ArrayList();
			WishList wish = wishList.get(i);
			wishListId = wish.getWishListId();
			lis.add(wishListId);
			productId = wish.getProductDetails().getProductId();
			lis.add(productId);

			wishlist.add(lis);
		}
		return wishlist;
	}

	@Path("buyerById/{buyerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerDetails buyerById(@PathParam("buyerId") int buyerId){
		BuyerDetailsDAO buyerdao = new BuyerDetailsDAO();
		BuyerDetails buyerdetails = buyerdao.getBuyerById(buyerId);
		System.out.println(buyerdetails);
		return buyerdetails;
	}

	@Path("sellerById/{sellerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SellerDetails sellerById(@PathParam("sellerId") int sellerId){
		SellerDetailsDAO sellerdao = new SellerDetailsDAO();
		SellerDetails sellerdetails = sellerdao.getSellerById(sellerId);
		System.out.println(sellerdetails);
		return sellerdetails;
	}

	@Path("getAllProductsByType/{productType}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductDetails> getAllProductsByType(@PathParam("productType") String productType){
		ProductDetailsDAO productdetailsdao = new ProductDetailsDAO();
		List<ProductDetails> productsList = productdetailsdao.getAllProductsByType(productType);
		return productsList;
	}

	@Path("getAllProductsByBrand/{productBrand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductDetails> getAllProductsByBrand(@PathParam("productBrand") String productBrand){
		ProductDetailsDAO productdetailsdao = new ProductDetailsDAO();
		List<ProductDetails> productsList = productdetailsdao.getAllProductsByBrand(productBrand);
		return productsList;
	}



	@Path("getOrderItems/{buyerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderProduct> getOrderItems(@PathParam("buyerId") int buyerId){
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerDetailsById(buyerId);

		OrderProductDAO orderproductdao = new OrderProductDAO();
		List<OrderProduct> orderList = orderproductdao.getOrderListByBuyer(buyer);
		return orderList;
	}
	
	
	
	/*
	@Path("getMyOrderedItems/{buyerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderDetails> getMyOrderedItems(@PathParam("buyerId") int buyerId){
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerDetailsById(buyerId);

		OrderDetailsDAO orderDetailsdao = new OrderDetailsDAO();
		List<OrderDetails> myOrdersList = orderDetailsdao.getMyOrderByBuyerId(buyer);
		System.out.println(myOrdersList);
		return myOrdersList;
	}
	
	*/
	

	//@Path("addOrderDetails/{orderId}/{cartId}")
	@Path("addOrderDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addOrderDetails(int orderDetails[]) {

		OrderProductDAO orderDao = new OrderProductDAO();
		OrderProduct order = orderDao.getOrderProduct(orderDetails[0]);

		ProductDetailsDAO productDao = new ProductDetailsDAO();
		ProductDetails product = productDao.getProductById(orderDetails[1]);

		OrderDetails orders = new OrderDetails();
		//orders.setCartDetails(cartList);
		orders.setOrderProduct(order);
		orders.setProductdetails(product);
		orders.setPrice(product.getProductPrice());
		orders.setQuantity(orderDetails[2]);
		double subtotal = orderDetails[2] * product.getProductPrice();
		orders.setSubTotal(subtotal);
		OrderDetailsDAO orderdetailsDao = new OrderDetailsDAO();
		orderdetailsDao.addOrderDetails(orders);


	}


	/*@Path("/updateOrder")
@POST
@Consumes(MediaType.APPLICATION_JSON)
public void updateOrderProduct(String update[]){
int orderId = Integer.parseInt(update[0]);
String orderStatus = update[1];
OrderProductDAO orderDao = new OrderProductDAO();
orderDao.updateOrder(orderId, orderStatus);
}*/

	@Path("/updateCart")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCartDetails(String update[]){
		int cartId = Integer.parseInt(update[0]);
		String cartStatus = update[1];
		CartDetailsDAO cartDao = new CartDetailsDAO();
		cartDao.updateCart(cartId, cartStatus);
	}

	@Path("getCartItems/{buyerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CartDetails> getCartItems(@PathParam("buyerId") int buyerId){
		int productId;
		int quantity;
		String status;
		int cartId;
		List cartDet = new ArrayList();
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerDetailsById(buyerId);

		CartDetailsDAO cartDao = new CartDetailsDAO();
		List<CartDetails> cartList = cartDao.getCartListByBuyer(buyer);
		for(int i = 0; i < cartList.size(); i++){
			List lis = new ArrayList();
			CartDetails cart = cartList.get(i);
			productId = cart.getProductDetails().getProductId();
			quantity = cart.getQuantity();
			status = cart.getStatus();
			cartId = cart.getCartId();
			lis.add(productId);
			lis.add(quantity);
			lis.add(status);
			lis.add(cartId);
			cartDet.add(lis);
		}
		return cartDet;
	}


	@Path("/addCartItems")
	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	public void addCartItems(int cartItems[]){
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerById(cartItems[0]);

		ProductDetailsDAO productDao = new ProductDetailsDAO();
		ProductDetails product = productDao.getProductById(cartItems[1]);

		CartDetails cart = new CartDetails();
		cart.setProductDetails(product);
		cart.setBuyerDetails(buyer);
		cart.setQuantity(cartItems[2]);

		CartDetailsDAO cartDao = new CartDetailsDAO();
		cartDao.addCartDetails(cart);

	}

	/*
@Path("/generateOTP")
@GET
@Produces( MediaType.TEXT_PLAIN)
public void otp(){
MyResource1 otp = new MyResource1();
otp.generateOTP();
}
	 */


	@Path("checkCartItems/{buyerId}/{productId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CartDetails> checkCartItems(@PathParam("buyerId") int buyerId, @PathParam("productId") int productId){
		List cartDet = new ArrayList();
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerDetailsById(buyerId);

		ProductDetailsDAO productDao = new ProductDetailsDAO();
		ProductDetails product = productDao.getProductById(productId);

		CartDetailsDAO cartDao = new CartDetailsDAO();
		int productID;
		int cartID;
		String status;
		List<CartDetails> cartList = cartDao.checkCartItems(buyer, product);
		for(int i = 0; i < cartList.size(); i++){
			List lis = new ArrayList();
			CartDetails cart = cartList.get(i);
			if(cart.getStatus() == null){
				productID = cart.getProductDetails().getProductId();
				status = cart.getStatus();
				cartID = cart.getCartId();
				lis.add(productID);
				lis.add(status);
				lis.add(cartID);
				cartDet.add(lis);
			}
		}

		return cartDet;
	}

	//sellerProducts
	@Path("sellerProducts/{sellerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductDetails> sellerProducts(@PathParam("sellerId") int sellerId){
		System.out.println(sellerId);
		SellerDetailsDAO sellerDao = new SellerDetailsDAO();
		SellerDetails seller = sellerDao.getSellerById(sellerId);

		ProductDetailsDAO productdetailsdao = new ProductDetailsDAO();
		List<ProductDetails> productsList = productdetailsdao.productsBySellerId(seller);
		return productsList;
	}

	//pending products
	@Path("getPendingProducts")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductDetails> getPendingProducts(){
		ProductDetailsDAO productdetailsdao = new ProductDetailsDAO();
		List<ProductDetails> productdetailsList = productdetailsdao.getAllPendingProducts();
		System.out.println("returns Product List...");
		return productdetailsList;
	}

	//Update Seller Product Status
	/*@Path("/updateSellerStatus/{productId}")
@POST
@Consumes({MediaType.MULTIPART_FORM_DATA})
public void updateSellerStatus(@PathParam("productId") int productId ) {
ProductDetailsDAO productDao = new ProductDetailsDAO();

ProductDetails product = productDao.getProductById(productId);

product.setStatus("Accepted");
productDao.updateProductStatus(product);
} */

	@Path("/updateSellerStatus")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateSellerStatus(String update[]){
		int productId = Integer.parseInt(update[0]);
		String productStatus = update[1];
		ProductDetailsDAO productDao = new ProductDetailsDAO();
		productDao.updateStatus(productId, productStatus);
	}

	// search
	@Path("getAllProductNames")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductDetails> getAllProductNames(){
		ProductDetailsDAO productdetailsdao = new ProductDetailsDAO();
		List<ProductDetails> productdetailsList = productdetailsdao.getAllProductNames();
		System.out.println("returns Product List...");
		return productdetailsList;
	}
	
	

	@Path("/updateQuantity")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateQuantity(int quantityReduction[]){
		int productId = quantityReduction[0];
		int quantity = quantityReduction[1];
		ProductDetailsDAO productDao = new ProductDetailsDAO();
		ProductDetails product = productDao.getProductById(productId);
		int availableQuantity = product.getAvailability();
		int update = availableQuantity - quantity;
		ProductDetailsDAO productDao1 = new ProductDetailsDAO();
		productDao1.updateQuantity(productId, update);

	}

	@Path("/deleteCartItem")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteCartItem(int cartId){
		CartDetailsDAO cartDao = new CartDetailsDAO();
		cartDao.deleteCartItem(cartId);
	}
	@Path("/deleteWishListItem")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteWishItem(int wishListId){
		WishListDAO wishDao = new WishListDAO();
		wishDao.deleteWishListItem(wishListId);
	}

	@Path("checkWishList/{buyerId}/{productId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<WishList> checkWishList(@PathParam("buyerId") int buyerId, @PathParam("productId") int productId){
		//int productID = Integer.parseInt(productId);
		List wish = new ArrayList();
		int productID;
		int wishID;
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.getBuyerDetailsById(buyerId);

		ProductDetailsDAO productDao = new ProductDetailsDAO();
		ProductDetails product = productDao.getProductById(productId);

		WishListDAO wishDao = new WishListDAO();
		List<WishList> wishList = wishDao.checkWishList(buyer, product);
		for(int i = 0; i < wishList.size(); i++){
			List lis = new ArrayList();
			WishList wishlist = wishList.get(i);
			productID = wishlist.getProductDetails().getProductId();
			wishID = wishlist.getWishListId();
			lis.add(productID);
			lis.add(wishID);
			wish.add(lis);
		}
		return wish;
	}
	
	
	
	//orderId using buyerId
		@Path("getOrderIdList/{buyerId}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<OrderProduct> getOrderIdList(@PathParam("buyerId") int buyerId){
			BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
			BuyerDetails buyer = buyerDao.getBuyerById(buyerId);
			
			OrderProductDAO orderproduct = new OrderProductDAO();
			List<OrderProduct> orderIdList = (List<OrderProduct>) orderproduct.getOrderIdListByQuery(buyer);
			return orderIdList;
		}
		
		//
		@Path("getProductIdList/{orderId}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<OrderDetails> getProductIdList(@PathParam("orderId") int orderId){
			OrderProductDAO orderDao = new OrderProductDAO();
			OrderProduct order = orderDao.getOrderProduct(orderId);
			
			OrderDetailsDAO orderdetails = new OrderDetailsDAO();
			List<OrderDetails> productIdList = (List<OrderDetails>) orderdetails.getOrderIdListByQuery(order);
			List orderDetails = new ArrayList();
			for(int i = 0; i < productIdList.size();i++ ){
				List orderdata = new ArrayList();
				OrderDetails ord=productIdList.get(i);
				orderdata.add(ord.getProductdetails().getProductId());
				orderdata.add(ord.getQuantity());
				orderdata.add(ord.getSubTotal());
				orderdata.add(order.getOrderDate());
				orderdata.add(ord.getOrderProduct().getOrderId());
				orderDetails.add(orderdata);
				
				}
			
			return orderDetails;
		}
}

