import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Gerente, Usuario } from 'src/app/shared';

import { AdminService } from '../services';

@Component({
  selector: 'app-inserir-gerente',
  templateUrl: './inserir-gerente.component.html',
  styleUrls: ['./inserir-gerente.component.css']
})
export class InserirGerenteComponent implements OnInit {

  @ViewChild('formGerente') formGerente!: NgForm;
  gerente!: Gerente;

  constructor(
    private adminService: AdminService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.gerente = new Gerente();
  }

  inserir(): void {
    if (this.formGerente.form.valid) {

      //Chama a insercao de gerente que automaticamente vai criar um novo usuario pra ele
      this.adminService.inserirGerente(this.gerente).subscribe(
        (gerente: Gerente) => {
          this.gerente = gerente;
          this.router.navigate(['/admin/listar-gerente']);
        });

      // let senha = Math.random().toString(36).slice(-8);
      // // let senha = 'gerente';
      // const novoUsuario: Usuario = new Usuario(
      //   this.gerente.nome,
      //   this.gerente.email,
      //   senha,
      //   'GERENTE',
      // );
      // this.adminService.inserirUsuario(novoUsuario).subscribe(
      //   (usuario: Usuario) => {
      //     this.gerente.usuario = usuario;
      //     this.adminService.inserirGerente(this.gerente).subscribe(
      //       (gerente: Gerente) => {
      //         this.gerente = gerente;
      //         this.router.navigate(['/admin/listar-gerente']);
      //       });
      //   }
      // );
    }
  }

}
