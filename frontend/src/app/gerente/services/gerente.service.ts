import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { filter, Observable } from 'rxjs';
import { Cliente, Conta, Gerente, Usuario } from 'src/app/shared';
import { environment } from 'src/environments/environment';
import { __values } from 'tslib';

const LS_CHAVE: string = "gerentes";

@Injectable({
  providedIn: 'root'
})

export class GerenteService {

  private BASE_URL_GERENTE = environment.BASE_URL + "/gerentes";
  private BASE_URL_CLIENTE = environment.BASE_URL + "/clientes";
  private BASE_URL_CONTA = environment.BASE_URL + "/contas";

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  public get gerenteLogado(): Gerente {
    let gerenteLogado = localStorage[LS_CHAVE];
    return gerenteLogado ? JSON.parse(gerenteLogado) : null;
  }

  public set gerenteLogado(gerente: Gerente) {
    localStorage[LS_CHAVE] = JSON.stringify(gerente)
  }

  constructor(private httpClient: HttpClient) { }

  recusar(cliente: Cliente): void {

  }

  buscarClientePorId(idCliente: number): Observable<Cliente> {
    return this.httpClient.get<Cliente>(`${this.BASE_URL_CLIENTE}/${idCliente}`, this.httpOptions);
  }

  listarContas(): Observable<Conta[]> {
    return this.httpClient.get<Conta[]>(this.BASE_URL_CONTA, this.httpOptions);
  }

  buscaContasPorGerente(gerente: Gerente): Observable<Conta[]> {
    return this.httpClient.get<Conta[]>(this.BASE_URL_CONTA + `?gerenteId=${gerente.id}`, this.httpOptions);
  }

  buscaContasInativasPorGerente(gerente: Gerente): Observable<Conta[]> {
    return this.httpClient.get<Conta[]>(this.BASE_URL_CONTA + `contas?gerente.id=${gerente.id}&ativa=false`, this.httpOptions);
  }

  buscarContaPorId(contaId: number): Observable<Conta> {
    return this.httpClient.get<Conta>(this.BASE_URL_CONTA +  `contas/${contaId}`, this.httpOptions);
  }

  buscarGerentePorUsuario(usuario: Usuario): Observable<Gerente[]> {
    return this.httpClient.get<Gerente[]>(this.BASE_URL_GERENTE + `gerentes?usuario.id=${usuario.id}`, this.httpOptions);
  }

  inserirUsuárioCliente(user: Usuario): Observable<Usuario> {
    return this.httpClient.post<Usuario>(this.BASE_URL_GERENTE + 'usuarios', JSON.stringify(user), this.httpOptions);
  }


}
