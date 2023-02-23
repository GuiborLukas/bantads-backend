import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente, Conta, Gerente} from 'src/app/shared';
import { environment } from 'src/environments/environment';

const LS_CHAVE: string = "gerentes";

@Injectable({
  providedIn: 'root'
})

export class AdminService {
  BASE_URL_GERENTE = `${environment.BASE_URL}/gerentes`;
  BASE_URL_CLIENTE = `${environment.BASE_URL}/clientes`;
  BASE_URL_CONTAS = `${environment.BASE_URL}/contas`;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(
    private httpClient: HttpClient
  ) { }

  inserirGerente(gerente: Gerente): Observable<Gerente> {
    return this.httpClient.post<Gerente>(this.BASE_URL_GERENTE, JSON.stringify(gerente), this.httpOptions);
  }

  removerGerente(idGerente: number): Observable<boolean> {
    return this.httpClient.delete<boolean>(this.BASE_URL_GERENTE + `/${idGerente}`, this.httpOptions);
  }

  listarTodosGerentes(): Observable<Gerente[]> {
    return this.httpClient.get<Gerente[]>(this.BASE_URL_GERENTE, this.httpOptions);
  }

  alterarGerente(ger: Gerente): Observable<Gerente> {
    return this.httpClient.put<Gerente>(this.BASE_URL_GERENTE + `/${ger.id}`, JSON.stringify(ger), this.httpOptions);
  }

  buscarGerentePorId(idGerente: number): Observable<Gerente> {
    return this.httpClient.get<Gerente>(this.BASE_URL_GERENTE + `/${idGerente}`, this.httpOptions);
  }

  listarTodosClientes(): Observable<Cliente[]> {
    return this.httpClient.get<Cliente[]>(this.BASE_URL_CLIENTE, this.httpOptions);
  }

  listarTodasContas(): Observable<Conta[]> {
    return this.httpClient.get<Conta[]>(this.BASE_URL_CONTAS, this.httpOptions);
  }

  buscaContasPorGerente(gerente: Gerente): Observable<Conta[]> {
    return this.httpClient.get<Conta[]>(this.BASE_URL_GERENTE + `/${gerente.id}/meusclientes` , this.httpOptions);
  }

}
