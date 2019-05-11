import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActividad } from 'app/shared/model/actividad.model';

type EntityResponseType = HttpResponse<IActividad>;
type EntityArrayResponseType = HttpResponse<IActividad[]>;

@Injectable({ providedIn: 'root' })
export class ActividadService {
  public resourceUrl = SERVER_API_URL + 'api/actividads';

  constructor(protected http: HttpClient) {}

  create(actividad: IActividad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actividad);
    return this.http
      .post<IActividad>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(actividad: IActividad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actividad);
    return this.http
      .put<IActividad>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IActividad>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IActividad[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(actividad: IActividad): IActividad {
    const copy: IActividad = Object.assign({}, actividad, {
      fechaini: actividad.fechaini != null && actividad.fechaini.isValid() ? actividad.fechaini.format(DATE_FORMAT) : null,
      fechafin: actividad.fechafin != null && actividad.fechafin.isValid() ? actividad.fechafin.format(DATE_FORMAT) : null,
      horaini: actividad.horaini != null && actividad.horaini.isValid() ? actividad.horaini.toJSON() : null,
      horafin: actividad.horafin != null && actividad.horafin.isValid() ? actividad.horafin.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaini = res.body.fechaini != null ? moment(res.body.fechaini) : null;
      res.body.fechafin = res.body.fechafin != null ? moment(res.body.fechafin) : null;
      res.body.horaini = res.body.horaini != null ? moment(res.body.horaini) : null;
      res.body.horafin = res.body.horafin != null ? moment(res.body.horafin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((actividad: IActividad) => {
        actividad.fechaini = actividad.fechaini != null ? moment(actividad.fechaini) : null;
        actividad.fechafin = actividad.fechafin != null ? moment(actividad.fechafin) : null;
        actividad.horaini = actividad.horaini != null ? moment(actividad.horaini) : null;
        actividad.horafin = actividad.horafin != null ? moment(actividad.horafin) : null;
      });
    }
    return res;
  }
}
