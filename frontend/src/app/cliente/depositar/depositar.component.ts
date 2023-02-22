import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Cliente, Conta } from 'src/app/shared';
import { ClienteService } from '../services';
import { LoginService } from 'src/app/auth/services/login.service';

@Component({
  selector: 'app-depositar',
  templateUrl: './depositar.component.html',
  styleUrls: ['./depositar.component.css']
})
export class DepositarComponent implements OnInit {

  @ViewChild('formDepositar') formDepositar!: NgForm;
  cliente!: Cliente;
  conta!: Conta;
  public valorDeposito!: number;

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.valorDeposito = 0;
    this.clienteService.buscarContaPorCliente(this.clienteService.clienteLogado).subscribe(
      (contas: Conta[]) => {
        if ((contas != null) && (contas.length > 0)) {
          this.conta = contas[0];
        }
      }
    );
  }

  depositar(): void {
    if ((this.formDepositar.form.valid) && (this.valorDeposito > 0)) {
      this.clienteService.depositar(this.valorDeposito).subscribe(
        (result: boolean) => {
          alert('Depósito realizado com sucesso.');
          // alert(result ? 'Depósito realizado com sucesso.' : 'Erro ao realizar depósito.');
          this.router.navigate(['/cliente']);
        }
      );
    }
  };
}

