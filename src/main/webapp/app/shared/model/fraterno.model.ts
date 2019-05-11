import { IFraternidad } from 'app/shared/model/fraternidad.model';

export interface IFraterno {
  id?: number;
  ci?: number;
  nombreFraterno?: string;
  ru?: number;
  item?: number;
  observaciones?: string;
  cis?: IFraternidad[];
}

export class Fraterno implements IFraterno {
  constructor(
    public id?: number,
    public ci?: number,
    public nombreFraterno?: string,
    public ru?: number,
    public item?: number,
    public observaciones?: string,
    public cis?: IFraternidad[]
  ) {}
}
