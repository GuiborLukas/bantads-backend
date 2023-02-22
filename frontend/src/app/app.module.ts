import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AdminModule } from './admin';
import { AdminService } from './admin/services';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { ClienteModule } from './cliente';
import { ClienteService } from './cliente/services';
import { GerenteModule } from './gerente';
import { GerenteService } from './gerente/services';
import { ErrorHandlerService } from "./httperror-handler/services";
import { SharedModule } from './shared';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CurrencyMaskConfig, CurrencyMaskModule, CURRENCY_MASK_CONFIG } from 'ng2-currency-mask';

export const CustomCurrencyMaskConfig: CurrencyMaskConfig = {
  align: "left",
  allowNegative: true,
  decimal: ",",
  precision: 2,
  prefix: "R$ ",
  suffix: "",
  thousands: "."
};

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AdminModule,
    ClienteModule,
    GerenteModule,
    SharedModule,
    AuthModule,
    HttpClientModule,
    CurrencyMaskModule
  ],
  providers: [
    AdminService,
    ClienteService,
    GerenteService,
    { provide: CURRENCY_MASK_CONFIG, useValue: CustomCurrencyMaskConfig },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorHandlerService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
