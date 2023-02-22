import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, map, of, take } from 'rxjs';
import { Usuario, Login, LoginResponse } from 'src/app/shared';
import { environment as env } from 'src/environments/environment';

const LS_CHAVE: string = 'usuarioLogado';
const LS_CHAVE_TOKEN: string = 'usuarioLogadoToken';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  BASE_URL: string = env.BASE_URL + "login/";

  constructor(
    private httpClient: HttpClient
  ) { }

  public get usuarioLogado(): Usuario {
    let usu = localStorage[LS_CHAVE];
    return usu ? JSON.parse(localStorage[LS_CHAVE]) : null;
  }
  public set usuarioLogado(usuario: Usuario) {
    localStorage[LS_CHAVE] = JSON.stringify(usuario);
  }

  public get userToken(): string {
    let token = localStorage[LS_CHAVE_TOKEN];
    return token;
  }

  public set userToken(token: string) {
    localStorage[LS_CHAVE_TOKEN] = token.trim();
  }

  logout() {
    delete localStorage[LS_CHAVE];
    delete localStorage[LS_CHAVE_TOKEN];
  }

  login(login: Login): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(this.BASE_URL, JSON.stringify(login));
  }
}
