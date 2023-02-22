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
    private router: Router) { }

  ngOnInit(): void {
    this.cliente = this.clienteService.clienteLogado;
  }

  atualizar($event: any): void {
    $event.preventDefault();
    if (this.formCliente.form.valid) {
      this.clienteService.atualizarCliente(this.cliente).subscribe(
        (cliente: Cliente) => {
          if (cliente != null) {
            alert('Cliente atualizado com sucesso');
            this.router.navigate(['/cliente']);
          }
        }
      );
    }
  }
}
