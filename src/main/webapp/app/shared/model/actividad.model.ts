import { Moment } from 'moment';

export interface IActividad {
  id?: number;
  titulo?: string;
  descripcion?: string;
  fechaini?: Moment;
  fechafin?: Moment;
  horaini?: Moment;
  horafin?: Moment;
  contenido?: string;
  estado?: number;
  gestion?: number;
}

export class Actividad implements IActividad {
  constructor(
    public id?: number,
    public titulo?: string,
    public descripcion?: string,
    public fechaini?: Moment,
    public fechafin?: Moment,
    public horaini?: Moment,
    public horafin?: Moment,
    public contenido?: string,
    public estado?: number,
    public gestion?: number
  ) {}
}
