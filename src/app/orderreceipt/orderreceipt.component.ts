import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';

@Component({
  selector: 'app-orderreceipt',
  templateUrl: './orderreceipt.component.html',
  styleUrls: ['./orderreceipt.component.css']
})
export class OrderreceiptComponent implements OnInit {

  address: any;
  retrievedData: any;
  buyerId: any;
  cartItems = [];
  cartId: any;
  cartID: any;
  total: any;
  images: any;
  orderId: any;
  orderDetails = Int16Array[4];
  orderStatus: any;
  totalAmount: any;
  //
  charges: any;
  buyer:any;
  orderDate: any;
  constructor(private route:ActivatedRoute,private router: Router, private service: UserService) {
    this.address = {name: '', mobileNo: '', street: '', town: '', state: '', pinCode: '', country: ''};
    this.cartItems = this.service.cartItems;
  }

  async ngOnInit(): Promise<void> {
    this.images = {image: 'assets/Images/logo.png'};
    this.buyerId = localStorage.getItem('buyerId');
    console.log(this.buyerId);
    this.address = JSON.parse(localStorage.getItem("address"));
    this.total = parseInt(localStorage.getItem('total'));

    //
    this.service.userProfile(this.buyerId).subscribe((result: any) => { console.log(result); this.buyer = result; 
      console.log(result.buyerEmail);
      this.service.confimationMail(result.buyerEmail).subscribe((data: any) => { console.log(data);});

    });
    if(this.total < 250) {
      this.charges = 40;
      this.totalAmount = this.total + this.charges;
    }
    else{
      this.charges = 'FREE';
      this.totalAmount = this.total;
    }
    //
    await this.service.getOrderItems(this.buyerId).then((data:any) => {
      var mostRecentDate = new Date(Math.max.apply(null, data.map( e => {
        return new Date(e.orderDate);
     })));
     var mostRecentObject = data.filter( e => { 
         var d = new Date( e.orderDate ); 
         return d.getTime() == mostRecentDate.getTime();
     })[0];
     console.log(mostRecentObject);
     console.log(data);
     this.orderId = mostRecentObject.orderId;
     this.orderDate = mostRecentObject.orderDate;
      localStorage.setItem("orderID", JSON.stringify(this.orderId));
     // localStorage.setItem("orderDate", JSON.)
    });

    this.orderId = parseInt(localStorage.getItem("orderID"));
    for(let i = 0; i < this.cartItems.length; i++){
      this.orderDetails = [this.orderId, this.cartItems[i].product.productId, this.cartItems[i].productQuantity];
      console.log(this.orderDetails);
      await this.service.addOrderDetails(this.orderDetails).then((data: any) => {
        console.log("Order Details Added");

      });
      this.service.removeCartItems(this.cartItems[i]);
    }
    
        
      
      
  }
}
