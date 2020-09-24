import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';
import { ViewChild,ElementRef } from '@angular/core';
import { NotificationService } from '../noification.service';

declare var jQuery: any;


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  user: any;

  loading = false;
  passcode: any;
  generatedPswd: any;
  buyerDet: any;
  currentUrl: any;
  num: any;

  //
  otp: string;
  showOtpComponent = true;
  generatedOtp: string;
  @ViewChild('ngOtpInput', { static: false}) ngOtpInput: any;
  config = {
    allowNumbersOnly: true,
    length: 4,
    isPasswordInput: true,
    disableAutoFocus: false,
    placeholder: '',
    inputStyles: {
      'width': '35px',
      'height': '35px'
    }
  };
  onOtpChange(otp) {
    this.otp = otp;
  }

  setVal(val) {
    this.ngOtpInput.setValue(val);
  }

  toggleDisable(){
    if(this.ngOtpInput.otpForm){
      if(this.ngOtpInput.otpForm.disabled){
        this.ngOtpInput.otpForm.enable();
      }else{
        this.ngOtpInput.otpForm.disable();
      }
    }
  }

  onConfigChange() {
    this.showOtpComponent = false;
    this.otp = null;
    setTimeout(() => {
      this.showOtpComponent = true;
    }, 0);
  }

  //

  @ViewChild('loginRef', {static: true }) loginElement: ElementRef;
  auth2: any;

  constructor(private route:ActivatedRoute, private router: Router, private service: UserService,private notifyService: NotificationService) {
    this.user = {userName: '', password: ''};

  }
  ngOnInit() {



    this.googleInitialize();
   console.log(this.router.url);
   console.log( window.location.href);
  }

  //LoginForm():void{
    //console.log(this.user);
  //}

  
  async validateUser(loginForm: any) {
    if (loginForm.emailId === 'admin' && loginForm.password === 'admin') {
      this.service.setUserLoggedIn();
      this.service.adminDeauthenticate();
      this.service.userAuthenticate();
      this.service.sellerAuthenticate();
      this.service.deauthenticate();
      this.router.navigate(['admin']);
    }
    else {
      await this.service.userLogIn(loginForm.emailId,loginForm.password).then((data: any) => {

        this.service.setUserLoggedIn();
          this.service.deauthenticate(); 
          this.service.userDeauthenticate();


          //
          let no = parseInt(this.route.snapshot.paramMap.get('no'));
          console.log(no);
          this.num = no;
          //
          
          localStorage.setItem('buyerId', JSON.stringify(data.buyerId));
          localStorage.setItem('buyer', JSON.stringify(data));
        

          if(this.num === 1) {
            this.router.navigate(['home']);
          }
          else {
            this.currentUrl = localStorage.getItem('currentUrl');
            this.router.navigate([this.currentUrl]);
          } 


    /*  await this.service.checkBuyerEmail(loginForm.emailId).then((data: any) => {
        if (data != null) {
          this.buyerDet = data;
          this.service.sendPasscode(loginForm.emailId).subscribe((result: any) => {this.generatedPswd = result});
            console.log("generated passcode " + this.generatedPswd);
            console.log("Passcode PopUp");
            jQuery('#passcodeModal').modal('show');
          
          
        } else {
          this.notifyService.showError("check username and password", "Invalid Credentials")
        }   */
      },
      (error) => {

      }); 
  }
}
//
async validateUserByNo(loginFormNo: any) {
  if (loginFormNo.mobileNo == 1234567890 ) {
    this.service.setUserLoggedIn();
    this.service.adminDeauthenticate();
    this.service.userAuthenticate();
    this.service.sellerAuthenticate();
    this.service.deauthenticate();
    this.router.navigate(['admin']);
  }
  else {
    await this.service.checkBuyerNum(loginFormNo.mobileNo).then((data: any) => {
      if (data != null) {
        this.buyerDet = data;
        this.service.getOTP(loginFormNo.mobileNo).subscribe((result: any) => {  this.generatedOtp = result });
        console.log(this.buyerDet);
        console.log("OTP PopUp");
        jQuery('#myModal').modal('show');

        this.service.setUserLoggedIn();
        this.service.deauthenticate(); 
        this.service.userDeauthenticate();

      } else {
        this.notifyService.showError("check mobile number", "Invalid Credentials")
      }
    },
    (error) => {
    });
}
}
//
googleInitialize() {
  window['googleSDKLoaded'] = () => {
    window['gapi'].load('auth2', () => {
      this.auth2 = window['gapi'].auth2.init({
        client_id: '731136061386-91ptjvgo3ciai6t70d2bf8tffupjo990.apps.googleusercontent.com',
        cookie_policy: 'single_host_origin',
        scope: 'profile email'
      });
      this.prepareLogin();
    });
  }
  (function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "https://apis.google.com/js/platform.js?onload=googleSDKLoaded";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'google-jssdk'));
}

prepareLogin() {
  this.auth2.attachClickHandler(this.loginElement.nativeElement, {},
    (googleUser) => {
      let profile = googleUser.getBasicProfile();
      console.log('Token || ' + googleUser.getAuthResponse().id_token);
      console.log('Image URL: ' + profile.getImageUrl());
      console.log('Email: ' + profile.getEmail());
      //this.router.navigate(['/home']);
     this.service.checkBuyerEmail(profile.getEmail()).then((data: any) => {
        if (data != null) {
          this.buyerDet = data;
          this.router.navigate(['/home']);

          this.service.setUserLoggedIn();
          this.service.deauthenticate(); 
          this.service.userDeauthenticate();

        } else {
          this.notifyService.showError("check username and password", "Invalid Credentials")
        }
      },
      (error) => {

      });
    }, (error) => {
      alert(JSON.stringify(error, undefined, 2));
    });
}

submitOtp(otp: any) {
    if(this.generatedOtp == otp) {
    
        this.service.setUserLoggedIn();
        this.service.deauthenticate(); 
        this.service.userDeauthenticate();
        
        //
        let no = parseInt(this.route.snapshot.paramMap.get('no'));
        console.log(no);
        this.num = no;
        //
        
        localStorage.setItem('buyerId', JSON.stringify(this.buyerDet.buyerId));
        localStorage.setItem('buyer', JSON.stringify(this.buyerDet));
        

       /* this.currentUrl = localStorage.getItem('currentUrl');
        console.log(this.currentUrl);
        this.router.navigate([this.currentUrl]); */

        if(this.num === 1) {
          this.router.navigate(['home']);
        }
        else {
          this.currentUrl = localStorage.getItem('currentUrl');
          this.router.navigate([this.currentUrl]);
        }
      }
      else {
        this.notifyService.showError("check otp", "Invalid Credentials")
      }


}

}