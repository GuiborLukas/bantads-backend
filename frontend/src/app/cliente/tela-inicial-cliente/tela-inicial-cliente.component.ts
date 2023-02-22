import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/auth/services/login.service';
import { Cliente, Conta } from 'src/app/shared';

import { ClienteService } from '../services';

@Component({
  selector: 'app-tela-inicial-cliente',
  templateUrl: './tela-inicial-cliente.component.html',
  styleUrls: ['./tela-inicial-cliente.component.css']
})
export class TelaInicialClienteComponent implements OnInit {
  conta!: Conta;

  constructor(
    private clienteService: ClienteService,
    private loginService: LoginService
  ) {
    this.conta = new Conta();
  }

  ngOnInit(): void {
    this.clienteService.buscarClientePorUsuario(this.loginService.usuarioLogado).subscribe(
      (clientes: Cliente[]) => {
        if ((clientes != null) && (clientes.length) > 0) {
          this.clienteService.clienteLogado = clientes[0];
          this.clienteService.buscarContaPorCliente(clientes[0]).subscribe(
            (contas: Conta[]) => {
              if ((contas != null) && (contas.length > 0)) {
                this.conta = contas[0];
              }
            }
          )
        }
      }
    );
  }
}
