import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Conta, TiposOperacao, Transacao, Usuario } from 'src/app/shared';
import { Cliente } from 'src/app/shared/models/cliente.model';
import { environment as env } from 'src/environments/environment';

const LS_CHAVE: string = "clientes";
const LS_CHAVE_CONTA: string = "contas";

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  BASE_URL_CLIENTE = env.BASE_URL + "clientes/";
  BASE_URL_CONTA = env.BASE_URL + "contas/";

  constructor(
    private httpClient: HttpClient
  ) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  public get clienteLogado(): Cliente {
    let clienteLogado = localStorage[LS_CHAVE];
    return clienteLogado ? JSON.parse(clienteLogado) : null;
  }

  public set clienteLogado(cliente: Cliente) {
    localStorage[LS_CHAVE] = JSON.stringify(cliente)
  }

  public get contaClienteLogado(): Conta {
    let contaClienteLogado = localStorage[LS_CHAVE_CONTA];
    return contaClienteLogado ? JSON.parse(contaClienteLogado) : null;
  }

  public set contaClienteLogado(conta: Conta) {
    localStorage[LS_CHAVE_CONTA] = JSON.stringify(conta)
  }

  inserirCliente(cliente: Cliente): Observable<Cliente> {
    return this.httpClient.post<Cliente>(this.BASE_URL_CLIENTE, JSON.stringify(cliente), this.httpOptions);
  }

  buscarClientePorUsuario(usuario: Usuario): Observable<Cliente> {
    return this.httpClient.get<Cliente>(this.BASE_URL_CLIENTE + `${usuario.id}`, this.httpOptions); 
  }
  atualizarCliente(cliente: Cliente): Observable<Cliente> {
    this.clienteLogado = cliente;
    return this.httpClient.put<Cliente>(this.BASE_URL_CLIENTE + `${cliente.id}`, JSON.stringify(cliente), this.httpOptions);
  }

  buscarContaPorCliente(cliente: Cliente): Observable<Conta> {
    let conta = this.httpClient.get<Conta>(this.BASE_URL_CONTA + `${cliente.id}`, this.httpOptions);
    conta.subscribe((conta: Conta) => this.contaClienteLogado = conta);
    return conta;
  }

  sacar(valor: number): Observable<boolean> {
    let jsonValor = {"valor" : valor};
    return this.httpClient.post<boolean>(this.BASE_URL_CONTA + `${this.contaClienteLogado.id}` + "/saque", jsonValor, this.httpOptions);
  }

  depositar(valor: number): Observable<boolean> {
    let jsonValor = {"valor" : valor};
    return this.httpClient.post<boolean>(this.BASE_URL_CONTA + `${this.contaClienteLogado.id}` + "/deposito", jsonValor, this.httpOptions);
  }

  transferir(numeroContaDestino: number, valor: number): Observable<boolean> {
    let jsonValor = {"valor" : valor};
    return this.httpClient.post<boolean>(this.BASE_URL_CONTA + `${this.contaClienteLogado.id}` + `/transferencia?destino=${numeroContaDestino}`, jsonValor, this.httpOptions);
  }

  buscarTodasTransacoes(): Observable<Transacao[]> {
    return this.httpClient.get<Transacao[]>(this.BASE_URL_CONTA + `${this.contaClienteLogado.id}` + "/extrato" , this.httpOptions);
  }

}

  // salvarConta(novaConta: Conta) {
  //   return this.httpClient.post<Conta>(this.BASE_URL + 'contas', JSON.stringify(novaConta), this.httpOptions);
  // }

  // buscarContaPorNumero(numero: number): Observable<Conta[]> {
  //   return this.httpClient.get<Conta[]>(this.BASE_URL + `contas?numero=${numero}`, this.httpOptions);
  // }

  // atualizarConta(conta: Conta): Observable<Conta> {
  //   return this.httpClient.put<Conta>(this.BASE_URL + `contas/${conta.id}`, JSON.stringify(conta), this.httpOptions);
  // }

  // registrarTransacao(tipo: TiposOperacao, valor: number, contaOrigem?: Conta, contaDestino?: Conta): Observable<Transacao> {
  //   let transacao = new Transacao(new Date(), tipo, valor, contaOrigem, contaDestino);
  //   return this.httpClient.post<Transacao>(this.BASE_URL + 'transacoes', JSON.stringify(transacao), this.httpOptions);
  // }

  // buscarTransacoesPorConta(conta: Conta): Observable<Transacao[]> {
  //   return this.httpClient.get<Transacao[]>(this.BASE_URL_CONTA + `${conta.id}` + "/extrato" , this.httpOptions);
  // }

