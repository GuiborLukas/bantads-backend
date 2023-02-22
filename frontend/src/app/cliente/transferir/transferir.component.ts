import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Conta } from 'src/app/shared';
import { ClienteService } from '../services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transferir',
  templateUrl: './transferir.component.html',
  styleUrls: ['./transferir.component.css']
})
export class TransferirComponent implements OnInit {
  @ViewChild('formTransf') formTransf!: NgForm;
  conta!: Conta;
  contaDestino!: number;
  valorTransf!: number;

  constructor(
    private clienteService: ClienteService,
    private router: Router,
  ) {
    this.conta = new Conta();
    this.valorTransf = 0;
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

  transferir(): void {
    if (this.formTransf.form.valid) {
      if (this.valorTransf > 0) {
        this.clienteService.transferir(this.contaDestino, this.valorTransf).subscribe(
          (status: boolean) => {
            // alert(status ? 'Transferência realizada com sucesso.' : 'Erro ao realizar transferência.');
            alert('Transferência realizada com sucesso.');
            this.router.navigate(['/cliente']);
          }
        );

      }
    }
  }


}
