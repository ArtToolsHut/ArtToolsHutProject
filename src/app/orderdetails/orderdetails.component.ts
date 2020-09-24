import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';
import { combineLatest } from 'rxjs';

@Component({
  selector: 'app-orderdetails',
  templateUrl: './orderdetails.component.html',
  styleUrls: ['./orderdetails.component.css']
})
export class OrderdetailsComponent implements OnInit {

  productDetails:any;
  id : any;
  productQuantity: any;
  subtotal: any;
  orderDetails: any;
  address: any;
  cartItems = [];
  cartItem: any;
  retrievedData: any;
  allTotal: any;
  buyerId: any;
  buyerDetails: any;
  cartProducts = Int16Array[4];
  show: boolean;
  cart : any;
  cartList = [];
  currentUrl: any;
  images: any;
  price:any;
  
  cartId: any;
  constructor(private route:ActivatedRoute,private router: Router, private service: UserService) {
    this.address = {name: '', mobileNo: '', street: '', town: '', state: '', pinCode: '', country: ''};
    this.cartItems = this.service.cartItems;
    console.log(this.service.cartItems);
   // this.cart = {product: {}, quantity: ''};
    
  }

  async ngOnInit(): Promise<void> {
    this.show = false;
    console.log(this.router.url);
    console.log( window.location.href);
    

    let productId = parseInt(this.route.snapshot.paramMap.get('productId'));
    console.log(productId);
    this.id = productId;
    this.service.getProductById(this.id).subscribe((result: any) => { console.log(result); this.productDetails = result;
    
    this.price = this.productDetails.productPrice;

   });
    console.log(this.productDetails);

    let quantity = parseInt(this.route.snapshot.paramMap.get('quantity'));
    console.log(quantity);
    this.productQuantity = quantity;
    this.buyerId = parseInt(localStorage.getItem('buyerId'));
    this.buyerDetails = localStorage.getItem('buyer');
    this.cartItem = {product: this.productDetails, productQuantity: this.productQuantity};

    this.calculateAllTotal(this.orderDetails);
    this.cartProducts = [this.buyerId, this.id, quantity];
    console.log(this.cartProducts);
    await this.service.addCartItems(this.cartProducts).then((data: any) => {
      console.log('Product Added to CartDetails');
      console.log(data);
    });

  }
  
  onSelect(productDetails) {
    //this.router.navigate(['/orderdetails', product.productId]);
    console.log(productDetails.productId);
    this.subtotal = this.productDetails.productPrice * this.productQuantity;
    console.log(this.subtotal);
    //this.router.navigate(['/orderreceipt', productDetails.productId,this.productQuantity,this.subtotal]);

  }

  calculateAllTotal(orderDetails: any[]) {
    let total = 0;
    console.log(this.cartItems);
    console.log(orderDetails);

    for(let i = 0; i < this.cartItems.length; i++) {
    //for(let i in orderDetails) {
      total = total +  (this.cartItems[i].product.productPrice * this.cartItems[i].productQuantity);
      console.log(total);
    }
    this.allTotal = total;
    localStorage.setItem('total', JSON.stringify(this.allTotal));
    console.log(this.allTotal);
  }

  getAddress(){
    this.router.navigate(['orders'])
  }

  deleteCartItem(productDetails: any){
    console.log(productDetails);

    let productId = parseInt(this.route.snapshot.paramMap.get('productId'));
    this.buyerId = parseInt(localStorage.getItem('buyerId'));
    this.service.checkCartItems(this.buyerId, productId).subscribe((result: any) => {
      console.log(result);
      this.cartId = result[0][2];
      this.service.deleteCartItem(this.cartId).subscribe((data: any) => {
        console.log("CartItem Deleted!!");
        this.removeItem(productDetails);
        this.service.removeCartItems(productDetails);
        this.calculateAllTotal(this.cartItems);
      });
    });
    
  }

  removeItem(cartItem: any){
    this.cartItems = this.service.cartItems.filter(item => item !== cartItem);
 }

}



