import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';
import { NotificationService } from '../noification.service';

@Component({
  selector: 'app-productdetails',
  templateUrl: './productdetails.component.html',
  styleUrls: ['./productdetails.component.css']
})
export class ProductdetailsComponent implements OnInit {
  products: any;
  id : any;
  buyerId:any;
  productPage: any;
  quantity: number=1;
  wishList = Int16Array[3];

  currentUrl: any;
  constructor(private route:ActivatedRoute,private router: Router, public service: UserService, private notifyService: NotificationService) { }
 
  ngOnInit(): void {

  console.log(this.router.url);
  console.log(window.location.href);
   
  this.currentUrl = this.router.url;
  localStorage.setItem('currentUrl',this.router.url);

    let productId = parseInt(this.route.snapshot.paramMap.get('productId'));
    console.log(productId);
    this.id = productId;
    this.service.getProductById(this.id).subscribe((result: any) => { console.log(result); this.productPage = result; });
    console.log(this.productPage);

    //this.service.getAllProducts().subscribe((result: any) => { console.log(result); this.products = result; });

  }

addToCart(productPage : any, quantity: any) {
  this.service.addToCart(productPage, quantity);
  this.buyerId = parseInt(localStorage.getItem('buyerId'));
  let productId = parseInt(this.route.snapshot.paramMap.get('productId'));
  this.service.checkCartItems(this.buyerId, productId).subscribe((data:any) => {
    console.log(data);
    if(data.length === 0 ){
      this.router.navigate(['/orderdetails', productPage.productId,quantity]);
      //this.service.addToCart(productPage,quantity);
      localStorage.setItem('product',JSON.stringify(productPage));
      console.log(productPage.productId);
      console.log(this.quantity);
    }
    else{
      this.showToasterAlert();
      console.log("product already exists");
      
    }
  });

}

async addToWishList(productPage : any){
  this.service.addToWishList(productPage);
  this.buyerId = parseInt(localStorage.getItem('buyerId'));
  this.wishList = [this.buyerId, productPage.productId];
  let productId = parseInt(this.route.snapshot.paramMap.get('productId'));
  this.service.checkWishListItems(this.buyerId, productId).subscribe((data: any) => {
    console.log(data);
    if(data.length === 0){
      this.service.addWishListItem(this.wishList).subscribe((data: any) => {
        console.log('Added To WishList');
        this.showToastrWishList();
      });
    }
    else{
      this.showToasterAlert();
      console.log("Product Already Exists");
    }
  });
   
  
   
  
  
  localStorage.setItem('product',JSON.stringify(productPage));
  console.log(productPage.productId);
}

showToasterSuccess(){
  this.notifyService.showSuccess("Registered successfully!!", "Registered")
}
showToasterAlert(){
  this.notifyService.showSuccess("Product Already Exists!!", "Product Exists")
}
showToastrWishList(){
  this.notifyService.showSuccess("Product Added to wishlist", "Product Added")
}

}