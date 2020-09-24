import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from './../user.service';
import { Router } from '@angular/router';
import { NotificationService } from '../noification.service';

@Component({
  selector: 'app-regseller',
  templateUrl: './regseller.component.html',
  styleUrls: ['./regseller.component.css']
})
export class RegsellerComponent implements OnInit {

 sellerdetails:any;
 address: any;
 mobileNo: any; 

 registerForm: FormGroup;
 submitted = false;


 constructor(private router: Router, private service: UserService, private notifyService: NotificationService, private formBuilder: FormBuilder) {

  this.sellerdetails ={sellerId:'', sellerName:'', sellerEmail:'', mobileNo:'', userName:'', password:'', address:{street:'', city:'', state:'',country:'', pincode:''} };
 
  }
  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      sellerName: ['', Validators.required],
      sellerEmail: ['', [Validators.required, Validators.email]],
      mobileNo: ['',  [Validators.required, Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]],
      street: ['', Validators.required],
      city: ['', Validators.required],
   
      pincode: ['', Validators.required],
     
      userName: ['', [Validators.required, Validators.minLength(4)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
      
        });
  }


  get f() { return this.registerForm.controls; }

  onSubmit(registerForm: any): void {
    this.submitted = true;
    if (this.registerForm.invalid) {
        return;
    }
    this.service.registerSeller(this.sellerdetails).subscribe((result: any) => { console.log(result); } );
    this.notifyService.showSuccess("Registered successfully!!", "Registered")
    console.log(this.sellerdetails);
    //this.router.navigate(['sellerlogin','1']);
    this.router.navigate(['sellerlogin']);
  }

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


getMobileNo(sellerdetails) {
  console.log(sellerdetails.mobileNo);
  this.mobileNo = sellerdetails.mobileNo;
  console.log(this.mobileNo);
  this.service.getBuyerMobile(this.mobileNo).subscribe((result: any) => { console.log(result); } );
}


}