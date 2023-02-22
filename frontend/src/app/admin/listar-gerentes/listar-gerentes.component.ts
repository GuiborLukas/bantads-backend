import { Component, OnInit } from '@angular/core';

import { Gerente } from 'src/app/shared';

import { AdminService } from '../services';

@Component({
  selector: 'app-listar-gerentes',
  templateUrl: './listar-gerentes.component.html',
  styleUrls: ['./listar-gerentes.component.css']
})
export class ListarGerentesComponent implements OnInit {

  gerentes: Gerente[] = [];

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.gerentes = this.listarTodos();
  }

  listarTodos(): Gerente[] {
    this.adminService.listarTodosGerentes().subscribe(
      (data: Gerente[]) => {
        if (data == null) {
          this.gerentes = []
        } else {
          this.gerentes = data;
        }
      });
    return this.gerentes;
  }

  remover($event: any, gerente: Gerente): void {
    $event.preventDefault();
    if (confirm(`Deseja realmente remover ${gerente.nome} ?`)) {
      this.adminService.removerGerente(gerente.id!).subscribe({
        next: (data: any) => {
          this.gerentes = this.listarTodos();
        }
      });
    }
  }

}
