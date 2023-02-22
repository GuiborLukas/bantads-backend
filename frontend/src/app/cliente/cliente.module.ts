import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { NgxMaskModule } from 'ngx-mask';

import { SharedModule } from '../shared';
import { AutocadastroComponent } from './autocadastro/autocadastro.component';
import { ConsultarExtratoComponent } from './consultar-extrato/consultar-extrato.component';
import { ConsultarSaldoComponent } from './consultar-saldo/consultar-saldo.component';
import { DepositarComponent } from './depositar/depositar.component';
import { EditarPerfilComponent } from './editar-perfil/editar-perfil.component';
import { SacarComponent } from './sacar/sacar.component';
import { TelaInicialClienteComponent } from './tela-inicial-cliente/tela-inicial-cliente.component';
import { TransferirComponent } from './transferir/transferir.component';


@NgModule({
  declarations: [
    AutocadastroComponent,
    TelaInicialClienteComponent,
    EditarPerfilComponent,
    DepositarComponent,
    SacarComponent,
    TransferirComponent,
    ConsultarSaldoComponent,
    ConsultarExtratoComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule,
    FormsModule,
    NgxMaskModule.forRoot(),
    CurrencyMaskModule
  ]
})
export class ClienteModule { }
