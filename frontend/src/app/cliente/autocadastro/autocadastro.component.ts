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
  styleUrls: ['./autocadastro.component.css']
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
      this.router.navigate([`${this.loginService.usuarioLogado.perfil?.toLowerCase()}`]);
    }
  }

  ngOnInit(): void {
    this.cliente = new Cliente();
  }

  autocadastrar(): void {
    if (this.formCliente.form.valid) {
      //Preenche os dados do cliente 
      this.clienteService.inserirCliente(this.cliente).subscribe(
        (cli: Cliente) => {
          let numero = Math.floor(Math.random() * 1000);
          let limite: number = cli.salario! / 2;
          this.adminService.listarTodosGerentes().subscribe(
            (gerentes: Gerente[]) => {
              //Eh pra ser o gerente com menos clientes, mas aqui vou associar qualquer um
              // let randomGerente: Gerente = gerentes[Math.floor(Math.random() * gerentes.length)];
              // o ultimo no caso
              let randomGerente: Gerente = gerentes[gerentes.length - 1];
              //Cria a conta pro carinha
              const novaConta: Conta = new Conta(numero, cli, limite, 0, randomGerente);
              this.clienteService.salvarConta(novaConta).subscribe(
                (conta: Conta) => {
                  //Cria um usuario pra ele tbm 
                  this.router.navigate(['/login'], {
                    queryParams: { error: 'Solicitação de cadastro enviada! Aguarde a resposta em seu e-mail!' },
                  });
                }
              );
            }
          );
        }
      );
    }
  }
}
