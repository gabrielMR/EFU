export interface IDelegado {
  id?: number;
  idDelegado?: number;
  ci?: number;
  nombreDelegado?: string;
  ru?: number;
  item?: number;
  telefono?: number;
  correo?: string;
}

export class Delegado implements IDelegado {
  constructor(
    public id?: number,
    public idDelegado?: number,
    public ci?: number,
    public nombreDelegado?: string,
    public ru?: number,
    public item?: number,
    public telefono?: number,
    public correo?: string
  ) {}
}
