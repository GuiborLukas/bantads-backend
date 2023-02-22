import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from 'src/app/shared';
import { GerenteService } from '../services';

@Component({
  selector: 'app-rejeitar-cliente',
  templateUrl: './rejeitar-cliente.component.html',
  styleUrls: ['./rejeitar-cliente.component.css']
})
export class RejeitarClienteComponent implements OnInit {

  cliente!: Cliente;

  constructor(
    private gerenteService: GerenteService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    let id = +this.route.snapshot.params['id'];
    const res = this.gerenteService.buscarClientePorId(id).subscribe(
      (cl: Cliente) => {
        if (cl != null) {
          this.cliente = cl;
        } else {
          throw new Error('Cliente não encontrado: id = ' + id);
        }
      }
    );
  }

  rejeitarCliente(){
    
  }

}
