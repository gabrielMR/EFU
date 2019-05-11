import { IFraternidad } from 'app/shared/model/fraternidad.model';

export interface IPremio {
  id?: number;
  tituloPremio?: string;
  descripcion?: string;
  categoria?: string;
  puesto?: number;
  gestion?: number;
  idFraternidad?: number;
  nombres?: IFraternidad[];
}

export class Premio implements IPremio {
  constructor(
    public id?: number,
    public tituloPremio?: string,
    public descripcion?: string,
    public categoria?: string,
    public puesto?: number,
    public gestion?: number,
    public idFraternidad?: number,
    public nombres?: IFraternidad[]
  ) {}
}
