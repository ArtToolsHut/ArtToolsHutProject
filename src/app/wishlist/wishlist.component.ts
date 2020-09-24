import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';
import { combineLatest } from 'rxjs';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {

  
  productDetails:any;
  id : any;
  buyerId: any;
  wishlist: any;
  wishItems = [];
  retrievedData: any;
  wishListItems = [];
  wishItem: any;
  wishListId: any;
  
  products: any;
  constructor(private route:ActivatedRoute,private router: Router, private service: UserService) {
    this.wishItems = this.service.wishItems;
    console.log(this.service.wishItems);
   }
  async ngOnInit() {
   //await this.service.getAllProducts().then((result: any) => { console.log(result); this.products = result; });
  
   /*let productId = parseInt(this.route.snapshot.paramMap.get('productId'));
    console.log(productId);
    this.id = productId;*/
    this.buyerId = parseInt(localStorage.getItem('buyerId'));
    await this.service.getWishListItems(this.buyerId).then((data: any) => {
      for(let i = 0; i < data.length; i++){
        this.id = data[i][1];
        this.wishListId = data[i][0];
        this.service.getProductById(this.id).subscribe((result: any) => { 
          console.log(result);
          this.productDetails = result;
          this.wishItem = {id: this.wishListId, product: this.productDetails}; 
          this.wishListItems.push(this.wishItem); });
        
    //console.log(this.productDetails);
      }
    });
    console.log(this.wishListItems);
  
  }


  onSelect(products) {
    this.router.navigate(['/productdetails', products.id]);
    console.log(products.id);

  }

  deleteWishListItem(products: any){
    console.log(products);
    this.service.deleteWishListItem(products.id).subscribe((data: any) => {
      console.log(products.id);
      console.log("WishListItem Deleted!!");
      this.removeItem(products);
    });
  }
  removeItem(wishItem: any){
    this.wishListItems = this.wishListItems.filter(item => item !== wishItem);
 }

}