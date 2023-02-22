import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Conta } from 'src/app/shared';
import { ClienteService } from '../services';

@Component({
  selector: 'app-sacar',
  templateUrl: './sacar.component.html',
  styleUrls: ['./sacar.component.css']
})
export class SacarComponent implements OnInit {

  @ViewChild('formSacar') formSacar!: NgForm;
  conta!: Conta;
  public valorSaque! : number;

  constructor(
    private clienteService: ClienteService,
    private router: Router,
    private route: ActivatedRoute
  ) { 
    this.conta = new Conta();
    this.valorSaque = 0;
  }

  ngOnInit(): void {
    this.clienteService.buscarContaPorCliente(this.clienteService.clienteLogado).subscribe(
      (contas: Conta[]) => {
        if ((contas != null) && (contas.length > 0)) {
          this.conta = contas[0];
        }
      }
    );
  }

  sacar(): void {
    if ((this.formSacar.form.valid) && (this.valorSaque > 0)) {
      this.clienteService.sacar(this.valorSaque).subscribe(
        (status: boolean) => {
          // alert(status ? 'Saque realizado com sucesso.' : 'Erro ao realizar saque.');
          alert('Saque realizado com sucesso.');
          this.router.navigate(['/cliente']);
        }
      );

    }
  }
}