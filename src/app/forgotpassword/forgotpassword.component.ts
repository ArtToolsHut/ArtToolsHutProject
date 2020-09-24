import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';
import { ViewChild,ElementRef } from '@angular/core';
import { NotificationService } from '../noification.service';

declare var jQuery: any;
@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.css']
})


export class ForgotpasswordComponent implements OnInit {
  generatedPswd: any;
  num: number;
  buyerDet: any;
  currentUrl: string;
  passcode: any;
 loginForm:any;
 


  constructor(private route:ActivatedRoute, private router: Router, private service: UserService,private notifyService: NotificationService) { }

  ngOnInit(): void {
    
  }
  
onSubmit(passcode: any){

  if(passcode === this.generatedPswd) {

        this.service.setUserLoggedIn();
        this.service.deauthenticate(); 
        this.service.userDeauthenticate();


        
        let no = parseInt(this.route.snapshot.paramMap.get('no'));
        console.log(no);
        this.num = no;
        
        
        localStorage.setItem('buyerId', JSON.stringify(this.buyerDet.buyerId));
        localStorage.setItem('buyer', JSON.stringify(this.buyerDet));
      
        this.router.navigate(['home']);
        /*
        if(this.num === 1) {
          this.router.navigate(['home']);
        }
        else {
          this.currentUrl = localStorage.getItem('currentUrl');
          this.router.navigate([this.currentUrl]);
        }*/
  }
  else{
    this.notifyService.showError("check passcode", "Invalid Credentials")
  }
}

validateUser(loginForm:any){
  this.service.checkBuyerEmail(loginForm.emailId).then((data: any) => {
    if (data != null) {
      this.buyerDet = data;
      this.service.sendPasscode(loginForm.emailId).subscribe((result: any) => {this.generatedPswd = result});
        console.log("generated passcode " + this.generatedPswd);
        console.log("Passcode PopUp");
        jQuery('#passcodeModal').modal('show');
      
      
    } else {
      this.notifyService.showError("check username and password", "Invalid Credentials")
    }   
  },
  (error) => {
  
  }); 
  }
}


