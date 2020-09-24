import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  title = 'angular-text-search-highlight';
  searchText = '';
  image: any;
  productDetails=[];
  productDetail:any;
  c:any;
products:any;
  constructor(private router: Router, public userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAllProductNames().subscribe((result: any) => { console.log(result); this.products = result; });
    
    
    
    
    this.userService.getForCart().subscribe(result => this.productDetails.push(result));
    this.image = {image1: 'assets/Images/logo.png'};
  }
  
  onSelect(c) {
    
    this.userService.getProductByName(c).subscribe((result: any) => { console.log(result.productId); this.productDetail = result; 
    this.router.navigate(['/productdetails',result.productId])});
    
    console.log(this.productDetail);
   
  }

  logoutUser(): void{
    this.userService.setUserLoggedOut();
    this.userService.authenticate();
    this.userService.userAuthenticate();
    this.userService.adminAuthenticate();
    this.userService.sellerAuthenticate();
    this.router.navigate(['home']);
  }
  validateUser(filled: any) {
    if (filled.search === 'login') {
     this.router.navigate(['login']);
    }
    else if (filled.search === 'home'){
      this.router.navigate(['home']);
    }
    else {
      alert('Invalid');
  }
  }

}