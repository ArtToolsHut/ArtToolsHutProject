import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart-display',
  templateUrl: './cart-display.component.html',
  styleUrls: ['./cart-display.component.css']
})
export class CartDisplayComponent implements OnInit {
  buyerId: any;
  productDetails: any;
  cart: any;
  allTotal: any;
  total: any;
  cartList = [];
  address: any;
  id: any;
  cartItems = [];
  orderDetails: any;


  constructor(private service: UserService, private router: Router ) {
    this.address = {name: '', mobileNo: '', street: '', town: '', state: '', pinCode: '', country: ''};
    this.cartItems = this.service.cartItems;
    this.cart = {quantity:'', product: {productId:'', productName: '', imageName: '', productType: '', productBrand: '', productPrice: '', availability: '', description: ''}, cartId:''};
   }

  async ngOnInit(): Promise<void> {
    this.buyerId = parseInt(localStorage.getItem("buyerId"));
    this.calculateTotal(this.orderDetails);
    await this.service.getCartItems(this.buyerId).then((result: any) => {
      console.log(result);
      console.log(this.cartList);
      
      for(let i = 0; i < result.length; i++){
        if(result[i][2] === null){
          console.log(result[i]);
          this.service.getProductById(result[i][0]).subscribe((data: any) =>{
            this.productDetails = data;
            this.cart = {quantity: result[i][1], product: this.productDetails, cartId: result[i][3]};
            this.cartList.push(this.cart);
          });        
        }
      }
      
      
    });
    
    
  }
  

  getAddress() {
    this.router.navigate(['/orders']);
  }
  

 deleteCartItem(productDetails: any){
  console.log(productDetails);
  this.service.deleteCartItem(productDetails.cartId).subscribe((data: any) => {
    console.log("CartItem Deleted!!");
    this.removeItem(productDetails);
    this.service.removeCartItems(productDetails);
    this.calculateTotal(this.cartList);
  });
  
}
removeItem(cartItem: any){
  this.cartList = this.cartList.filter(item => item !== cartItem);
}
calculateTotal(orderDetails: any){
  console.log(orderDetails);
  let total = 0;
  for(let i = 0; i < this.cartList.length; i++){
    total += this.cartList[i].quantity * this.cartList[i].product.productPrice;
  }
  this.allTotal = total;
  console.log(this.allTotal);
}
}