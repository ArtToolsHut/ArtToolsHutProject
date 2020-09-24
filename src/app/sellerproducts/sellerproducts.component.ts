import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';

@Component({
  selector: 'app-sellerproducts',
  templateUrl: './sellerproducts.component.html',
  styleUrls: ['./sellerproducts.component.css']
})
export class SellerproductsComponent implements OnInit {

  sellerId: any;
  sellerProducts: any;
  constructor(private route:ActivatedRoute,private router: Router, private service: UserService) { }

  ngOnInit(): void {

    this.sellerId = localStorage.getItem('sellerId');
    console.log(this.sellerId);

    this.service.productsBySellerId(this.sellerId).subscribe((result: any) => { console.log(result); this.sellerProducts = result; });
    console.log(this.sellerProducts);
    
  }

}
