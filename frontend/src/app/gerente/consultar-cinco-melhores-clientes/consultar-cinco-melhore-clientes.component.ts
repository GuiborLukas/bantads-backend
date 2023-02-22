import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/shared';
import { GerenteService } from '../services';

@Component({
  selector: 'app-consultar-cinco-melhores-clientes',
  templateUrl: './consultar-cinco-melhores-clientes.component.html',
  styleUrls: ['./consultar-cinco-melhores-clientes.component.css']
})
export class ConsultarCincoMelhoresClientesComponent implements OnInit {

  clientes: Cliente[] = [];

  constructor(private gerenteService: GerenteService) { }

  ngOnInit(): void {
    this.clientes = this.listar5Melhores();
  }

  listar5Melhores(): Cliente[] {
    return [];
  }

}
