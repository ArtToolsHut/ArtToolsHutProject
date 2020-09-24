import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from './../user.service';
import { Router } from '@angular/router';
import { NotificationService } from '../noification.service';
import { ViewChild } from '@angular/core';


declare var jQuery: any;
@Component({
  selector: 'app-regbuyer',
  templateUrl: './regbuyer.component.html',
  styleUrls: ['./regbuyer.component.css']
})

export class RegbuyerComponent implements OnInit {

 buyerdetails:any;
  mobileNo: any; 

  registerForm: FormGroup;
  submitted = false;

  confirmpassword: any;
  //-------------------------------------------------
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


  constructor(private router: Router, private service: UserService, private notifyService: NotificationService, private formBuilder: FormBuilder) {
  
   this.buyerdetails={buyerId:'', buyerName:'', buyerEmail:'', mobileNo:'', userName:'',password:''};

  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
            buyerName: ['', Validators.required],
            buyerEmail: ['', [Validators.required, Validators.email]],
            mobileNo: ['',  [Validators.required, Validators.pattern("[0-9]{10}$")]],
            userName: ['', [Validators.required, Validators.minLength(4)]],
           password: ['', [Validators.required, Validators.minLength(6)]],
           // confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
        });
  }
  get f() { return this.registerForm.controls; }


  formValidation(registerForm: any,buyerdetails :any){
    this.submitted = true;

  if (registerForm.invalid) {
    return;
  }
  jQuery('#myModal').modal('show');

  console.log(buyerdetails.mobileNo);
  this.mobileNo = buyerdetails.mobileNo;
  console.log(this.mobileNo);
  this.service.getOTP(this.mobileNo).subscribe((result: any) => {  this.generatedOtp = result });
  }


getOtp(buyerdetails :any){
  
  }
  


register(registerForm: any): void {
  console.log(this.generatedOtp);
  console.log(this.otp);

  if(this.generatedOtp == this.otp) {
    this.service.registerBuyer(this.buyerdetails).subscribe((result: any) => { console.log(result);});
    this.notifyService.showSuccess("Registered successfully!!", "Registered")
    //console.log(registerForm)
    console.log(this.buyerdetails);
    //alert("Registration success");
    this.router.navigate(['login','1']);
  }
  else{
    this.notifyService.showError("Otp verification not successfull", "Not Registered")
  }  
}


  /*onSubmit(registerForm: any): void {
    this.submitted = true;

    // stop here if form is invalid
    if (registerForm.invalid) {
      return;
    }
    console.log(registerForm)
    //this.buyerdetails = registerForm;
    console.log(this.buyerdetails);
    this.service.registerBuyer(this.buyerdetails).subscribe((result: any) => { console.log(result); } );
    
    
    
    //alert("Registration success");
    this.router.navigate(['login','1']);

      //alert('SUCCESS!! :-)')
    
}*/

  showToasterSuccess(){
    this.notifyService.showSuccess("Registered successfully!!", "Registered")
  }

showToasterError(){
    this.notifyService.showError("Something is wrong", "ItSolutionStuff.com")
}

showToasterInfo(){
    this.notifyService.showInfo("This is info", "ItSolutionStuff.com")

}

showToasterWarning(){
    this.notifyService.showWarning("This is warning", "ItSolutionStuff.com")
}



getMobileNo(buyerdetails) {
  console.log(buyerdetails.mobileNo);
  this.mobileNo = buyerdetails.mobileNo;
  console.log(this.mobileNo);
  this.service.getBuyerMobile(this.mobileNo).subscribe((result: any) => { console.log(result); } );

}

}

