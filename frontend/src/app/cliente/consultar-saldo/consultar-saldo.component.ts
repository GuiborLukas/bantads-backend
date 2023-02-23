import { Component, OnInit } from '@angular/core';
import { Cliente, Conta, Usuario } from 'src/app/shared';
import { ClienteService } from '../services';
import { LoginService } from 'src/app/auth/services/login.service';

@Component({
  selector: 'app-consultar-saldo',
  templateUrl: './consultar-saldo.component.html',
  styleUrls: ['./consultar-saldo.component.css']
})
export class ConsultarSaldoComponent implements OnInit {
  conta!: Conta;

  constructor(
    private clienteService: ClienteService
  ) {
    this.conta = new Conta();
  }

  ngOnInit(): void {
    if (this.clienteService.clienteLogado) {
      this.clienteService.buscarContaPorCliente(this.clienteService.clienteLogado).subscribe(
        (contas: Conta) => {
          if (contas != null) {
            this.conta = contas;
          }
        }
      );
    }
  }
}