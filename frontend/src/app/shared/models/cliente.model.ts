import { Endereco } from "./endereco.model";
import { Usuario } from "./usuario.model";

export class Cliente {
    constructor(
        public id?: string,
        public nome?: string,
        public email?: string,
        public cpf?: string,
        public endereco?: Endereco,
        public telefone?: string,
        public salario?: number,
        public usuario?: Usuario
    ) { }
}