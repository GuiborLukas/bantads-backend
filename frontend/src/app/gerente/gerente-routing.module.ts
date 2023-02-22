import { RecursiveAstVisitor } from "@angular/compiler";
import { Routes } from "@angular/router";
import { AprovarClienteComponent } from "./aprovar-cliente/aprovar-cliente.component";
import { ConsultarCincoMelhoresClientesComponent } from "./consultar-cinco-melhores-clientes/consultar-cinco-melhore-clientes.component";
import { ConsultarClienteComponent } from "./consultar-cliente/consultar-cliente.component";
import { ConsultarTodosClientesComponent } from "./consultar-todos-clientes/consultar-todos-clientes.component";
import { RejeitarClienteComponent } from "./rejeitar-cliente/rejeitar-cliente.component";
import { TelaInicialComponent } from "./tela-inicial/tela-inicial.component";
import { AuthGuard } from "../auth/auth.guard";

export const GerenteRoutes: Routes = [
  {
    path: 'gerente',
    canActivate: [AuthGuard],
    data: {
      role: 'GERENTE'
    },
    children: [
      {
        path: '',
        redirectTo: 'inicio',
        pathMatch: "full"
      },
      {
        path: 'inicio',
        component: TelaInicialComponent
      },
      {
        path: 'listar-clientes',
        component: ConsultarTodosClientesComponent
      },
      {
        path: 'listar-5-melhores-clientes',
        component: ConsultarCincoMelhoresClientesComponent
      },
      {
        path: 'aceitar-cliente/:id',
        component: AprovarClienteComponent
      },
      {
        path: 'recusar-cliente/:id',
        component: RejeitarClienteComponent
      },
      {
        path: 'consultar-cliente',
        component: ConsultarClienteComponent
      }
    ]
  },
];