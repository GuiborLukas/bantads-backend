import { Component, OnInit } from '@angular/core';
import { Conta, Gerente } from 'src/app/shared';
import { AdminService } from '../services';
import { ClienteService } from 'src/app/cliente/services';
import { GerenteService } from 'src/app/gerente/services';

@Component({
  selector: 'app-tela-inicial',
  templateUrl: './tela-inicial.component.html',
  styleUrls: ['./tela-inicial.component.css']
})
export class TelaInicialComponent implements OnInit {
  gerentesComTotais: Record<string, any>[] = [];
  totalClientes!: number;
  totalSaldo!: number;
  totalGerentes!: number;

  constructor(
    private adminService: AdminService,
    private gerenteService: GerenteService
  ) { }

  ngOnInit(): void {
    this.totalSaldo = 0;
    this.totalGerentes = 0;
    this.totalClientes = 0;
    //Lista os gerentes
    this.adminService.listarTodosGerentes().subscribe(
      (data: Gerente[]) => {
        if (data != null) {
          data.forEach((gerente, idx, arr) => {
            //Busca todas as contas do gerente
            this.adminService.buscaContasPorGerente(gerente).subscribe(
              (contas: Conta[]) => {
                //Total de clientes = total de contas
                let totalClientes: number = contas.length;
                //Total saldo positivo
                let totalSaldoPositivo: number = contas
                  .reduce((acum, obj) => { return acum + (obj.saldo! > 0 ? obj.saldo! : 0) }, 0);
                //Total saldo Negativo
                let totalSaldoNegativo: number = contas
                  .reduce((acum, obj) => { return acum - (obj.saldo! < 0 ? obj.saldo! : 0) }, 0);

                var obj: Record<string, any> = {
                  ...gerente,
                  totalClientes: totalClientes,
                  totalSaldoPositivo: totalSaldoPositivo,
                  totalSaldoNegativo: totalSaldoNegativo
                }
                //insere o objeto no array utilizado no componente
                this.gerentesComTotais.push(obj);
                this.totalSaldo += totalSaldoPositivo - totalSaldoNegativo;
                this.totalClientes += totalClientes;
                this.totalGerentes++;
              }
            );
          })
        }
      });

  }

}
