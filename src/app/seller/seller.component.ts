import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-seller',
  templateUrl: './seller.component.html',
  styles: [
  ]
})
export class SellerComponent implements OnInit {
image:any;
  constructor() {
    this.image= {image1:'assets/Images/myproducts.jpeg', image2:'assets/Images/new product.jpeg'};
   }

  ngOnInit(): void {
   
    //this.images = {image1: 'assets/Images/101.jpg', image2: 'assets/Images/102.jpg'};
  }

}
