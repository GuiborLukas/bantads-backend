import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/auth/services/login.service';
import { Cliente, Conta, Gerente } from 'src/app/shared';
import { GerenteService } from '../services';

@Component({
  selector: 'app-consultar-todos-clientes',
  templateUrl: './consultar-todos-clientes.component.html',
  styleUrls: ['./consultar-todos-clientes.component.css']
})
export class ConsultarTodosClientesComponent implements OnInit {
  contas: Conta[] = [];

  constructor(private gerenteService: GerenteService, private loginService: LoginService) { }

  ngOnInit(): void {
    this.gerenteService.buscaContasPorGerente(this.gerenteService.gerenteLogado).subscribe(
      (contas: Conta[]) => {
        if (contas != null) {
          this.contas = contas.sort((a, b) => (a.cliente?.nome! < b.cliente?.nome!) ? -1 : 1);
        }
      }
    );
  }

  pesquisarCliente(valor: string): void {

  }

}
