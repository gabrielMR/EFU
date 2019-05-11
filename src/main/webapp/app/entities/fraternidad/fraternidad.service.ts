import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFraternidad } from 'app/shared/model/fraternidad.model';

type EntityResponseType = HttpResponse<IFraternidad>;
type EntityArrayResponseType = HttpResponse<IFraternidad[]>;

@Injectable({ providedIn: 'root' })
export class FraternidadService {
  public resourceUrl = SERVER_API_URL + 'api/fraternidads';

  constructor(protected http: HttpClient) {}

  create(fraternidad: IFraternidad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fraternidad);
    return this.http
      .post<IFraternidad>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fraternidad: IFraternidad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fraternidad);
    return this.http
      .put<IFraternidad>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFraternidad>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFraternidad[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fraternidad: IFraternidad): IFraternidad {
    const copy: IFraternidad = Object.assign({}, fraternidad, {
      fundacion: fraternidad.fundacion != null && fraternidad.fundacion.isValid() ? fraternidad.fundacion.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fundacion = res.body.fundacion != null ? moment(res.body.fundacion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fraternidad: IFraternidad) => {
        fraternidad.fundacion = fraternidad.fundacion != null ? moment(fraternidad.fundacion) : null;
      });
    }
    return res;
  }
}
