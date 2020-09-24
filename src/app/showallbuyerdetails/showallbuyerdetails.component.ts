import { Component, OnInit } from '@angular/core';
import { UserService } from './../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-showallbuyerdetails',
  templateUrl: './showallbuyerdetails.component.html',
  styleUrls: ['./showallbuyerdetails.component.css']
})
export class ShowallbuyerdetailsComponent implements OnInit {

  
  buyerdetails: any;

  constructor(private router: Router, private service: UserService) {

  }
  
    ngOnInit() {
     this.service.getAllBuyerDetails().subscribe((result: any) => { console.log(result); this.buyerdetails = result; });
    }
 
   logoutUser(): void{
     this.service.setUserLoggedOut();
     this.router.navigate(['login']);
   }
 

}