import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';
import { NotificationService } from '../noification.service';

@Component({
  selector: 'app-myorders',
  templateUrl: './myorders.component.html',
  styleUrls: ['./myorders.component.css']
})
export class MyordersComponent implements OnInit {

  buyerId: any;
  orderIdList: any;
  orderId: any;
  idList: any;
  productIds: any;
  productsList:any;
  productsList2=[];
  products: any;
  constructor(private route:ActivatedRoute, private router: Router, private service: UserService,private notifyService: NotificationService) { 

  }

  async ngOnInit() {
    //buyerId
    this.buyerId = localStorage.getItem('buyerId');
    console.log(this.buyerId);

    await this.service.getOrderIds(this.buyerId).then(async (result: any) => { 
      console.log("OrderID List: ", result); 
      this.orderIdList = result;

       for(let i = 0; i < result.length; i++){
        this.orderId = result[i];
        console.log(this.orderId);


        await this.service.getProductIds(this.orderId).then((data: any) => {
          console.log("ProductId List: ", data);
          this.productIds=data;
          
          for(let j = 0; j < this.productIds.length; j++){
              this.service.getProductById(this.productIds[j][0]).subscribe((product: any) =>{ 
                console.log("Product: ", product) ;
                this.products=product;
                this.productsList={orderId:this.productIds[j][4],product:this.products,quantity:this.productIds[j][1]};
                this.productsList2.push(this.productsList);
              
            });
              }
             console.log(this.productsList2);

            
        });
      }
    });
   
  }
}

    
      
    