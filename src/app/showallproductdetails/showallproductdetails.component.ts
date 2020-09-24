import { Component, OnInit } from '@angular/core';
import { UserService } from './../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-showallproductdetails',
  templateUrl: './showallproductdetails.component.html',
  styleUrls: ['./showallproductdetails.component.css']
})
export class ShowallproductdetailsComponent implements OnInit {


  products: any;
  images: any;

  isLogin: any;
  product : any;

  constructor(private service: UserService,private router: Router) {
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


}