import { Component, OnInit } from '@angular/core';
import { Cliente, Conta, Gerente } from 'src/app/shared';
import { GerenteService } from '../services';
import { LoginService } from 'src/app/auth/services/login.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-tela-inicial',
  templateUrl: './tela-inicial.component.html',
  styleUrls: ['./tela-inicial.component.css']
})
export class TelaInicialComponent implements OnInit {

  contas: Conta[] = [];

  constructor(private gerenteService: GerenteService, private loginService: LoginService) { }

  ngOnInit(): void {
    this.gerenteService.buscaContasInativasPorGerente().subscribe(
      (data: Conta[]) => {
        if (data != null) {
          this.contas = data;
        }
      }
    );
  }

}
