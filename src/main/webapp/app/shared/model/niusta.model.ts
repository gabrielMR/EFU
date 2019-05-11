export interface INiusta {
  id?: number;
  ci?: number;
  nombreNiusta?: string;
  ru?: number;
  item?: number;
  edad?: number;
  gustos?: string;
  estatura?: number;
  titulo?: string;
}

export class Niusta implements INiusta {
  constructor(
    public id?: number,
    public ci?: number,
    public nombreNiusta?: string,
    public ru?: number,
    public item?: number,
    public edad?: number,
    public gustos?: string,
    public estatura?: number,
    public titulo?: string
  ) {}
}
