import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Conta, Gerente, Usuario } from 'src/app/shared';
import { environment } from 'src/environments/environment';

const LS_CHAVE: string = "gerentes";

@Injectable({
  providedIn: 'root'
})

export class AdminService {
  BASE_URL_GERENTE = `${environment.BASE_URL}/gerentes`;
  BASE_URL_CONTA = `${environment.BASE_URL}/contas`;
  BASE_URL_USUARIO = `${environment.BASE_URL}/usuarios`;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(
    private httpClient: HttpClient
  ) { }

  inserirGerente(ger: Gerente): Observable<Gerente> {
    return this.httpClient.post<Gerente>(this.BASE_URL_GERENTE, JSON.stringify(ger), this.httpOptions);
  }

  listarTodosGerentes(): Observable<Gerente[]> {
    return this.httpClient.get<Gerente[]>(this.BASE_URL_GERENTE, this.httpOptions);
  }

  buscarGerentePorId(idGerente: number): Observable<Gerente> {
    return this.httpClient.get<Gerente>(this.BASE_URL_GERENTE + `/${idGerente}`, this.httpOptions);
  }

  alterarGerente(ger: Gerente): Observable<Gerente> {
    return this.httpClient.put<Gerente>(this.BASE_URL_GERENTE + `/${ger.id}`, JSON.stringify(ger), this.httpOptions);
  }

  removerGerente(idGerente: number): Observable<any> {
    return this.httpClient.delete<any>(this.BASE_URL_GERENTE + `/${idGerente}`, this.httpOptions);
  }

  listarTodasContas(): Observable<Conta[]> {
    return this.httpClient.get<Conta[]>(this.BASE_URL_CONTA, this.httpOptions);
  }

  inserirUsuario(novoUsuario: Usuario): Observable<Usuario> {
    return this.httpClient.post<Usuario>(this.BASE_URL_USUARIO, JSON.stringify(novoUsuario), this.httpOptions);
  }

  alterarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.httpClient.put<Usuario>(this.BASE_URL_USUARIO + `/${usuario.id}`, JSON.stringify(usuario), this.httpOptions);
  }
}
