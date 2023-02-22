import { Component, OnInit } from '@angular/core';
import { Conta, Transacao, TiposOperacao } from 'src/app/shared';
import { ClienteService } from '../services';

@Component({
  selector: 'app-consultar-extrato',
  templateUrl: './consultar-extrato.component.html',
  styleUrls: ['./consultar-extrato.component.css']
})
export class ConsultarExtratoComponent implements OnInit {
  conta!: Conta;
  extrato: Transacao[] = [];

  public TiposOperacao = TiposOperacao;

  constructor(
    private clienteService: ClienteService
  ) {
    this.conta = new Conta();
  }

  ngOnInit(): void {
    this.clienteService.buscarTodasTransacoes().subscribe(
      (transacoes: Transacao[]) => {
        this.extrato = transacoes;
      }
    );
  }
}


