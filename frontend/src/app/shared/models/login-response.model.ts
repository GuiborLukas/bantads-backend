import { Usuario } from "./usuario.model";

export class LoginResponse {
  constructor(public auth?: boolean, public token?: string, public usuario?: Usuario) { }
}
