export class Usuario {
  public isActive? : boolean = false;
  
  constructor(
    public nome?: string,
    public email?: string,
    public perfil?: string,
    public id?: string,
  ) { }
}
