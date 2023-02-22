import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/admin/services';
import { Cliente, Conta, Gerente, Usuario } from 'src/app/shared';

import { ClienteService } from '../services';
import { LoginService } from 'src/app/auth/services/login.service';

@Component({
  selector: 'app-autocadastro',
  templateUrl: './autocadastro.component.html',
  styleUrls: ['./autocadastro.component.css'],
})
export class AutocadastroComponent implements OnInit {
  @ViewChild('formCliente') formCliente!: NgForm;
  cliente!: Cliente;

  constructor(
    private loginService: LoginService,
    private clienteService: ClienteService,
    private adminService: AdminService,
    private router: Router
  ) {
    if (this.loginService.usuarioLogado) {
      this.router.navigate([
        `${this.loginService.usuarioLogado.perfil?.toLowerCase()}`,
      ]);
    }
  }

  ngOnInit(): void {
    this.cliente = new Cliente();
  }

  autocadastrar(): void {
    if (this.formCliente.form.valid) {
      //Preenche os dados do cliente
      this.clienteService
        .inserirCliente(this.cliente)
        .subscribe((cli: Cliente) => {
          if (cli != null) {
            this.cliente = new Cliente(
              cli.id,
              cli.nome,
              cli.email,
              cli.cpf,
              cli.endereco,
              cli.telefone,
              cli.salario,
              cli.usuario
            );
            this.router.navigate(['/login'], {
              queryParams: {
                error:
                  'Solicitação de cadastro enviada! Aguarde a resposta em seu e-mail!',
              },
            });
          }
        });
    }
  }
}
