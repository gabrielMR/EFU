import { Moment } from 'moment';
import { INiusta } from 'app/shared/model/niusta.model';
import { IDelegado } from 'app/shared/model/delegado.model';
import { IFraterno } from 'app/shared/model/fraterno.model';
import { IPremio } from 'app/shared/model/premio.model';
import { INotificacion } from 'app/shared/model/notificacion.model';

export interface IFraternidad {
  id?: number;
  idFraternidad?: number;
  nombre?: string;
  danza?: string;
  instancia?: string;
  fundacion?: Moment;
  descripcion?: string;
  estado?: number;
  nombreNiusta?: INiusta;
  nombreDelegado?: IDelegado;
  nombres?: IFraterno[];
  tituloPremios?: IPremio[];
  tituloNotificacions?: INotificacion[];
}

export class Fraternidad implements IFraternidad {
  constructor(
    public id?: number,
    public idFraternidad?: number,
    public nombre?: string,
    public danza?: string,
    public instancia?: string,
    public fundacion?: Moment,
    public descripcion?: string,
    public estado?: number,
    public nombreNiusta?: INiusta,
    public nombreDelegado?: IDelegado,
    public nombres?: IFraterno[],
    public tituloPremios?: IPremio[],
    public tituloNotificacions?: INotificacion[]
  ) {}
}
