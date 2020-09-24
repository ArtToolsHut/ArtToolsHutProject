import { Component, OnInit, HostListener } from '@angular/core';
//import { UserService } from './../user.service';
import { UserService } from './../user.service';
import { Router } from '@angular/router';
import { NotificationService } from '../noification.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  buyerId:any;
  products: any;
  images: any;

  isLogin: any;
  isShow: boolean;
  topPosToStartShowing = 100;
  product : any;
  wishList = Int16Array[3];

  /////////
  
  @HostListener('window:scroll')
  checkScroll() {
      
    // window의 scroll top
    // Both window.pageYOffset and document.documentElement.scrollTop returns the same result in all the cases. window.pageYOffset is not supported below IE 9.

    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;

  
    
    if (scrollPosition >= this.topPosToStartShowing) {
      this.isShow = true;
    } else {
      this.isShow = false;
    }
  }

  // TODO: Cross browsing
  gotoTop() {
    window.scroll({ 
      top: 0, 
      left: 0, 
      behavior: 'smooth' 
    });
  }
  /////////

  productId: any;
  constructor(private service: UserService,private router: Router,private notifyService: NotificationService) {
    this.images = {image1: 'assets/Images/101.jpg', image2: 'assets/Images/102.jpg'};
   }

   async ngOnInit() {
    this.service.getAllProducts().subscribe((result: any) => { console.log(result); this.products = result; });


   this.isLogin = this.service.getUserLogged();
  }


  onSelect(product) {
    this.router.navigate(['/productdetails', product.productId]);
    console.log(product.productId);
    //this.router.navigate(['/orderreceipt']);
  }

  onClickBrand(){
    this.router.navigate(['/viewall-brand']);
  }

  onClick(){
    this.router.navigate(['/viewall-sbcat']);
  }

  onClickSpecials(){
    this.router.navigate(['/viewall-specials']);
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

  async addToWish(product : any){
    this.service.addToWish(product);
    this.buyerId = parseInt(localStorage.getItem('buyerId'));
    this.wishList = [this.buyerId, product.productId];
    this.productId = parseInt(product.productId);
    console.log(this.productId);
    this.service.checkWishListItems(this.buyerId, this.productId).subscribe((data: any) => {
      console.log(data);
      if(data.length === 0){
        this.service.addWishListItem(this.wishList).subscribe((data: any) => {
          console.log('Added To WishList'); 
          this.showToastrWishList();
        });
      }
      else{
        this.showToasterAlert();
        console.log("product already exists");
      }
    });
   
      
      
    /*this.service.checkWishListItems(this.buyerId, this.productId).subscribe((result: any) => {
      console.log(result);
      this.wishItems = result;
    });*/

    localStorage.setItem('product',JSON.stringify(product));
    console.log(product.productId);
  }

  
}
