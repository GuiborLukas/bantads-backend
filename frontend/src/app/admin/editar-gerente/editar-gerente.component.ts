import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Gerente } from 'src/app/shared';

import { AdminService } from '../services';

@Component({
  selector: 'app-editar-gerente',
  templateUrl: './editar-gerente.component.html',
  styleUrls: ['./editar-gerente.component.css']
})
export class EditarGerenteComponent implements OnInit {

  @ViewChild('formGerente') formGerente!: NgForm;
  gerente!: Gerente;

  constructor(
    private adminService: AdminService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    let id = +this.route.snapshot.params['id'];
    const res = this.adminService.buscarGerentePorId(id).subscribe(
      (ger: Gerente) => {
        if (ger != null) {
          this.gerente = ger;
        } else {
          throw new Error('Gerente não encontrado: id = ' + id);
        }
      }
    );
  }

  atualizar(): void {
    if (this.formGerente.form.valid) {
      this.adminService.alterarGerente(this.gerente).subscribe(
        (gerente: Gerente) => {
          this.router.navigate(['/admin/listar-gerente']);
        }
      );
    }
  }

}
