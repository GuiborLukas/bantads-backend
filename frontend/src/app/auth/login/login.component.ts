import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Login, LoginResponse, Usuario } from 'src/app/shared';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  @ViewChild('formLogin') formLogin!: NgForm;
  login: Login = new Login();
  loading: boolean = false;
  message!: string;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    if (this.loginService.usuarioLogado) {
      this.router.navigate([`${this.loginService.usuarioLogado.perfil?.toLowerCase()}`]);
    }
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params: any) => {
      this.message = params['error'];
      // this.message = params['message'];
    });
  }

  logar(): void {
    this.loading = true;
    if (this.formLogin.form.valid) {
      this.loginService.userToken = "tokenTeste";
      this.loginService.usuarioLogado = new Usuario("Gerente", "gerente@email.com", "gerente", "GERENTE", 1);
      // this.loginService.usuarioLogado = new Usuario("Admin", "admin@email.com", "admin", "ADMIN", 1);
      // this.loginService.usuarioLogado = new Usuario("Cliente Novo 1", "cliente1@email.com", "cliente", "CLIENTE", 1);
      this.loading = false;
      this.router.navigate([`${this.loginService.usuarioLogado.perfil?.toLowerCase()}`]);
      // this.loginService.login(this.login).subscribe(
      //   (loginResponse: LoginResponse) => {
      //     if (loginResponse != null) {
      //       let { auth: isAuth, token, usuario: usu } = loginResponse;
      //       if (isAuth) {
      //         this.loginService.userToken = token!;
      //         this.loginService.usuarioLogado = usu!;
      //         this.loading = false;
      //         this.router.navigate([`${usu!.perfil?.toLowerCase()}`]);
      //       }
      //     }
      //     else {
      //       this.message = 'Usuário/Senha inválidos.';
      //     }
      //   });
    }
    this.loading = false;
  }
}
