import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';
import { NotificationService } from '../noification.service';

@Component({
  selector: 'app-productbybrand',
  templateUrl: './productbybrand.component.html',
  styleUrls: ['./productbybrand.component.css']
})
export class ProductbybrandComponent implements OnInit {

  products: any;
  buyerId: number;
  wishList=Int16Array[3];
  productId: number;
  constructor(private route:ActivatedRoute,private router: Router, private service: UserService,private notifyService: NotificationService) { }

  ngOnInit(): void {
    let brand = this.route.snapshot.paramMap.get('brand');
    console.log(brand);

    this.service.getAllProductsByBrand(brand).subscribe((result: any) => { console.log(result); this.products = result; });
    console.log(this.products);
  }

  onSelect(product) {
    this.router.navigate(['/productdetails', product.productId]);
    console.log(product.productId);
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