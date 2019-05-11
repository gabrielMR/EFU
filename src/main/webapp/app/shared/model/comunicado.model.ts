export interface IComunicado {
  id?: number;
  titulo?: string;
  descripcion?: string;
  contenido?: string;
  gestion?: number;
}

export class Comunicado implements IComunicado {
  constructor(
    public id?: number,
    public titulo?: string,
    public descripcion?: string,
    public contenido?: string,
    public gestion?: number
  ) {}
}
