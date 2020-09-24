import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-sellerlogin',
  templateUrl: './sellerlogin.component.html',
  styleUrls: ['./sellerlogin.component.css']
})
export class SellerloginComponent implements OnInit {

  user: any;
  constructor(private router: Router, private service: UserService) {
    this.user = {userName: '', password: ''};
  }

  ngOnInit(): void {
  }

  async validateSeller(sellerLoginForm: any) {
    if (sellerLoginForm.userName === 'admin' && sellerLoginForm.password === 'admin') {
    this.service.setUserLoggedIn();
     this.router.navigate(['home']);
    }
    else {
      await this.service.sellerLogIn(sellerLoginForm.emailId, sellerLoginForm.password).then((result: any) => {
        if (result != null) {
          this.service.setUserLoggedIn();
          
          this.service.deauthenticate();
          this.service.userAuthenticate();
          this.service.sellerDeauthenticate();


          localStorage.setItem('sellerId', JSON.stringify(result.sellerId));
          
          this.router.navigate(['seller']);
        } else {
          console.log("Inavlid Credentials")
        }
      },
      (error) => {
        console.log(error);
      });
  }

  }
}


