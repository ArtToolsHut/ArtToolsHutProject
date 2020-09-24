import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';



import { AuthGuard } from './auth.guard';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { RegbuyerComponent } from './regbuyer/regbuyer.component';
import { RegsellerComponent } from './regseller/regseller.component';
import { ProductsComponent } from './products/products.component';
import { CarouselComponent } from './carousel/carousel.component';
import { OrderdetailsComponent } from './orderdetails/orderdetails.component';
import { AddproductsComponent } from './addproducts/addproducts.component';
import { OrderreceiptComponent } from './orderreceipt/orderreceipt.component';
import { ProductdetailsComponent } from './productdetails/productdetails.component';
import { AdminComponent } from './admin/admin.component';
import { ShowallbuyerdetailsComponent } from './showallbuyerdetails/showallbuyerdetails.component';
import { ShowallsellerdetailsComponent } from './showallsellerdetails/showallsellerdetails.component';
import { SellerloginComponent } from './sellerlogin/sellerlogin.component';
import { ProductbybrandComponent } from './productbybrand/productbybrand.component';
import { ProductbytypeComponent } from './productbytype/productbytype.component';
import { ShowallproductdetailsComponent } from './showallproductdetails/showallproductdetails.component';
import { AdminaddproductsComponent } from './adminaddproducts/adminaddproducts.component';
import { UserprofileComponent } from './userprofile/userprofile.component';
import { SellerprofileComponent } from './sellerprofile/sellerprofile.component';
import { SellerComponent } from './seller/seller.component';
import { OrdersComponent } from './orders/orders.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { ViewallSBCatComponent } from './viewall-sbcat/viewall-sbcat.component';
import { ViewallBrandComponent } from './viewall-brand/viewall-brand.component';
import { ViewallSpecialsComponent } from './viewall-specials/viewall-specials.component';
import { CartDisplayComponent } from './cart-display/cart-display.component';
import { NgOtpInputModule } from 'ng-otp-input';
import { SellerproductsComponent } from './sellerproducts/sellerproducts.component';
import { FilterPipe } from './filter.pipe';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { MyordersComponent } from './myorders/myorders.component';


const appRoot: Routes = [{path: '', component: ProductsComponent},
                         {path: 'home', component: ProductsComponent},
                         //{path: 'login', component: LoginComponent},
                         {path: 'sellerlogin', component: SellerloginComponent},
                         {path: 'login/:no', component: LoginComponent},
                         {path: 'regbuyer', component: RegbuyerComponent},
                         {path: 'regseller', component: RegsellerComponent},
                         //{path: 'OrderreceiptComponent', component: OrderreceiptComponent},
                         //{path: 'orderdetails/:productId', component: OrderdetailsComponent},
                         {path: 'addproducts', component: AddproductsComponent},
                         {path: 'logout', component: LoginComponent},
                         {path: 'admin', component: AdminComponent},
                         {path: 'showallbuyerdetails', component: ShowallbuyerdetailsComponent},
                         {path: 'showallsellerdetails', component: ShowallsellerdetailsComponent},
                         {path: 'productdetails/:productId', component: ProductdetailsComponent},
                         {path: 'orderdetails/:productId/:quantity', component: OrderdetailsComponent},
                         {path: 'cart-display', component: CartDisplayComponent},
                         //{path: 'orderreceipt/:productId/:productQuantity/:subtotal', component: OrderreceiptComponent},
                         {path: 'orderreceipt', component: OrderreceiptComponent},
                         {path: 'orders', component: OrdersComponent},
                         {path: 'productbytype/:type', component: ProductbytypeComponent},
                         {path: 'productbybrand/:brand', component: ProductbybrandComponent},
                         {path: 'showallproductdetails', component: ShowallproductdetailsComponent},
                         {path: 'adminproduct', component: AdminaddproductsComponent},
                         {path: 'seller', component: SellerComponent},
                         {path: 'viewall-sbcat', component: ViewallSBCatComponent},
                         {path: 'viewall-specials', component: ViewallSpecialsComponent},
                         {path: 'viewall-brand', component: ViewallBrandComponent},
                         {path: 'profile', component: UserprofileComponent},
                         {path: 'sellerprofile', component: SellerprofileComponent},
                         {path: 'wishlist/:productId', component: WishlistComponent},
                         {path: 'sellerproducts', component: SellerproductsComponent},
                         {path: 'wishlist', component: WishlistComponent},
                         {path: 'forgotpassword', component: ForgotpasswordComponent},
                         {path:'about',component:AboutComponent},
                         {path:'myorders',component:MyordersComponent}

];


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    AboutComponent,
    RegbuyerComponent,
    RegsellerComponent,
    ProductsComponent,
    CarouselComponent,
    OrderdetailsComponent,
    AddproductsComponent,
    OrderreceiptComponent,
    ProductdetailsComponent,
    AdminComponent,
    ShowallbuyerdetailsComponent,
    ShowallsellerdetailsComponent,
    SellerloginComponent,
    ProductbybrandComponent,
    ProductbytypeComponent,
    ShowallproductdetailsComponent,
    AdminaddproductsComponent,
    UserprofileComponent,
    SellerprofileComponent,
    SellerComponent,
    OrdersComponent,
    WishlistComponent,
    ViewallSBCatComponent,
    ViewallBrandComponent,
    ViewallSpecialsComponent,
    CartDisplayComponent,
    SellerproductsComponent,
    FilterPipe,
    ForgotpasswordComponent,
    MyordersComponent,
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoot),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgOtpInputModule,
    ReactiveFormsModule ,
   
  ],
  providers: [AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { 

}


