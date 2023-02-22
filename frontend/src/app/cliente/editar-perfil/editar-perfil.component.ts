import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/admin/services';
import { Cliente, Conta, Usuario } from 'src/app/shared';

import { ClienteService } from '../services';
import { LoginService } from 'src/app/auth/services/login.service';

@Component({
  selector: 'app-editar-perfil',
  templateUrl: './editar-perfil.component.html',
  styleUrls: ['./editar-perfil.component.css']
})
export class EditarPerfilComponent implements OnInit {

  @ViewChild('formCliente') formCliente!: NgForm;
  cliente!: Cliente;

  constructor(
    private clienteService: ClienteService,
    private loginService: LoginService,
    private adminService: AdminService,
    private router: Router) { }

  ngOnInit(): void {
    this.cliente = this.clienteService.clienteLogado;
  }

  atualizar($event: any): void {
    $event.preventDefault();
    if (this.formCliente.form.valid) {
      let limite: number = this.cliente.salario! / 2;
      this.clienteService.buscarContaPorCliente(this.cliente).subscribe(
        (contas: Conta[]) => {
          if (contas != null) {
            let conta = contas[0];
            let saldoNegativo = (conta.saldo! < 0) ? conta.saldo! * (-1) : 0;

            conta.limite = (saldoNegativo > 0 && limite < saldoNegativo) ? saldoNegativo : limite;

            this.clienteService.atualizarConta(conta).subscribe(
              (conta: Conta) => {
                this.clienteService.atualizarCliente(this.cliente).subscribe(
                  (cliente: Cliente) => {
                    if (cliente != null) {
                      let usu: Usuario = cliente.usuario!;
                      usu!.email = cliente.email;
                      this.adminService.alterarUsuario(usu).subscribe();
                      alert('Cliente atualizado com sucesso');
                      this.router.navigate(['/cliente']);
                    }
                  }
                );
              }
            )
          }
        }
      );

    }
  }
}
