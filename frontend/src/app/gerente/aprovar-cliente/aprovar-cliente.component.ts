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
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.usuarioCliente = new Usuario();

    let id = +this.route.snapshot.params['id'];
    this.gerenteService.buscarClientePorId(id).subscribe(
      (cliente: Cliente) => {
        if (cliente != null) {
          this.cliente = cliente;
        }
      }
    );
  };


  aceitarCliente() {
    this.gerenteService.aceitarCliente(this.cliente.id!).subscribe(
      (status: boolean) => {
        alert('Cliente aprovado com sucesso.');
        this.router.navigate(['/gerentes']);
      }
    );
  }
}