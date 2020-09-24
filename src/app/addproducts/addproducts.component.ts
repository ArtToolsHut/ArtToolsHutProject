import { Component, OnInit } from '@angular/core';
import { UserService } from './../user.service';
import { Router } from '@angular/router';
import { NotificationService } from '../noification.service';

@Component({
  selector: 'app-addproducts',
  templateUrl: './addproducts.component.html',
  styleUrls: ['./addproducts.component.css']
})
export class AddproductsComponent implements OnInit {
  
 imageUrl: string;
 fileToUpload: File = null;
 reader: FileReader;
 sellerId: any;

 

  constructor(private router: Router, private service: UserService, private notifyService: NotificationService) {
    this.imageUrl = '/assets/Images/th.jpg';
    this.sellerId = localStorage.getItem('sellerId');
  
   }

  ngOnInit(): void {
/*
    this.service.registerSeller(this.sellerdetails).subscribe((data: any) => {console.log(data); this.sellerdetails = data;});
*/
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
    this.service.postFile(addProductsForm, this.fileToUpload, this.sellerId).subscribe(
      data =>{
        console.log('done');
        this.imageUrl = '/assets/Images/th.jpg';
      }
    );
    this.router.navigate(['seller']);
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

