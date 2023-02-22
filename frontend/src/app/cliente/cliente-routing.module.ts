import { Routes } from "@angular/router";
import { ConsultarExtratoComponent } from "./consultar-extrato/consultar-extrato.component";
import { ConsultarSaldoComponent } from "./consultar-saldo/consultar-saldo.component";
import { DepositarComponent } from "./depositar/depositar.component";
import { EditarPerfilComponent } from "./editar-perfil/editar-perfil.component";
import { SacarComponent } from "./sacar/sacar.component";
import { TelaInicialClienteComponent } from "./tela-inicial-cliente/tela-inicial-cliente.component";
import { AuthGuard } from "../auth/auth.guard";
import { TransferirComponent } from "./transferir/transferir.component";

export const ClienteRoutes: Routes = [
  {
    path: 'cliente',
    canActivate: [AuthGuard],
    data: {
      role: 'CLIENTE'
    },
    children: [
      {
        path: '',
        redirectTo: 'inicial',
        pathMatch: 'full'
      },
      {
        path: 'consultar-extrato',
        component: ConsultarExtratoComponent
      },
      {
        path: 'consultar-saldo',
        component: ConsultarSaldoComponent
      },
      {
        path: 'depositar',
        component: DepositarComponent
      },
      {
        path: 'editar-perfil',
        component: EditarPerfilComponent
      },
      {
        path: 'sacar',
        component: SacarComponent
      },
      {
        path: 'transferir',
        component: TransferirComponent
      },
      {
        path: 'inicial',
        component: TelaInicialClienteComponent
      }
    ],
  }
];