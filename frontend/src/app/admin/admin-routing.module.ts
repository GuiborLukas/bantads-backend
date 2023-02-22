import { Routes } from '@angular/router';

import { EditarGerenteComponent } from './editar-gerente/editar-gerente.component';
import { InserirGerenteComponent } from './inserir-gerente/inserir-gerente.component';
import { ListarGerentesComponent } from './listar-gerentes/listar-gerentes.component';
import { RelatorioClientesComponent } from './relatorio-clientes/relatorio-clientes.component';
import { TelaInicialComponent } from './tela-inicial/tela-inicial.component';
import { AuthGuard } from '../auth/auth.guard';


export const AdminRoutes: Routes = [
  {
    path: 'admin',
    canActivate: [AuthGuard],
    data: {
      role: 'ADMIN'
    },
    children: [
      {
        path: '',
        redirectTo: 'inicio',
        pathMatch: 'full',
      },
      {
        path: 'inicio',
        component: TelaInicialComponent
      },
      {
        path: 'listar-gerente',
        component: ListarGerentesComponent
      },
      {
        path: 'editar-gerente/:id',
        component: EditarGerenteComponent
      },
      {
        path: 'novo-gerente',
        component: InserirGerenteComponent
      },
      {
        path: 'relatorio-clientes',
        component: RelatorioClientesComponent
      }
    ],
  }
];
