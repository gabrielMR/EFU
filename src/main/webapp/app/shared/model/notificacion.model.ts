import { Moment } from 'moment';
import { IFraternidad } from 'app/shared/model/fraternidad.model';

export interface INotificacion {
  id?: number;
  tituloNotificacion?: string;
  descripcion?: string;
  contenido?: string;
  estado?: number;
  fecha?: Moment;
  hora?: Moment;
  idFraternidad?: number;
  nombres?: IFraternidad[];
}

export class Notificacion implements INotificacion {
  constructor(
    public id?: number,
    public tituloNotificacion?: string,
    public descripcion?: string,
    public contenido?: string,
    public estado?: number,
    public fecha?: Moment,
    public hora?: Moment,
    public idFraternidad?: number,
    public nombres?: IFraternidad[]
  ) {}
}
