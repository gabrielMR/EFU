export interface IRecorrido {
  id?: number;
  nombre?: string;
  inicio?: string;
  fin?: string;
  descripcion?: string;
  gestion?: number;
}

export class Recorrido implements IRecorrido {
  constructor(
    public id?: number,
    public nombre?: string,
    public inicio?: string,
    public fin?: string,
    public descripcion?: string,
    public gestion?: number
  ) {}
}
