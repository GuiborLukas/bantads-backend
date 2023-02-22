import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TelaInicialComponent } from './tela-inicial/tela-inicial.component';
import { RelatorioClientesComponent } from './relatorio-clientes/relatorio-clientes.component';
import { InserirGerenteComponent } from './inserir-gerente/inserir-gerente.component';
import { ListarGerentesComponent } from './listar-gerentes/listar-gerentes.component';
import { EditarGerenteComponent } from './editar-gerente/editar-gerente.component';

import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared';
import { NgxMaskModule, IConfig } from 'ngx-mask';

export const options: Partial<IConfig> | (() => Partial<IConfig>) = {};

@NgModule({
  declarations: [
    TelaInicialComponent,
    RelatorioClientesComponent,
    InserirGerenteComponent,
    ListarGerentesComponent,
    EditarGerenteComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    SharedModule,
    NgxMaskModule.forRoot()
  ],
})

export class AdminModule { }