import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecorrido } from 'app/shared/model/recorrido.model';

type EntityResponseType = HttpResponse<IRecorrido>;
type EntityArrayResponseType = HttpResponse<IRecorrido[]>;

@Injectable({ providedIn: 'root' })
export class RecorridoService {
  public resourceUrl = SERVER_API_URL + 'api/recorridos';

  constructor(protected http: HttpClient) {}

  create(recorrido: IRecorrido): Observable<EntityResponseType> {
    return this.http.post<IRecorrido>(this.resourceUrl, recorrido, { observe: 'response' });
  }

  update(recorrido: IRecorrido): Observable<EntityResponseType> {
    return this.http.put<IRecorrido>(this.resourceUrl, recorrido, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecorrido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecorrido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
