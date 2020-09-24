package com.ts.db;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


import com.rest.dto.BuyerDetails;
import com.rest.dto.CartDetails;
import com.rest.dto.Employee;
import com.rest.dto.OrderProduct;
import com.rest.dto.ProductDetails;
import com.rest.dto.SellerDetails;

public class HibernateTemplate {

	private static SessionFactory sessionFactory;

	static {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public static int addObject(Object obj){
		System.out.println("Inside Template...");
		int result=0;

		Transaction tx=null;

		try {

			Session session=sessionFactory.openSession();
			tx=session.beginTransaction();

			session.save(obj);

			tx.commit();

			result=1;

		} catch (Exception e) {

			tx.rollback();

			e.printStackTrace();
		}

		return result;
	}

	public static Object getObject(Class c,Serializable serializable)
	{
		Object obj=null;

		try {			
			return sessionFactory.openSession().get(c,serializable);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return obj;
	}
	public static Object getObjectByUserPass(String emailId,String password) {

		String queryString = "from BuyerDetails where buyerEmail = :emailId and password =:password";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setString("emailId", emailId);
		query.setString("password", password);
		Object queryResult = query.uniqueResult();
		BuyerDetails buyer = (BuyerDetails)queryResult;
		return buyer; 
	}
	public static Object getSellerByUserPass(String emailId,String password) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String queryString = "from SellerDetails where sellerEmail = :emailId and password =:password";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setString("emailId", emailId);
		query.setString("password", password);
		Object queryResult = query.uniqueResult();
		SellerDetails seller = (SellerDetails)queryResult;
		return seller; 
	}
	
	public static int updateCartDetails(int cartId, String cartStatus){
		Session session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		String queryString = "update CartDetails set status = :cartStatus where cartId = :cartId";
		Query query = session.createQuery(queryString);
		query.setString("cartStatus", cartStatus);
		query.setInteger("cartId", cartId);
		int result = query.executeUpdate();

		tx.commit();

		return result;		
	}
	
	public static int updateProductStatus(int productId, String productStatus){
		Session session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		String queryString = "update ProductDetails set status = :productStatus where productId = :productId";
		Query query = session.createQuery(queryString);
		query.setString("productStatus", productStatus);
		query.setInteger("productId", productId);
		int result = query.executeUpdate();

		tx.commit();

		return result;		
	}
	
	
	
	public static List<Object> getWishListByQuery(BuyerDetails buyer)
	{
		
		String queryString = "from WishList where buyerId = :buyerId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("buyerId", buyer.getBuyerId());
		return query.list();
	}
	
	
	
	
	public static Object getObjectByEmail(String email) {

		String queryString = "from Employee where email = :email";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setString("email", email);
		Object queryResult = query.uniqueResult();
		Employee employee = (Employee)queryResult;
		return employee; 
	}

	public static List<Object> getObjectListByQuery(String query)
	{
		return sessionFactory.openSession().createQuery(query).list();
	}
	public static List<Object> getOrderListByQuery(BuyerDetails buyer)
	{
		
		String queryString = "from OrderProduct where buyerId = :buyerId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("buyerId", buyer.getBuyerId());
		return query.list();
	}
	
	/*public static List<Object> getMyOrdersListByQuery(BuyerDetails buyer)
{
		String queryString = "from OrderProduct op, OrderDetails od where od.orderProduct.orderId=op.orderId and buyerId=:buyerId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("buyerId", buyer.getBuyerId());
		return query.list();
	}
	*/public static List<Object> getOrderIdListByQuery(BuyerDetails buyer)
	{
		
		String queryString = "select orderId from OrderProduct where buyerId=:buyerId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("buyerId", buyer.getBuyerId());
		return query.list();
	}
	
	public static List<Object> getProductIdListByQuery(OrderProduct order)
	{
		
		String queryString = "from OrderDetails where orderId=:orderId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("orderId", order.getOrderId());
		return query.list();
	}
	

	
	
	public static Object checkEmail(String emailId)
	{
		String queryString = "from BuyerDetails where buyerEmail = :emailId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setString("emailId", emailId);
		Object queryResult = query.uniqueResult();
		BuyerDetails buyer = (BuyerDetails)queryResult;
		return buyer; 
	}
	
	public static Object checkproductName(String productName)
	{
		String queryString = "from ProductDetails where productName= :productName";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setString("productName", productName);
		Object queryResult = query.uniqueResult();
		ProductDetails product = (ProductDetails)queryResult;
		return product; 
	}
	
	
	
	public static Object checkNumber(String mobileNo)
	{
		String queryString = "from BuyerDetails where mobileNo = :mobileNo";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setString("mobileNo", mobileNo);
		Object queryResult = query.uniqueResult();
		BuyerDetails buyer = (BuyerDetails)queryResult;
		return buyer; 
	}
	public static List<Object> checkCartListByQuery(BuyerDetails buyer, ProductDetails product)
	{
		String queryString = "from CartDetails where buyerId = :buyerId and productId = :productId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("buyerId", buyer.getBuyerId());
		query.setInteger("productId", product.getProductId());
		return query.list();
		//return sessionFactory.openSession().createQuery(query).list();
	}
	
	public static List<Object> getCartListByQuery(BuyerDetails buyer)
	{
		String queryString = "from CartDetails where buyerId = :buyerId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("buyerId", buyer.getBuyerId());
		return query.list();
		//return sessionFactory.openSession().createQuery(query).list();
	}

	public static int updateObject(Object obj)
	{
		int result=0;

		Transaction tx=null;

		try {

			Session session=sessionFactory.openSession();
			tx=session.beginTransaction();

			session.saveOrUpdate(obj);

			tx.commit();

			result=1;

		} catch (Exception e) {

			tx.rollback();

			e.printStackTrace();
		}

		return result;
	}

	public static int deleteObject(Class c,Serializable serializable)
	{
		int result=0;

		Session session=sessionFactory.openSession();

		Transaction tx=session.beginTransaction();

		try {

			Object obj=session.get(c,serializable);

			session.delete(obj);

			tx.commit();

			result=1;

		} catch (Exception e) {

			e.printStackTrace();

			tx.rollback();
		}

		return result;
	}

	public static List<Object> getObjectListByName(Class c, String columName, String value) {
		Session session=sessionFactory.openSession();
		Criteria criteria = session.createCriteria(c);
		Criterion nameCriterion = Restrictions.eq(columName, value);
		criteria.add(nameCriterion);
		return criteria.list();
	}
	
	public static List<Object> getProductListByQuery(SellerDetails seller)
	{
		
		String queryString = "from ProductDetails where sellerId = :sellerId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("sellerId", seller.getSellerId());
		return query.list();
	}
	
	public static int updateQuantity(int productId, int quantity){
		Session session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		String queryString = "update ProductDetails set availability = :quantity where productId = :productId";
		Query query = session.createQuery(queryString);
		query.setInteger("quantity", quantity);
		query.setInteger("productId", productId);
		int result = query.executeUpdate();

		tx.commit();

		return result;		
	}
	public static List<Object> checkWishListByQuery(BuyerDetails buyer, ProductDetails product)
	{
		String queryString = "from WishList where buyerId = :buyerId and productId = :productId";
		Query query = sessionFactory.openSession().createQuery(queryString);
		query.setInteger("buyerId", buyer.getBuyerId());
		query.setInteger("productId", product.getProductId());
		return query.list();
		//return sessionFactory.openSession().createQuery(query).list();
	}

}