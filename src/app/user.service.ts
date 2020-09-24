import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { retry } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private isUserLogged: any;
  cartItems = [];
  cartItem: any;
  orderDetails = [];
  orderUpdate = [];
  wishItems = [];
  productToBeWished: Subject <any>;
  
  
  productToBeAdded: Subject <any>;
  
    authenticated$: BehaviorSubject<boolean> = new BehaviorSubject(true);
    sellerauthenticated$: BehaviorSubject<boolean> = new BehaviorSubject(true);
    userauthenticated$: BehaviorSubject<boolean> = new BehaviorSubject(true);
    adminauthenticated$: BehaviorSubject<boolean> = new BehaviorSubject(true);
  


  constructor(private httpClient: HttpClient) {
    
    this.isUserLogged = false;
    this.productToBeAdded = new Subject();
    this.productToBeWished = new Subject();
    //this.cartItem = {product:{}, productQuantity:''};
   }

   setUserLoggedIn(): void { // login success
    this.isUserLogged = true;
   }
   setUserLoggedOut(): void { // logout success
    this.isUserLogged = false;
   }
   getUserLogged(): any { // call this in AuthGuard
     return this.isUserLogged;
   }

   addToCart(productPage: any,quantity: any){
    this.productToBeAdded.next(productPage);
    //this.products.push(product);
    this.cartItem = {product: productPage, productQuantity: quantity};
    this.cartItems.push(this.cartItem);
  }

  getAllProductDetails(): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getAllProductDetails/');
   }

  getForCart(){
    return this.productToBeAdded.asObservable();
  }

   //http://localhost:8080/RESTAPI2018/webapi/myresource1/registerBuyer
  
  getAllProductsByType(type: string): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getAllProductsByType/'+ type);
   }
   getAllProductsByBrand(brand: string): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getAllProductsByBrand/'+ brand);
   }


   addproductsNew(productdetails: any) {
    return this.httpClient.post('RESTAPI2018/webapi/myresource/registerProduct/',  productdetails);
  }

  registerBuyer(buyerdetails: any) { // register as buyer
    return this.httpClient.post('RESTAPI2018/webapi/myresource/registerBuyer/',  buyerdetails);
  }

  registerSeller(sellerdetails: any) { // register as seller
    return this.httpClient.post('RESTAPI2018/webapi/myresource/registerSeller/',  sellerdetails);
  }

   getAllProducts(): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getAllProductDetails/');
    
   }


   getProductByName(c: any): any{
     console.log(c);
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getProductByName/'+ c);
   }

   getProductById(productId: any): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getProductById/'+ productId);
   }
   userLogIn(emailId: string, password: string):any {
    console.log('Inside service: ' ,emailId,password);
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getUserLogin/' + emailId + '/' + password).toPromise();
   }
   sellerLogIn(emailId: string, password: string):any {
    console.log('Inside service: ' ,emailId,password);
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getSellerLogin/' + emailId + '/' + password).toPromise();
   }
   getAllBuyerDetails(): any {
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getAllBuyerDetails/');
    //http://localhost:8080/RESTAPI/webapi/myresource/getAllEmployees
   }
   
 postFile(addProductsForm: any, fileToUpload: File, sellerId: any) {
  //throw new Error("Method not implemented.");
  const endPoint = 'RESTAPI2018/webapi/myresource/addProducts';
  const formData: FormData = new FormData();
  formData.append('productImage', fileToUpload, fileToUpload.name);
  formData.append('productName', addProductsForm.productName);
  formData.append('productType', addProductsForm.productType);
  formData.append('productBrand', addProductsForm.productBrand);
  formData.append('productPrice', addProductsForm.productPrice);
  formData.append('availability', addProductsForm.availability);
  formData.append('description', addProductsForm.description);
  formData.append('sellerId', sellerId);
  return this.httpClient.post(endPoint, formData);

}

uploadProfileImg(fileToUpload: File, buyerId: any): any {
  const endPoint = 'RESTAPI2018/webapi/myresource/updateProfilePic';
  const formData: FormData = new FormData();
  formData.append('buyerImage', fileToUpload, fileToUpload.name);
  formData.append('buyerId', buyerId);
  return this.httpClient.post(endPoint, formData);
}

uploadProfileImgS(fileToUpload: File, sellerId: any): any {
  const endPoint = 'RESTAPI2018/webapi/myresource/updatePicSeller';
  const formData: FormData = new FormData();
  formData.append('sellerImage', fileToUpload, fileToUpload.name);
  formData.append('sellerId', sellerId);
  return this.httpClient.post(endPoint, formData);
}


adminAddProducts(addProductsForm: any, fileToUpload: File) {
  //throw new Error("Method not implemented.");
  const endPoint = 'RESTAPI2018/webapi/myresource/addAdminProducts';
  const formData: FormData = new FormData();
  formData.append('productImage', fileToUpload, fileToUpload.name);
  formData.append('productName', addProductsForm.productName);
  formData.append('productType', addProductsForm.productType);
  formData.append('productBrand', addProductsForm.productBrand);
  formData.append('productPrice', addProductsForm.productPrice);
  formData.append('availability', addProductsForm.availability);
  formData.append('description', addProductsForm.description);
  //formData.append('sellerId', sellerId);
  return this.httpClient.post(endPoint, formData);

}

addCartItems(cartProducts: Int16Array){
  const endPoint = 'RESTAPI2018/webapi/myresource/addCartItems';
  return this.httpClient.post(endPoint, cartProducts).toPromise();
  
}

placeOrder( buyerId: any, address:any, total:any){
  const endPoint = 'RESTAPI2018/webapi/myresource/placeOrder';
  
  const form: FormData = new FormData();
  form.append('street', address.street);
  form.append('city', address.city);
  form.append('state', address.state);
  form.append('country', address.country);
  form.append('pincode', address.pinCode);
  form.append('amount', total);
  form.append('buyerId', buyerId);
  return this.httpClient.post(endPoint, form);


}

   getAllSellerDetails(): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getAllSellerDetails/');
   }



   public authenticate() {
    this.authenticated$.next(true);
  }

   public deauthenticate() {
    this.authenticated$.next(false);
  }
  
   public sellerAuthenticate() {
     this.sellerauthenticated$.next(true);
   }
   public sellerDeauthenticate() {
    this.sellerauthenticated$.next(false);
  } 
   
   public userAuthenticate() {
    this.userauthenticated$.next(true);
  }
  public userDeauthenticate() {
   this.userauthenticated$.next(false);
 }

 public adminAuthenticate() {
  this.adminauthenticated$.next(true);
}
public adminDeauthenticate() {
 this.adminauthenticated$.next(false);
}
  

  getBuyerMobile(mobileNo: any): any {
    console.log(mobileNo);
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getMessage/' + mobileNo);
  }
  
  userProfile(buyerId: any): any {
    return this.httpClient.get('RESTAPI2018/webapi/myresource/buyerById/' + buyerId);
  }
  
  sellerProfile(sellerId: any): any {
    return this.httpClient.get('RESTAPI2018/webapi/myresource/sellerById/' + sellerId);
  }



  getMyOrderedItems(buyerId:any):any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getMyOrderedItems/' + buyerId).toPromise();
  }


  updateUser(editObject: any): any{
    return this.httpClient.post('RESTAPI2018/webapi/myresource/updateBuyerDetails', editObject);
  }

  getCartItems(buyerId: any): any {
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getCartItems/' + buyerId ).toPromise(); 
  }

  getOrderItems(buyerId: any): any {
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getOrderItems/' + buyerId).toPromise(); 
  }

  addOrderDetails(orderDetails: Int16Array): any {
    return this.httpClient.post('RESTAPI2018/webapi/myresource/addOrderDetails/', orderDetails).toPromise();
  }

 /* updateOrderStatus(orderId: any, orderStatus: any): any{
    this.orderUpdate = [orderId, orderStatus];
    return this.httpClient.post('RESTAPI2018/webapi/myresource/updateOrder/', this.orderUpdate);
  }
*/
  updateCartStatus(cartUpdate: any): any{  
    return this.httpClient.post('RESTAPI2018/webapi/myresource/updateCart/', cartUpdate);
  }

  addToWishList(productPage: any){
    this.productToBeWished.next(productPage);
    //this.products.push(product);
    this.wishItems.push(productPage);
  }

  getForWishList(){
    return this.productToBeWished.asObservable();
  }

  addToWish(product: any){
    this.productToBeWished.next(product);
    //this.products.push(product);
    this.wishItems.push(product);
  }

  getWishList(){
    return this.productToBeWished.asObservable();
  }
  addWishListItem(wishList: Int16Array){
    return this.httpClient.post('RESTAPI2018/webapi/myresource/addToWishList/', wishList);
  }

  getWishListItems(buyerId: any){
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getWishList/' + buyerId ).toPromise(); 
  }

  getOTP(mobileNo: any){
    return this.httpClient.get('RESTAPI2018/webapi/myresource1/sendSMS/' + mobileNo);
  }

  updateSeller(editObject: any): any{
    return this.httpClient.post('RESTAPI2018/webapi/myresource/updateSellerDetails', editObject);
  }

  
  checkBuyerEmail(emailId: string):any {
    console.log('Inside service: ' ,emailId);
    return this.httpClient.get('RESTAPI2018/webapi/myresource1/getUserLogin/' + emailId).toPromise();
   }
   checkBuyerNum(mobileNo: any) {
    console.log('Inside service: ' ,mobileNo);
    return this.httpClient.get('RESTAPI2018/webapi/myresource1/getUserLoginByNo/' + mobileNo).toPromise();
   }

   sendPasscode(buyerEmail: any) {
    return this.httpClient.get('RESTAPI2018/webapi/myresource1/sendEmail/' + buyerEmail ,{responseType: 'text'});
   }
   checkWishListItems(buyerId:any, productId:any):any{
    //this.wishlist = [buyerId, productId];
    return this.httpClient.get('RESTAPI2018/webapi/myresource/checkWishList/'+ buyerId+ '/' + productId);
  }

  deleteWishListItem(wishListId: any){
    return this.httpClient.post('RESTAPI2018/webapi/myresource/deleteWishListItem/', wishListId);

  }
  deleteCartItem(cartId: any){
    return this.httpClient.post('RESTAPI2018/webapi/myresource/deleteCartItem/', cartId);
  }
  checkCartItems(buyerId: any, productId: any): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/checkCartItems/' + buyerId + '/' +productId);
  }

  quantityreduction(quantityUpdate: any):any{
    return this.httpClient.post('RESTAPI2018/webapi/myresource/updateQuantity/', quantityUpdate);
  }

  productsBySellerId(sellerId: any) {
    return this.httpClient.get('RESTAPI2018/webapi/myresource/sellerProducts/' + sellerId);
  }

  productsOfSeller(): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getPendingProducts/');
  }

  //Status of seller products
  updateSellerStatus(updateStatus: any): any {
    return this.httpClient.post('RESTAPI2018/webapi/myresource/updateSellerStatus/' , updateStatus);
  }

  getAllProductNames(): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getAllProductNames/'); 
   }
   confimationMail(buyerEmail: any) {
    return this.httpClient.get('RESTAPI2018/webapi/myresource1/confirmationEmail/' + buyerEmail,{responseType: 'text'});
   }


   //orderIDs using buyerID
   getOrderIds(buyerId: any): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getOrderIdList/' + buyerId).toPromise();
   }

   //
   getProductIds(orderId: any): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getProductIdList/' + orderId).toPromise();
   }

   /*getOrderDetailsById(id: any): any{
    return this.httpClient.get('RESTAPI2018/webapi/myresource/getOrderDetailsById/' + id);
   }
*/
removeCartItems(cartItem: any){
  this.cartItems = this.cartItems.filter(item => item !== cartItem);
}

}

 