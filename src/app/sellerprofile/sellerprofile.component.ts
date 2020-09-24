import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

declare var jQuery: any;

@Component({
  selector: 'app-sellerprofile',
  templateUrl: './sellerprofile.component.html',
  styleUrls: ['./sellerprofile.component.css']
})
export class SellerprofileComponent implements OnInit {

  
  sellerId: any;
  seller: any;
  editObject: any;
  imageUrl: string;
  fileToUpload: File = null;
  reader: FileReader;
  constructor(private router: Router,private service: UserService) {
    this.editObject = {sellerId: '', sellerName: '', sellerEmail: '', mobileNo: '',userName: '', password: ''};
    this.imageUrl = '/assets/Images/th.jpg';
   }

  ngOnInit(): void {
    this.sellerId = localStorage.getItem('sellerId');
    console.log(this.sellerId);

    this.service.sellerProfile(this.sellerId).subscribe((result: any) => { console.log(result); this.seller = result; });
    console.log(this.seller);
  }

  showEditPopup(seller: any) {
    console.log("Edit PopUp");
    this.editObject = seller;
    jQuery('#empModel').modal('show');
  }

  updateEmp() {
    this.service.updateSeller(this.editObject).subscribe();
    console.log(this.editObject);
  }

  handleFileInput(file: FileList){
    this.fileToUpload = file.item(0);

    //show image preview
    this.reader = new FileReader();
    this.reader.readAsDataURL(this.fileToUpload);
    this.reader.onload = (event:any) =>{
      this.imageUrl = event.target.result;
    };
    this.service.uploadProfileImgS(this.fileToUpload, this.sellerId).subscribe(
      data =>{
        console.log('done');
        //this.imageUrl = '/assets/Images/th.jpg';
        //this.router.navigate(['profile']);
      }
    );
  } 

  
  logoutUser(): void{
    this.service.setUserLoggedOut();
    this.service.authenticate();
    this.router.navigate(['home']);
  }

}
