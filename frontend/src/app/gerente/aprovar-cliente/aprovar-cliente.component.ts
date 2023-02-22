import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from 'src/app/admin/services';
import { ClienteService } from 'src/app/cliente/services';
import { Cliente, Conta, Usuario } from 'src/app/shared';
import { GerenteService } from '../services';

@Component({
  selector: 'app-aprovar-cliente',
  templateUrl: './aprovar-cliente.component.html',
  styleUrls: ['./aprovar-cliente.component.css'],
})
export class AprovarClienteComponent implements OnInit {
  cliente!: Cliente;
  conta!: Conta;
  usuarioCliente!: Usuario;

  constructor(
    private gerenteService: GerenteService,
    private adminService: AdminService,
    private clienteService: ClienteService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.usuarioCliente = new Usuario();

    let id = +this.route.snapshot.params['id'];
    this.gerenteService.buscarContaPorId(id).subscribe(
      (conta: Conta) => {
        if (conta != null) {
          this.conta = conta;
          this.gerenteService.buscarClientePorId(conta.cliente?.id!).subscribe(
            (cliente: Cliente) => {
              if (cliente != null) {
                this.cliente = cliente;
              }
            }
          );
        }
      }
    );
  };


  aceitarCliente() {
    this.conta!.ativa = true;

    this.clienteService.atualizarConta(this.conta).subscribe(
      (conta: Conta) => {
        if (conta != null) {
          // let senha = Math.random().toString(36).slice(-8);
          let senha = 'cliente';
          const novoUsuario: Usuario = new Usuario(
            this.cliente.nome,
            this.cliente.email,
            senha,
            'CLIENTE',
          );
          this.adminService.inserirUsuario(novoUsuario).subscribe(
            (usuario: Usuario) => {
              if (usuario != null) {
                this.cliente.usuario = usuario;
                this.clienteService.atualizarCliente(this.cliente).subscribe(
                  (cliente: Cliente) => {
                    this.router.navigate(['/gerente']);
                  }
                );
              }
            }
          );
        }
      }
    );

  }
}