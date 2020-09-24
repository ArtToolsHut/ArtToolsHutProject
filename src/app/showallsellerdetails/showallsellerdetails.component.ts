import { Component, OnInit } from '@angular/core';
import { UserService } from './../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-showallsellerdetails',
  templateUrl: './showallsellerdetails.component.html',
  styleUrls: ['./showallsellerdetails.component.css']
})
export class ShowallsellerdetailsComponent implements OnInit {

  
  sellerdetails: any;

  constructor(private router: Router, private service: UserService) {

  }
  
    ngOnInit() {
     this.service.getAllSellerDetails().subscribe((result: any) => { console.log(result); this.sellerdetails = result; });
    }

    
   logoutUser(): void{
    this.service.setUserLoggedOut();
    this.router.navigate(['login']);
  }


}