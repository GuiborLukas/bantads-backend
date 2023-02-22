import { Conta } from "./conta.model";

export enum TiposOperacao {
    Saque = 'SAQUE',
    Deposito = 'DEPÓSITO',
    Transferencia = 'TRANSFERÊNCIA'
}

export class Transacao {
    public id?: number

    constructor(
        public dataHora: Date,
        public tipo: TiposOperacao,
        public valor: number,
        public contaOrigem?: Conta,
        public contaDestino?: Conta
    ) { }
}


