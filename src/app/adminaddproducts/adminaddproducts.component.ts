import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { NotificationService } from '../noification.service';

@Component({
  selector: 'app-adminaddproducts',
  templateUrl: './adminaddproducts.component.html',
  styleUrls: ['./adminaddproducts.component.css']
})
export class AdminaddproductsComponent implements OnInit {

  imageUrl: string;
  fileToUpload: File = null;
  reader: FileReader;

  constructor(private router: Router, private service: UserService, private notifyService: NotificationService) {
    this.imageUrl = '/assets/Images/th.jpg';
  
   }

  ngOnInit(): void {
  }
  logoutUser(): void{
    this.service.setUserLoggedOut();
    this.router.navigate(['login']);
  }

 

  
handleFileInput(file: FileList){
  this.fileToUpload = file.item(0);

  //show image preview
  this.reader = new FileReader();
  this.reader.readAsDataURL(this.fileToUpload);
  this.reader.onload = (event:any) =>{
    this.imageUrl = event.target.result;
  };
}

OnSubmit(addProductsForm: any){
  this.service.adminAddProducts(addProductsForm, this.fileToUpload).subscribe(
    data =>{
      console.log('done');
      this.imageUrl = '/assets/Images/th.jpg';
    }
  );
  this.router.navigate(['admin']);
}
showToasterSuccess(){
  this.notifyService.showSuccess("Product Added Successfully!!", "Product added")
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

}