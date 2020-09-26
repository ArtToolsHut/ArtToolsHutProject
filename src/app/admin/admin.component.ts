import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  pendingProducts: any;
  updateStatus: any;
  productStatus: any;
  updateReject: any;
  images: any;
  constructor(private router: Router, private userService: UserService) { }

  ngOnInit(): void {
    this.images = {image: 'assets/Images/logo.png'};
    this.userService.productsOfSeller().subscribe((result: any) => { console.log(result); this.pendingProducts = result; });
    console.log(this.pendingProducts);

  }
  logoutUser(): void{
    this.userService.setUserLoggedOut();
    this.router.navigate(['login']);
  }

  accept(product): any{
    console.log(product.productId);
    this.productStatus = "Accepted";
    product.comment = 'Null';
    this.updateStatus = [product.productId, this.productStatus, product.comment];
    console.log(this.updateStatus);
    this.userService.updateSellerStatus(this.updateStatus).subscribe((data: any) => {
      console.log("Status Updated");
    });
    }

  reject(product){
    console.log(product.productId);
    this.productStatus = "Rejected";
    this.updateStatus = [product.productId, this.productStatus, product.comment];
    console.log(this.updateStatus);
    this.userService.updateSellerStatus(this.updateStatus).subscribe((data: any) => {
      console.log("Status Updated");
    });
  }

}