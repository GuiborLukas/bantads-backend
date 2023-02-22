export class Usuario {
  public isActive? : boolean = false;
  
  constructor(
    public nome?: string,
    public email?: string,
    public senha?: string,
    public perfil?: string,
    public id?: number,
  ) { }
}
