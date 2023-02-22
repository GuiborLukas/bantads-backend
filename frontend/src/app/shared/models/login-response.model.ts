import { UsuarioDTO } from "./usuario-dto.model";

export class LoginResponse {
  constructor(public auth?: boolean, public token?: string, public usuario?: UsuarioDTO) { }
}
