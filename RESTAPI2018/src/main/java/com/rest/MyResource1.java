package com.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rest.dto.Address;
import com.rest.dto.BuyerDetails;
import com.rest.dto.OrderDetails;
import com.rest.dto.OrderProduct;
import com.rest.dto.ProductDetails;
import com.rest.dto.SellerDetails;
import com.ts.dao.BuyerDetailsDAO;
import com.ts.dao.EmailUtil;
import com.ts.dao.OrderDetailsDAO;
import com.ts.dao.OrderProductDAO;
import com.ts.dao.ProductDetailsDAO;
import com.ts.dao.SellerDetailsDAO;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Path("myresource1")
public class MyResource1 {

	@Path("generateOTP")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public static String generateOTP() {
		
		//System.out.println("MobileNo : " +mobileNo);
		
		int randomPin   =(int) (Math.random()*9000)+1000; 
		String otp  = String.valueOf(randomPin); 
		//function calling 
		System.out.println("OTP : "+otp); 

		return otp; //returning value of otp 

	}

	@Path("sendSMS/{mobileNo}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sendSMS (@PathParam("mobileNo") String mobileNo) {
		String otpStr = MyResource1.generateOTP();
		System.out.println(otpStr);
		System.out.println("MobileNo : " +mobileNo);
		try {

			String apiKey = "apiKey=" + "Lw8jq0k0Rl0-jMdeecOm25TEz06cnmbg17ty6vPR7m";

			String message = "&message=" + URLEncoder.encode("Your OTP is " + otpStr,
					"UTF-8");

			String numbers = "&numbers=" + mobileNo;

			String apiURL = "https://api.textlocal.in/send/?" + apiKey + message + numbers;

			URL url = new URL(apiURL);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);

			BufferedReader reader = new BufferedReader(new 
					InputStreamReader(connection.getInputStream()));

			String line = "";
			StringBuilder sb = new StringBuilder();

			while ( (line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}

			System.out.println(sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otpStr;
	}
	
	@Path("sendEmail/{buyerEmail}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
		/**
		   Outgoing Mail (SMTP) Server
		   requires TLS or SSL: smtp.gmail.com (use authentication)
		   Use Authentication: Yes
		   Port for TLS/STARTTLS: 587
		 */
		public String sendEmail(@PathParam("buyerEmail") String buyerEmail) {
			
			String generatedPswrd = generatePassword();
			
			final String fromEmail = "arttoolshut@gmail.com"; //requires valid gmail id
			final String password = "ngrj2020"; // correct password for gmail id
			final String toEmail = buyerEmail; // can be any email id 
			
			System.out.println("TLSEmail Start");
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
			props.put("mail.smtp.port", "587"); //TLS Port
			props.put("mail.smtp.auth", "true"); //enable authentication
			props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
			
	                //create Authenticator object to pass in Session.getInstance argument
			Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};
			Session session = Session.getInstance(props, auth);
			
			//EmailUtil.sendEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
			EmailUtil.sendEmail(session, toEmail,"User confirmation mail", "Your generated password is: " +generatedPswrd );
			
			return generatedPswrd;
		}
	
	
	
	@Path("generatePassword")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
		   public String generatePassword() {
			  int length = 6;
		      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		      //String specialCharacters = "!@#$";
		      String numbers = "1234567890";
		      String combinedChars = capitalCaseLetters + lowerCaseLetters + numbers;
		      Random random = new Random();
		      char[] password = new char[length];

		      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		      //password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		      password[2] = numbers.charAt(random.nextInt(numbers.length()));
		   
		      for(int i = 3; i< length ; i++) {
		         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		      }
		      System.out.println(password);
		      String generatedPswrd = new String(password);
		      return generatedPswrd;
		   }
	
	
	@Path("getUserLogin/{emailId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerDetails getUserLogin(@PathParam("emailId") String emailId) {
		System.out.println("Received Parameters : " + emailId);
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.checkBuyerEmail(emailId);
		return buyer;
	}
	
	@Path("getUserLoginByNo/{mobileNo}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerDetails getUserLoginByNo(@PathParam("mobileNo") String mobileNo) {
		System.out.println("Received Parameters : " + mobileNo);
		BuyerDetailsDAO buyerDao = new BuyerDetailsDAO();
		BuyerDetails buyer = buyerDao.checkBuyerNum(mobileNo);
		return buyer;
	}
	@Path("confirmationEmail/{buyerEmail}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
		/**
		   Outgoing Mail (SMTP) Server
		   requires TLS or SSL: smtp.gmail.com (use authentication)
		   Use Authentication: Yes
		   Port for TLS/STARTTLS: 587
		 */
	public String confirmationEmail(@PathParam("buyerEmail") String buyerEmail) {
		
		BuyerDetails buyer = getUserLogin(buyerEmail);
		String name = buyer.getBuyerName(); 
		
		//String generatedPswrd = generatePassword();
		String message = "Your ArtToolsHut order is confirmed";
		
		final String fromEmail = "arttoolshut@gmail.com"; //requires valid gmail id
		final String password = "ngrj2020"; // correct password for gmail id
		final String toEmail = buyerEmail; // can be any email id 
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		//EmailUtil.sendEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
		EmailUtil.sendEmail(session, toEmail,"Success! Your ArtToolsHut order is confirmed","Hey " + name + "," + "\n" + "\n" + "Your Order has been placed successfully." + "\n" + "Once  packed and shipped out, we shall keep you updated on the promised date of delivery. Excited? Not more than us! We are doing our best to get you your purchase as soon as possible." + "\n" + "\n" + "Thank you for shopping with ArtToolsHut. Stay home. Stay safe." );
		
		return "success";
	}
	
}
	
	
	
	