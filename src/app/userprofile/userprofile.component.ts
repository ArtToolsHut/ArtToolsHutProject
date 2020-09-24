import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { NotificationService } from '../noification.service';

declare var jQuery: any;

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent implements OnInit {

  buyerId: any;
  buyer: any;
  editObject: any;
  imageUrl: string;
  fileToUpload: File = null;
  reader: FileReader;

  constructor(private router: Router,private service: UserService, private notifyService: NotificationService) { 
    this.editObject = {buyerId: '', buyerName: '', buyerEmail: '', mobileNo: '',userName: '', password: ''};
    this.imageUrl = '/assets/Images/th.jpg';
    
  }

  ngOnInit(): void {
    this.buyerId = localStorage.getItem('buyerId');
    console.log(this.buyerId);

    this.service.userProfile(this.buyerId).subscribe((result: any) => { console.log(result); this.buyer = result; });
    console.log(this.buyer);
  }

  
  logoutUser(): void{
    this.service.setUserLoggedOut();
    this.service.authenticate();
    this.router.navigate(['home']);
  }


  showEditPopup(buyer: any) {
    console.log("Edit PopUp");
    this.editObject = buyer;
    jQuery('#empModel').modal('show');
  }

  updateEmp() {
    this.service.updateUser(this.editObject).subscribe();
    console.log(this.editObject);
  }


  myorders(){
    this.buyerId = parseInt(localStorage.getItem('buyerId'));
    this.service.getMyOrderedItems(this.buyerId).subscribe((result: any) => { console.log(result); this.myorders = result; });
  }

  handleFileInput(file: FileList){
    this.fileToUpload = file.item(0);

    //show image preview
    this.reader = new FileReader();
    this.reader.readAsDataURL(this.fileToUpload);
    this.reader.onload = (event:any) =>{
      this.imageUrl = event.target.result;
    };
    this.service.uploadProfileImg(this.fileToUpload, this.buyerId).subscribe(
      data =>{
        console.log('done');
        //this.imageUrl = '/assets/Images/th.jpg';
        //this.router.navigate(['profile']);
      }
    );
  }  

}
