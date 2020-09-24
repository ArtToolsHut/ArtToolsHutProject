import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  buyer:any;

  constructor() {
    this.buyer={buyerId:'', buyerName:'', buyerEmail:'', mobile:'', loginId:'', password:'', colony:'',
    town:'', city:'', pinCode:'' 
    };
  }

  ngOnInit(): void {
  }
  registerFrom(): void {
   //this.service.registerEmp(this.employee).subscribe((result: any) => { console.log(result); } );
    //console.log(registerForm);
    console.log(this.buyer);
  }
}


